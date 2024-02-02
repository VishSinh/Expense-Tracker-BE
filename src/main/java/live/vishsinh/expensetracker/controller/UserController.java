package live.vishsinh.expensetracker.controller;

import live.vishsinh.expensetracker.helpers.ResponseObj;
import live.vishsinh.expensetracker.service.UserService;

import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    public static class AuthSignUpRequest {
        public String phoneNumber;
        public String username;
        public String password;
    }

    @PostMapping("/auth/signup")
    public ResponseObj authSignUp(@RequestBody AuthSignUpRequest requestBody) {
        try{
            if(requestBody.phoneNumber == null || requestBody.username == null || requestBody.password == null) {
                throw new RuntimeException("Invalid request body. Please provide all required fields.");
            }

            Object token = userService.signUpUser(requestBody.phoneNumber, requestBody.username, requestBody.password);

            return new ResponseObj(true, "User Created", token, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseObj(false, e.getMessage(),  HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseObj(false, "Internal Server Error",  HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static class AuthLoginRequest {
        public String phoneNumber;
        public String password;
    }

    @PostMapping("/auth/login")
    public ResponseObj authLogin(@RequestBody AuthLoginRequest requestBody) {
        try{
            if(requestBody.phoneNumber == null || requestBody.password == null) {
                throw new BadRequestException("Invalid request body. Please provide all required fields.");
            }

            Object token = userService.loginUser(requestBody.phoneNumber, requestBody.password);

            return new ResponseObj(true, "User Logged In", token, HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseObj(false, e.getMessage(),  HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return new ResponseObj(false, "Internal Server Error",  HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
