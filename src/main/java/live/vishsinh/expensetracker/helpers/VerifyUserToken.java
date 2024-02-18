package live.vishsinh.expensetracker.helpers;

import live.vishsinh.expensetracker.repository.ActiveSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.security.auth.login.LoginException;
import java.util.UUID;


@Component
public class VerifyUserToken {

    @Autowired
    private ActiveSessionRepository activeSessionRepository;

    public void verifyToken(String token, String userIdHash) throws LoginException {
        if (!activeSessionRepository.existsByUserIdAndToken(UUID.fromString(userIdHash), token.substring(7))) throw new LoginException("Invalid token");
    }
}
