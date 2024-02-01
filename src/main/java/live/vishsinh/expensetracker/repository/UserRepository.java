package live.vishsinh.expensetracker.repository;

import live.vishsinh.expensetracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
}
