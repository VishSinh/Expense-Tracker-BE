package live.vishsinh.expensetracker.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(nullable = false, name = "user_id_hash")
    private String userIdHash;

    @Column(nullable = false,name = "password_hash")
    private String passwordHash;

    @Column(nullable = false, name = "phone_number")
    private String phoneNumber;

    @Column(nullable = false, length = 20)
    private String username;

    @Column(name = "group_id")
    private Long groupId;  // Nullable group_id

    // Constructors
    public User() {
    }

    public User(String userIdHash, String passwordHash, String phoneNumber, String username) {
        this.userIdHash = userIdHash;
        this.passwordHash = passwordHash;
        this.phoneNumber = phoneNumber;
        this.username = username;
    }

    // setters
    public void setUserIdHash(String userIdHash) {
        this.userIdHash = userIdHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

}
