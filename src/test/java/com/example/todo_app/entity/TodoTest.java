package com.example.todo_app.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TodoTest {
    @Test
    void testSettersAndGetters() {
        // Arrange
        Todo todo = new Todo();

        // Act
        todo.setId(1L);
        todo.setTitle("Updated Title");
        todo.setDescription("Updated Description");

        // Assert
        assertEquals(1L, todo.getId());
        assertEquals("Updated Title", todo.getTitle());
        assertEquals("Updated Description", todo.getDescription());
    }

}