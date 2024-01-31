package live.vishsinh.expensetracker.service;

import live.vishsinh.expensetracker.entity.Group;
import live.vishsinh.expensetracker.repository.GroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class GroupService {

        @Autowired
        private GroupRepository groupRepository;

        public Iterable<Group> getAllGroups() {
            return groupRepository.findAll();
        }

        public Group getGroupById(Long id) {
            return groupRepository.findById(id).orElse(null);
        }

        public Group createGroup(Group group) {
            return groupRepository.save(group);
        }
}
