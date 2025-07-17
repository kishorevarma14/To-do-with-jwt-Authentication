package com.example.TaskTracker.Controller;

import com.example.TaskTracker.Entity.Task;
import com.example.TaskTracker.Entity.Todo;
import com.example.TaskTracker.Repository.TaskRepository;
import com.example.TaskTracker.Repository.TodoRepository;
import com.example.TaskTracker.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class TaskController {
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    TaskRepository taskrepository;
    @Autowired
    TodoRepository todoRepository;
    @Autowired
    UserService userService;
    @PostMapping("/Register")
    public String AddTask(@RequestBody Task task)
    {
        Task existing=taskrepository.findByEmail(task.getEmail());
        if(existing==null)
        {
            //Encryting the password
            task.setPassword(passwordEncoder.encode(task.getPassword()));
            taskrepository.save(task);
            return "Sucessful";
        }
        return "Already Exist "+task.getId();
    }
    @GetMapping("/RegisteredUsers")
    public ResponseEntity<List<Task>> allregistedusers()
    {
        List<Task> users= taskrepository.findAll();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/Display/{id}")
    public Task DisplayById(@PathVariable long id)
    {
        Optional<Task> task=taskrepository.findById(id);
        if(task.isPresent())
        {
            Task found=task.get();
            return found;
        }
        return null;
    }
    @GetMapping("/HomePage")
    public String printHomepage()
    {
        return "Welcome to HomePage";
    }
    @PostMapping("/todos")
    public Todo gettodo(@RequestBody Todo todo)
    {
        return todoRepository.save(todo);
    }
    @PutMapping("/todos/{id}")
     public Todo updatetodo(@RequestBody Todo todo,@PathVariable long id)
    {
        return todoRepository.save(todo);
    }
    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Void> deletetodo(@PathVariable long id)
    {
        Optional<Todo> optional=todoRepository.findById(id);
        if(!optional.isPresent())
        {
            return ResponseEntity.notFound().build();
        }
        todoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/todos")
    public ResponseEntity<Page<Todo>> getalltodos(@RequestParam int page, @RequestParam int size)
    {
        Pageable pageable = PageRequest.of(page, size);
        Page<Todo> todos = todoRepository.findAll(pageable);
        return ResponseEntity.ok(todos);
    }
    @PostMapping("/login")
    public String gettoken(@RequestBody Task task)
    {
        return userService.verify(task);
    }
}

