package live.vishsinh.expensetracker.entity;

import jakarta.persistence.*;

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

    // Getters and Setters
    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
