package live.vishsinh.expensetracker.controller;

import live.vishsinh.expensetracker.entity.Expense;
import live.vishsinh.expensetracker.helpers.ResponseObj;
import live.vishsinh.expensetracker.helpers.VerifyUserToken;
import live.vishsinh.expensetracker.service.ExpenseService;

import lombok.NonNull;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.*;

import java.util.Date;
import java.util.List;


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
        public String userIdHash;
    }

    @PostMapping("/create")
    public ResponseObj createExpense(@RequestBody createExpenseRequest requestBody, @RequestHeader("Authorization") String token){
        try{
            boolean isTokenValid = verifyUserToken.verifyToken(token, requestBody.userIdHash);

            if (!isTokenValid) {
                return new ResponseObj(false, "Invalid Token", HttpStatus.UNAUTHORIZED);
            }

            Expense createdExpense = expenseService.createExpense(requestBody.amount, requestBody.date, requestBody.description, requestBody.userIdHash);
            return new ResponseObj(true, "", createdExpense, HttpStatus.CREATED);
        }
        catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return new ResponseObj(false, "Internal Server Error",  HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public static class getUserExpenseRequest {
        public String userIdHash;
    }

    @PostMapping("/get")
    public ResponseObj getUserExpense(@RequestBody getUserExpenseRequest requestBody) {
        try{
            List<Expense> userExpenses = expenseService.getUserExpense(requestBody.userIdHash);
            System.out.println("Here - 3");
            return new ResponseObj(true, "", userExpenses, HttpStatus.OK);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return new ResponseObj(false, "Internal Server Error",  HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
