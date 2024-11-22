CREATE DATABASE IF NOT EXISTS todo_db;

-- Use the database
USE todo_db;

-- Create the 'todos' table
CREATE TABLE IF NOT EXISTS todos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL
);

-- Insert sample data into 'todos' table
INSERT INTO todos (title, description) VALUES
('Buy groceries', 'Milk, Bread, Cheese, Eggs'),
('Clean the house', 'Living room, Kitchen, Bathroom'),
('Finish project report', 'Complete the financial analysis and conclusion'),
('Read a book', 'Start reading "Atomic Habits"'),
('Exercise', '30 minutes of jogging and stretching'),
('Plan vacation', 'Research destinations and book flights'),
('Call mom', 'Catch up and discuss family updates'),
('Organize closet', 'Sort clothes into keep, donate, and toss piles'),
('Prepare presentation', 'Create slides for the meeting on Friday'),
('Watch a movie', 'Relax and watch "Inception"');

-- Verify the data
SELECT * FROM todos;