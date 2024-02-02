package live.vishsinh.expensetracker.repository;

import live.vishsinh.expensetracker.entity.ActiveSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActiveSessionRepository extends JpaRepository<ActiveSession, Long> {
    boolean existsByUserIdHash(String userIdHash);
    void deleteByUserIdHash(String userIdHash);

    Boolean existsByUserIdHashAndToken(String userIdHash, String token);
}
