package com.example.todo_app.service;

import com.example.todo_app.entity.Todo;
import com.example.todo_app.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TodoServiceTest {
    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTodos_ShouldReturnListOfTodos() {
        when(todoRepository.findAll()).thenReturn(Arrays.asList(
                new Todo("Task 1", "Description 1"),
                new Todo("Task 2", "Description 2")
        ));

        var todos = todoService.getAllTodos();

        assertEquals(2, todos.size());
        assertEquals("Task 1", todos.get(0).getTitle());
        assertEquals("Task 2", todos.get(1).getTitle());
        verify(todoRepository, times(1)).findAll();
    }

    @Test
    void getTodoById_ShouldReturnTodo_WhenExists() {
        Todo todo = new Todo("Task", "Description");
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));

        var result = todoService.getTodoById(1L);

        assertTrue(result.isPresent());
        assertEquals("Task", result.get().getTitle());
        verify(todoRepository, times(1)).findById(1L);
    }

    @Test
    void getTodoById_ShouldReturnEmpty_WhenNotExists() {
        when(todoRepository.findById(1L)).thenReturn(Optional.empty());

        var result = todoService.getTodoById(1L);

        assertFalse(result.isPresent());
        verify(todoRepository, times(1)).findById(1L);
    }

    @Test
    void createTodo_ShouldSaveAndReturnTodo() {
        Todo todo = new Todo("New Task", "New Description");
        when(todoRepository.save(todo)).thenReturn(todo);

        var result = todoService.createTodo(todo);

        assertNotNull(result);
        assertEquals("New Task", result.getTitle());
        verify(todoRepository, times(1)).save(todo);
    }

    @Test
    void updateTodo_ShouldUpdateAndReturnUpdatedTodo() {
        Todo existingTodo = new Todo("Old Task", "Old Description");
        existingTodo.setId(1L);

        Todo updatedTodo = new Todo("Updated Task", "Updated Description");

        when(todoRepository.findById(1L)).thenReturn(Optional.of(existingTodo));
        when(todoRepository.save(existingTodo)).thenReturn(existingTodo);

        var result = todoService.updateTodo(1L, updatedTodo);

        assertNotNull(result);
        assertEquals("Updated Task", result.getTitle());
        verify(todoRepository, times(1)).findById(1L);
        verify(todoRepository, times(1)).save(existingTodo);
    }

    @Test
    void deleteTodoById_ShouldInvokeDeleteById() {
        doNothing().when(todoRepository).deleteById(1L);

        todoService.deleteTodoById(1L);

        verify(todoRepository, times(1)).deleteById(1L);
    }

}