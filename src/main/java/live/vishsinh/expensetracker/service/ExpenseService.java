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

        public Expense createExpense(Double amount, Date date, String description, String userIdHash) {

            User user = userRepository.findByUserIdHash(userIdHash);
            if(user == null) {
                throw new RuntimeException("User not found");
            }

            Long groupId = user.getGroupId();

            Expense expense = new Expense();
            expense.setAmount(amount);
            expense.setDate(date);
            expense.setDescription(description);
            expense.setUserId(user.getUserIdHash());

            return expenseRepository.save(expense);
        }

        public List<Expense> getUserExpense(String userIdHash) {

            User user = userRepository.findByUserIdHash(userIdHash);
            if(user == null) {
                throw new RuntimeException("User not found");
            }

            return expenseRepository.findByUserIdHash(user.getUserIdHash());
        }
}
