package live.vishsinh.expensetracker.service;

import live.vishsinh.expensetracker.entity.*;
import live.vishsinh.expensetracker.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class ExpenseService {

        @Autowired
        private ExpenseRepository expenseRepository;

        @Autowired
        private UserRepository userRepository;

        public Expense createExpense(Double amount, Date date, String description, UUID userId, Object groupId) {
            User user = userRepository.findByUserId(userId);
            if(user == null) {
                throw new RuntimeException("User not found");
            }

            if(groupId instanceof UUID) {
                Set<Group> groups = user.getGroups();
                boolean groupFound = false;

                for(Group group: groups) {
                    if(group.getGroupId().equals(groupId)) {
                        groupFound = true;
                        break;
                    }
                }

                if(!groupFound) {
                    throw new RuntimeException("User not in group");
                }
            }

            Expense expense = new Expense(amount, description, date, userId);
            if(groupId instanceof UUID) {
                expense.setGroupId((UUID) groupId);
            }

            return expenseRepository.save(expense);
        }

        public List<Expense> getExpenses(UUID userId, Object groupId) {
            List<Expense> expenses;

            if(groupId instanceof UUID) {
                // CASE 1: Fetch expenses for the group
                Set<Group> groups = userRepository.findByUserId(userId).getGroups();
                boolean groupFound = false;

                for(Group group: groups) {
                    if(group.getGroupId().equals(groupId)) {
                        groupFound = true;
                        break;
                    }
                }

                if(!groupFound) {
                    throw new RuntimeException("User not in group");
                }

                expenses = expenseRepository.findByGroupId((UUID) groupId);
            }else{
                // CASE 2: Fetch expenses for the user (groupId == null)
                expenses = expenseRepository.findByUserId(userId);
            }

            if (expenses.isEmpty()) {
                throw new RuntimeException("No expenses found");
            }

            return expenses;
        }
}
