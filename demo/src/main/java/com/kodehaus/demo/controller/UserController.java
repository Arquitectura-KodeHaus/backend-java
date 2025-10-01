package com.kodehaus.demo.controller;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kodehaus.demo.dto.UserDto;
import com.kodehaus.demo.model.User;
import com.kodehaus.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<String> createUser(@RequestBody User user){
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") String id){
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PutMapping()
    public ResponseEntity<String> updateUser(@RequestBody User user){
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") String id){
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @GetMapping("/find-user")
    public ResponseEntity<UserDto> findByName(@RequestParam String email){
        return ResponseEntity.ok(userService.findByEmailId(email));
    }

    @GetMapping("/find-salary")
    public ResponseEntity<UserDto> findSalaryLesserThan(@RequestParam double salary){
        return ResponseEntity.ok(userService.findSalary(salary));
    }

    @GetMapping("/find-by-role")
    public ResponseEntity<UserDto> findByRoles(@RequestParam String role){
        return ResponseEntity.ok(userService.findUserByRole(role));
    }

    @GetMapping("/find-by-roles")
    public ResponseEntity<UserDto> findByRoles(@RequestParam List<String> roles){
        return ResponseEntity.ok(userService.findUserByMultipleRole(roles));
    }

    @GetMapping("/find-by-names")
    public ResponseEntity<UserDto> findByNames(@RequestParam List<String> names){
        return ResponseEntity.ok(userService.findUserByIn(names));
    }
}

