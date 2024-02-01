package live.vishsinh.expensetracker.service;

import live.vishsinh.expensetracker.entity.*;
import live.vishsinh.expensetracker.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Service
public class ExpenseService {

        @Autowired
        private ExpenseRepository expenseRepository;
        @Autowired
        private UserRepository userRepository;

        public Expense createExpense(Double amount, Date date, String description, Long userId) {

            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

            Expense expense = new Expense();
            expense.setAmount(amount);
            expense.setDate(date);
            expense.setDescription(description);
            expense.setUserId(user.getUserId());

            return expenseRepository.save(expense);
        }

        public List<Expense> getUserExpense(Long userId) {
            System.out.println("Here - 1");
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            System.out.println("Here - 2");
            return expenseRepository.findByUserId(user.getUserId());
        }
}
