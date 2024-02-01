package live.vishsinh.expensetracker.controller;

import live.vishsinh.expensetracker.entity.Expense;
import live.vishsinh.expensetracker.helpers.ResponseObj;
import live.vishsinh.expensetracker.service.ExpenseService;

import lombok.NonNull;
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

    public static class createExpenseRequest {
        public Double amount;
        public Date date;
        public String description;
        public Long userId;
    }

    @PostMapping("/create")
    public ResponseObj createExpense(@RequestBody createExpenseRequest requestBody) {
        try{
            if(requestBody.amount <= 0) {
                throw new RuntimeException("Amount must be greater than 0");
            }

            Expense createdExpense = expenseService.createExpense(requestBody.amount, requestBody.date, requestBody.description, requestBody.userId);
            return new ResponseObj(true, "", createdExpense, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseObj(false, e.getMessage(),  HttpStatus.BAD_REQUEST);
        }
    }


    public static class getUserExpenseRequest {
        public Long userId;
    }

    @PostMapping("/get")
    public ResponseObj getUserExpense(@RequestBody getUserExpenseRequest requestBody) {
        try{
            List<Expense> userExpenses = expenseService.getUserExpense(requestBody.userId);
            System.out.println("Here - 3");
            return new ResponseObj(true, "", userExpenses, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseObj(false, e.getMessage(),  HttpStatus.BAD_REQUEST);
        }
    }
}
