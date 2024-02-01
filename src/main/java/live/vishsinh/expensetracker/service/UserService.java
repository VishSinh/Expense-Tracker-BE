package live.vishsinh.expensetracker.service;

import live.vishsinh.expensetracker.entity.User;
import live.vishsinh.expensetracker.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser( String username) {
        User user = new User(username);
        return userRepository.save(user);
    }
}
