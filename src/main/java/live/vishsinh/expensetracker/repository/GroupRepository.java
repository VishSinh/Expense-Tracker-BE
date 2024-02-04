package live.vishsinh.expensetracker.repository;

import live.vishsinh.expensetracker.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GroupRepository extends JpaRepository<Group, Long>{
    Group findByGroupName(String groupName);
    Group findByGroupId(Long groupId);
}
