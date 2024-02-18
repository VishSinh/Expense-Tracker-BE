package live.vishsinh.expensetracker.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import live.vishsinh.expensetracker.entity.Expense;
import live.vishsinh.expensetracker.helpers.ResponseObj;
import live.vishsinh.expensetracker.helpers.VerifyUserToken;
import live.vishsinh.expensetracker.service.ExpenseService;

import lombok.NonNull;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import javax.validation.constraints.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/expense")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private VerifyUserToken verifyUserToken;

    public static class createExpenseRequest {
        public Double amount;
        public Date date;
        public String description;

        @JsonProperty("user_id")
        public UUID userId;

        @JsonProperty("group_id")
        public UUID groupId;
    }

    @PostMapping("/create")
    public ResponseObj createExpense(@RequestBody createExpenseRequest requestBody, @RequestHeader("Authorization") String token) {
        try {
            verifyUserToken.verifyToken(token, requestBody.userId.toString());

            if (requestBody.amount == null || requestBody.date == null || requestBody.description == null || requestBody.userId == null) {
                throw new BadRequestException("Invalid request body");
            }

            if (requestBody.amount <= 0) {
                throw new BadRequestException("Invalid amount");
            }

            Expense createdExpense = expenseService.createExpense(requestBody.amount, requestBody.date, requestBody.description, requestBody.userId, requestBody.groupId);

            return new ResponseObj(true, "Expense record created", createdExpense, HttpStatus.CREATED);

        } catch (BadRequestException e) {
            return new ResponseObj(false, e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (LoginException e) {
            return new ResponseObj(false, e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch(RuntimeException e){
            System.out.println(e.getMessage());
            return new ResponseObj(false, "An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public static class getExpenseRequest {
        @JsonProperty("user_id")
        public UUID userId;

        @JsonProperty("group_id")
        public UUID groupId;
    }

    @PostMapping("/get")
    public ResponseObj getExpenses(@RequestBody getExpenseRequest requestBody, @RequestHeader("Authorization") String token) {
        try {
            verifyUserToken.verifyToken(token, requestBody.userId.toString());

            if (requestBody.userId == null) {
                throw new BadRequestException("Invalid request body");
            }

            List<Expense> expenses = expenseService.getExpenses(requestBody.userId, requestBody.groupId);

            return new ResponseObj(true, "Expenses fetched", expenses, HttpStatus.OK);

        } catch (BadRequestException e) {
            return new ResponseObj(false, e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (LoginException e) {
            return new ResponseObj(false, e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch(RuntimeException e){
            System.out.println(e.getMessage());
            return new ResponseObj(false, "An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
