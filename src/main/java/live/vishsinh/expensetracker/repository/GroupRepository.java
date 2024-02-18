package live.vishsinh.expensetracker.repository;

import live.vishsinh.expensetracker.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface GroupRepository extends JpaRepository<Group, UUID>{
    List<Group> findAllByAdminId(UUID adminId);
//    Group findByGroupName(String groupName);
//    Group findByGroupId(Long groupId);
}
