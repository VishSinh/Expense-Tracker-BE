package live.vishsinh.expensetracker.repository;

import live.vishsinh.expensetracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ExpenseRepository extends JpaRepository<Expense, Long>{

    List<Expense> findByUserIdHash(String userIdHash);
}
