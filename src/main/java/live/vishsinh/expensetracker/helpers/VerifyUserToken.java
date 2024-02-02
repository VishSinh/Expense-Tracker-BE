package live.vishsinh.expensetracker.helpers;

import live.vishsinh.expensetracker.repository.ActiveSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class VerifyUserToken {

    @Autowired
    private ActiveSessionRepository activeSessionRepository;

    public boolean verifyToken(String token, String userIdHash) {
        token = token.substring(7);
        return activeSessionRepository.existsByUserIdHashAndToken(userIdHash, token);
    }
}
