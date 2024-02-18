package live.vishsinh.expensetracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "group_table")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "group_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID groupId;

    @Column(name = "admin_id", nullable = false)
    private UUID adminId;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(name = "group_name", nullable = false, length = 20)
    private String groupName;

    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();

    // Constructors
    public Group() {
    }

    public Group(UUID adminId, String password, String groupName) {
        this.adminId = adminId;
        this.password = password;
        this.groupName = groupName;
    }
}
