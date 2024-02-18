package live.vishsinh.expensetracker.repository;

import live.vishsinh.expensetracker.entity.ActiveSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ActiveSessionRepository extends JpaRepository<ActiveSession, UUID> {
    boolean existsByUserId(UUID userId);
    void deleteByUserId(UUID userId);

    Boolean existsByUserIdAndToken(UUID userId, String token);
}
