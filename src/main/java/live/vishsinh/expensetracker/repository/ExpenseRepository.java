package live.vishsinh.expensetracker.repository;

import live.vishsinh.expensetracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ExpenseRepository extends JpaRepository<Expense, Long>{

}
