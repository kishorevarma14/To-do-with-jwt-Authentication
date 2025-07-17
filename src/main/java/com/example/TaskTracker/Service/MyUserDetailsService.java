package com.example.TaskTracker.Service;

import com.example.TaskTracker.Entity.Task;
import com.example.TaskTracker.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    TaskRepository taskRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Task task= taskRepository.findByEmail(email);
        if(task==null){
            throw new UsernameNotFoundException("User Not Found");
        }
        return new MyUserDetails(task);
    }
}
