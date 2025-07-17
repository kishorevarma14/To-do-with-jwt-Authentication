package com.example.TaskTracker.Repository;

import com.example.TaskTracker.Entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo,Long> {
    Page<Todo> findAll(Pageable pageable);
}
