package live.vishsinh.expensetracker.service;

import live.vishsinh.expensetracker.entity.*;
import live.vishsinh.expensetracker.repository.ExpenseRepository;
import live.vishsinh.expensetracker.repository.GroupRepository;
import live.vishsinh.expensetracker.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Service
public class GroupService {

        @Autowired
        private GroupRepository groupRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private ExpenseRepository expenseRepository;


        public Object createGroup(UUID userId, String password, String groupName) throws BadRequestException {

            User user = userRepository.findByUserId(userId);
            if (user == null) {
                throw new BadRequestException("User not found");
            }

            System.out.println("userId: " + userId);

            // Find all the groups in which the user is an admin
            List<Group> groups = groupRepository.findAllByAdminId(userId);
            for(Group group: groups){
                if(group.getGroupName().equals(groupName)){
                    throw new BadRequestException("Group with same name already exists");
                }
            }

            Group newGroup = new Group(userId, password, groupName);
            groupRepository.save(newGroup);

            Set<Group> userGroups = user.getGroups();
            userGroups.add(newGroup);
            user.setGroups(userGroups);
            userRepository.save(user);

            Set<User> groupUsers = newGroup.getUsers();
            groupUsers.add(user);
            newGroup.setUsers(groupUsers);
            groupRepository.save(newGroup);

            List<UUID> userIds = new ArrayList<>();
            for (User groupUser: groupUsers){
                userIds.add(groupUser.getUserId());
            }

            return new HashMap<>(Map.of(
                    "group_id", newGroup.getGroupId(),
                    "group_name", newGroup.getGroupName(),
                    "admin_id", newGroup.getAdminId(),
                    "user_ids", userIds
            ));
        }
//
//        public Object joinGroup(Long groupId, String password, String userIdHash) throws BadRequestException{
//
//            User user = userRepository.findByUserId(UUID.fromString(userIdHash));
//
//            if(user == null){
//                throw new BadRequestException("User not found");
//            }
//
//            Group group = groupRepository.findByGroupId(groupId);
//            if(!Objects.equals(group.getPassword(), password)){
//                throw new RuntimeException("Pleas Check password");
//            }
//
////            user.setGroupId(groupId);
//            userRepository.save(user);
//
//            return user;
//        }
//
//        public Object getGroupExpense(Long groupId) throws BadRequestException{
//            Group group = groupRepository.findByGroupId(groupId);
//            if(group == null){
//                throw new BadRequestException("Group not found");
//            }
//
////            List<User> users = userRepository.findAllByGroupId(groupId);
////            if(users.isEmpty()){
////                throw new BadRequestException("No users found in group");
////            }
//
//            List<Expense> expenses = new ArrayList<>();
//
////            for(User user: users){
////                List<Expense> userExpense = expenseRepository.findByUserIdHash(user.getUserIdHash());
////                expenses.addAll(userExpense);
////            }
//
//            return expenses;
//        }
}
