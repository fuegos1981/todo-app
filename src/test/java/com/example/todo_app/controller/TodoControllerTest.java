package com.example.todo_app.controller;

import com.example.todo_app.entity.Todo;
import com.example.todo_app.service.TodoService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TodoControllerTest {
    @Mock
    private TodoService todoService;

    @InjectMocks
    private TodoController todoController;

    public TodoControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTodos_ShouldReturnTodos() {
        when(todoService.getAllTodos()).thenReturn(Arrays.asList(
                new Todo("Task 1", "Description 1"),
                new Todo("Task 2", "Description 2")
        ));

        var todos = todoController.getAllTodos();

        assertEquals(2, todos.size());
        assertEquals("Task 1", todos.get(0).getTitle());
        verify(todoService, times(1)).getAllTodos();
    }

    @Test
    void getTodoById_ShouldReturnTodo_WhenExists() {
        Todo todo = new Todo("Task", "Description");
        when(todoService.getTodoById(1L)).thenReturn(Optional.of(todo));

        var response = todoController.getTodoById(1L);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertEquals("Task", response.getBody().getTitle());
        verify(todoService, times(1)).getTodoById(1L);
    }

    @Test
    void getTodoById_ShouldReturnNotFound_WhenNotExists() {
        when(todoService.getTodoById(1L)).thenReturn(Optional.empty());

        var response = todoController.getTodoById(1L);

        assertTrue(response.getStatusCode().is4xxClientError());
        verify(todoService, times(1)).getTodoById(1L);
    }

    @Test
    void createTodo_ShouldReturnCreatedTodo() {
        Todo todo = new Todo("New Task", "New Description");
        when(todoService.createTodo(todo)).thenReturn(todo);

        var result = todoController.createTodo(todo);

        assertNotNull(result);
        assertEquals("New Task", result.getTitle());
        verify(todoService, times(1)).createTodo(todo);
    }

    @Test
    void updateTodo_ShouldReturnUpdatedTodo_WhenSuccessful() {
        // Arrange
        Long id = 1L;
        Todo updatedTodo = new Todo("Updated Title", "Updated Description");
        updatedTodo.setId(id);

        when(todoService.updateTodo(id, updatedTodo)).thenReturn(updatedTodo);

        // Act
        ResponseEntity<Todo> response = todoController.updateTodo(id, updatedTodo);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Updated Title", response.getBody().getTitle());
        assertEquals("Updated Description", response.getBody().getDescription());

        verify(todoService, times(1)).updateTodo(id, updatedTodo);
    }

    @Test
    void updateTodo_ShouldReturnNotFound_WhenTodoNotFound() {
        // Arrange
        Long id = 1L;
        Todo updatedTodo = new Todo("Updated Title", "Updated Description");

        when(todoService.updateTodo(id, updatedTodo)).thenThrow(new RuntimeException("Todo not found"));

        // Act
        ResponseEntity<Todo> response = todoController.updateTodo(id, updatedTodo);

        // Assert
        assertNotNull(response);
        assertEquals(404, response.getStatusCode().value());
        assertNull(response.getBody());

        verify(todoService, times(1)).updateTodo(id, updatedTodo);
    }

    @Test
    void deleteTodoById_ShouldReturnNoContent() {
        doNothing().when(todoService).deleteTodoById(1L);

        var response = todoController.deleteTodoById(1L);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        verify(todoService, times(1)).deleteTodoById(1L);
    }

}