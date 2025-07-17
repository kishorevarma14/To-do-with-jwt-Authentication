package com.example.TaskTracker.Repository;

import com.example.TaskTracker.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    Task findByEmail(String email);
    Task findByName(String username);
}
