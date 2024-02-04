package live.vishsinh.expensetracker.repository;

import live.vishsinh.expensetracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>{
    Boolean existsByPhoneNumber(String phoneNumber);
    User findByPhoneNumber(String phoneNumber);
    User findByUserIdHash(String userIdHash);
    List<User> findAllByGroupId(Long groupId);
}
