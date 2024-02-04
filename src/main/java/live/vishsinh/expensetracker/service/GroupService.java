package live.vishsinh.expensetracker.service;

import live.vishsinh.expensetracker.entity.*;
import live.vishsinh.expensetracker.repository.ExpenseRepository;
import live.vishsinh.expensetracker.repository.GroupRepository;
import live.vishsinh.expensetracker.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class GroupService {

        @Autowired
        private GroupRepository groupRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private ExpenseRepository expenseRepository;


        public Object createGroup(String adminId, String password, String groupName) throws BadRequestException {

            User user = userRepository.findByUserIdHash(adminId);
            if(user == null) {
                throw new BadRequestException("User not found");
            }

            if(user.getGroupId() != null){
                throw new RuntimeException("User is already part of a group");
            }

            Group newGroup = new Group(adminId, password, groupName);
            groupRepository.save(newGroup);

            user.setGroupId(newGroup.getGroupId());
            userRepository.save(user);
            return newGroup;
        }

        public Object joinGroup(Long groupId, String password, String userIdHash) throws BadRequestException{
            User user = userRepository.findByUserIdHash(userIdHash);
            if(user == null){
                throw new BadRequestException("User not found");
            }

            Group group = groupRepository.findByGroupId(groupId);
            if(!Objects.equals(group.getPassword(), password)){
                throw new RuntimeException("Pleas Check password");
            }

            user.setGroupId(groupId);
            userRepository.save(user);

            return user;
        }

        public Object getGroupExpense(Long groupId) throws BadRequestException{
            Group group = groupRepository.findByGroupId(groupId);
            if(group == null){
                throw new BadRequestException("Group not found");
            }

            List<User> users = userRepository.findAllByGroupId(groupId);
            if(users.isEmpty()){
                throw new BadRequestException("No users found in group");
            }

            List<Expense> expenses = new ArrayList<>();

            for(User user: users){
                List<Expense> userExpense = expenseRepository.findByUserIdHash(user.getUserIdHash());
                expenses.addAll(userExpense);
            }

            return expenses;
        }
}
