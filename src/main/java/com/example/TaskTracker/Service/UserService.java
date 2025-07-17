package com.example.TaskTracker.Service;

import com.example.TaskTracker.Entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtService jwtService;
    public String verify(Task task)
    {
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(task.getEmail(),task.getPassword()));
        if(authentication.isAuthenticated())
        {
            return jwtService.generateToken(task);
        }
        return "Fail";
    }
}
