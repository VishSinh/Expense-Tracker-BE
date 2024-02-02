package live.vishsinh.expensetracker.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "user_group")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "admin_id")
    private Long adminId;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(name = "group_name", length = 20)
    private String groupName;

    // Constructors
    public Group() {
    }

    public Group(Long adminId, String password, String groupName) {
        this.adminId = adminId;
        this.password = password;
        this.groupName = groupName;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
