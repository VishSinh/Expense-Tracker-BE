package live.vishsinh.expensetracker.service;

import jakarta.transaction.Transactional;
import live.vishsinh.expensetracker.entity.ActiveSession;
import live.vishsinh.expensetracker.entity.User;
import live.vishsinh.expensetracker.helpers.JwtTokenProvider;
import live.vishsinh.expensetracker.helpers.Sha256HashGenerator;
import live.vishsinh.expensetracker.repository.ActiveSessionRepository;
import live.vishsinh.expensetracker.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.Date;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActiveSessionRepository activeSessionRepository;

    @Autowired
    private JwtTokenProvider JwtTokenProvider;

    @Autowired
    private Sha256HashGenerator Sha256HashGenerator;

    public Object signUpUser(String phoneNumber, String username,String password) {

        if (userRepository.existsByPhoneNumber(phoneNumber)){
            throw new RuntimeException("Phone number already in use. Please login instead.");
        }

        Date now = new Date();

        // Create a new user
        User user = new User();
        user.setUserIdHash(Sha256HashGenerator.generateSha256Hash(phoneNumber));
        user.setPasswordHash(Sha256HashGenerator.generateSha256Hash(password));
        user.setPhoneNumber(phoneNumber);
        user.setUsername(username);

        // Generate a token
        String token = JwtTokenProvider.generateToken(user.getUserIdHash());

        // Create a new active session
        ActiveSession activeSession = new ActiveSession(user.getUserIdHash(), token, now);

        // Save the user
        userRepository.save(user);

        return activeSessionRepository.save(activeSession);
    }

    @Transactional
    public Object loginUser(String phoneNumber, String password) {
        // Find user by phone number
        User user = userRepository.findByPhoneNumber(phoneNumber);

        // Check if user exists
        if (user == null) {
            throw new RuntimeException("Error: User not found");
        }

        // Check password
        if (!Objects.equals(Sha256HashGenerator.generateSha256Hash(password), user.getPasswordHash())) {
            throw new RuntimeException("Error: Invalid password");
        }

        String userIdHash = user.getUserIdHash();

        //Check if their exists a Active Session record and delete it
        if(activeSessionRepository.existsByUserIdHash(userIdHash)){
            activeSessionRepository.deleteByUserIdHash(userIdHash);
        }

        // Generate a token
        String token = JwtTokenProvider.generateToken(user.getUserIdHash());
        ActiveSession activeSession =  new ActiveSession(userIdHash, token, new Date());

        // Generate and return the token
        return activeSessionRepository.save(activeSession);
    }
}


