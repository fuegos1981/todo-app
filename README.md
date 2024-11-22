TODO-APP

Description

    This RESTful API to manage a simple todo list application using Spring Boot, Hibernate, and MySQL. 
    The application should allow users to create, read, update, and delete todo items. 
    Each item should have a title and a description. Use Hibernate to persist the items in the database.

Run the Application

    1. Start a MySQL database server and create a database named todo_db. Script for creating database is in directory sql.
    2. Run the application using mvn spring-boot:run or through your IDE.
    3. Use tools like Postman or cURL to test the API endpoints:
        GET /api/todos
        GET /api/todos/{id}
        POST /api/todos with a JSON body like { "title": "Buy milk", "description": "Remember to buy milk" }
        PUT /api/todos/{id} to update a todo.
        DELETE /api/todos/{id} to delete a todo.

feedback

    1. It was easy to complete the task using AI
    2. I needed 2 hours
    3. I had problem only with pom.xml.