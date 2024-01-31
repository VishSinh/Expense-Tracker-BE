package live.vishsinh.expensetracker.service;

import live.vishsinh.expensetracker.entity.Expense;
import live.vishsinh.expensetracker.repository.ExpenseRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ExpenseService {

        @Autowired
        private ExpenseRepository expenseRepository;

        public List<Expense> getAllExpenses() {
            return expenseRepository.findAll();
        }

        public Expense getExpenseById(Long id) {
            return expenseRepository.findById(id).orElse(null);
        }

        public Expense createExpense(Expense expense) {
            return expenseRepository.save(expense);
        }

//        public Expense updateExpense(Expense expense) {
//            Expense existingExpense = expenseRepository.findById(expense.getId()).orElse(null);
//            existingExpense.setAmount(expense.getAmount());
//            existingExpense.setPaidBy(expense.getPaidBy());
//            existingExpense.setPaidFor(expense.getPaidFor());
//            existingExpense.setGroup(expense.getGroup());
//            return expenseRepository.save(existingExpense);
//        }

        public String deleteExpense(Long id) {
            expenseRepository.deleteById(id);
            return "Expense removed !! " + id;
        }
}
