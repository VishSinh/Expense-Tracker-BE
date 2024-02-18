package live.vishsinh.expensetracker.repository;

import live.vishsinh.expensetracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>{
    Boolean existsByPhoneNumber(String phoneNumber);
    User findByPhoneNumber(String phoneNumber);
    User findByUserId(UUID userId);
}
