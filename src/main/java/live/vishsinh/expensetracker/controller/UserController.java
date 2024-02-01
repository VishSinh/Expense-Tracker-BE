package live.vishsinh.expensetracker.controller;

import live.vishsinh.expensetracker.entity.User;
import live.vishsinh.expensetracker.service.UserService;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    public static class createUserRequest {
        public String username;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody createUserRequest requestBody) {
        try{
            if(requestBody.username == null || requestBody.username.isEmpty()){
                throw new Exception("Username cannot be empty");
            }

            User createdUser = userService.createUser(requestBody.username);
            return new ResponseEntity<>("User created with ID: " + createdUser.getUserId(), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
