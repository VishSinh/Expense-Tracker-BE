package live.vishsinh.expensetracker.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import live.vishsinh.expensetracker.helpers.ResponseObj;
import live.vishsinh.expensetracker.helpers.VerifyUserToken;
import live.vishsinh.expensetracker.service.UserService;

import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

import javax.security.auth.login.LoginException;
import java.util.UUID;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private VerifyUserToken verifyUserToken;

    public static class AuthSignUpRequest {
        @JsonProperty("phone_number")
        public String phoneNumber;

        public String name;

        public String password;
    }

    @PostMapping("/auth/signup")
    public ResponseObj authSignUp(@RequestBody AuthSignUpRequest requestBody) {
        try{
            if(requestBody.phoneNumber == null || requestBody.name == null || requestBody.password == null) {
                throw new RuntimeException("Invalid request body. Please provide all required fields.");
            }

            Object token = userService.signUpUser(requestBody.phoneNumber, requestBody.name, requestBody.password);

            return new ResponseObj(true, "User Created", token, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseObj(false, e.getMessage(),  HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseObj(false, "Internal Server Error",  HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static class AuthLoginRequest {
        @JsonProperty("phone_number")
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

    public static class AuthPasswordResetRequest {
        @JsonProperty("user_id")
        public UUID userId;

        @JsonProperty("old_password")
        public String oldPassword;

        @JsonProperty("new_password")
        public String newPassword;
    }

    @PostMapping("/auth/password/reset")
    public ResponseObj authPasswordReset(@RequestBody AuthPasswordResetRequest requestBody, @RequestHeader("Authorization") String token){
        try{
            verifyUserToken.verifyToken(token, requestBody.userId.toString());

            if(requestBody.userId == null || requestBody.oldPassword == null || requestBody.newPassword == null) {
                throw new BadRequestException("Invalid request body. Please provide all required fields.");
            }

            Object responseObj = userService.resetPassword(requestBody.userId, requestBody.oldPassword, requestBody.newPassword);

            return new ResponseObj(true, "Password Reset Successful", responseObj, HttpStatus.OK);

        }catch (LoginException e) {
            return new ResponseObj(false, e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (BadRequestException e) {
            return new ResponseObj(false, e.getMessage(),  HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return new ResponseObj(false, "Internal Server Error",  HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static class UserFetchDetailsRequest {
        @JsonProperty("user_id")
        public UUID userId;
    }

    @PostMapping("/details")
    public ResponseObj fetchUserDetails(@RequestBody UserFetchDetailsRequest requestBody, @RequestHeader("Authorization") String token){
        try{
            verifyUserToken.verifyToken(token, requestBody.userId.toString());

            if(requestBody.userId == null) {
                throw new BadRequestException("Invalid request body. Please provide all required fields.");
            }

            Object responseObj = userService.fetchUserDetails(requestBody.userId);

            return new ResponseObj(true, "User Details Fetched", responseObj, HttpStatus.OK);

        }catch (LoginException e) {
            return new ResponseObj(false, e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (BadRequestException e) {
            return new ResponseObj(false, e.getMessage(),  HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return new ResponseObj(false, "Internal Server Error",  HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
