package live.vishsinh.expensetracker.repository;

import live.vishsinh.expensetracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface ExpenseRepository extends JpaRepository<Expense, UUID>{
    List<Expense> findByUserId(UUID userId);
    List<Expense> findByGroupId(UUID groupId);
}
