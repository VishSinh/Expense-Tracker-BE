package live.vishsinh.expensetracker.service;

import jakarta.transaction.Transactional;
import live.vishsinh.expensetracker.entity.ActiveSession;
import live.vishsinh.expensetracker.entity.Group;
import live.vishsinh.expensetracker.entity.User;
import live.vishsinh.expensetracker.helpers.JwtTokenProvider;
import live.vishsinh.expensetracker.helpers.Sha256HashGenerator;
import live.vishsinh.expensetracker.repository.ActiveSessionRepository;
import live.vishsinh.expensetracker.repository.UserRepository;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

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

    public Object signUpUser(String phoneNumber, String name, String password) {

        if (userRepository.existsByPhoneNumber(phoneNumber))
            throw new RuntimeException("Phone number already in use. Please login instead.");

        // Create a new user by hashing the password
        User user = new User(Sha256HashGenerator.generateSha256Hash(password), phoneNumber, name);

        // Save the user
        userRepository.save(user);

        // Generate a token
        String token = JwtTokenProvider.generateToken(user.getUserId().toString());

        // Create a new active session
        ActiveSession activeSession = new ActiveSession(user.getUserId(), token, new Date());
        activeSessionRepository.save(activeSession);

        return new HashMap<>(Map.of("user_id", activeSession.getUserId(), "token", activeSession.getToken()));
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
        if (!Objects.equals(Sha256HashGenerator.generateSha256Hash(password), user.getPassword())) {
            throw new RuntimeException("Error: Invalid password");
        }

        UUID userId = user.getUserId();

        //Check if their exists an Active Session record and delete it
        if (activeSessionRepository.existsByUserId(userId)) activeSessionRepository.deleteByUserId(userId);

        // Generate a token
        String token = JwtTokenProvider.generateToken(user.getUserId().toString());

        // Create a new active session
        ActiveSession activeSession = new ActiveSession(userId, token, new Date());
        activeSessionRepository.save(activeSession);

        return new HashMap<>(Map.of("user_id", activeSession.getUserId(), "token", activeSession.getToken()));
    }

    @Transactional
    public Object resetPassword(UUID userId, String oldPassword, String newPassword) throws BadRequestException {

        User user = userRepository.findByUserId(userId);

        // Check if user exists
        if (user == null) {
            throw new BadRequestException("Error: User not found");
        }

        // Check password
        if (!Objects.equals(Sha256HashGenerator.generateSha256Hash(oldPassword), user.getPassword())) {
            throw new BadRequestException("Error: Invalid password");
        }

        // Update the password
        user.setPassword(Sha256HashGenerator.generateSha256Hash(newPassword));
        userRepository.save(user);

        //Check if their exists an Active Session record and delete it
        if (!activeSessionRepository.existsByUserId(userId))
            throw new RuntimeException("Error: User not logged in");
        activeSessionRepository.deleteByUserId(userId);

        // Generate a token
        String token = JwtTokenProvider.generateToken(user.getUserId().toString());

        // Create a new active session and save it
        activeSessionRepository.save(new ActiveSession(userId, token, new Date()));

        return new HashMap<>(Map.of("user_id", userId,"token", token));
    }

    public Object fetchUserDetails(UUID userId) throws BadRequestException {

        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new BadRequestException("Error: User not found");
        }

        Set<Group> groups = user.getGroups();
        List<UUID> groupIds = new ArrayList<>();
        for (Group group : groups) {
            groupIds.add(group.getGroupId());
        }

        return new HashMap<>(Map.of("phone_number", user.getPhoneNumber(), "name", user.getName(), "group_ids", groupIds));
    }
}


