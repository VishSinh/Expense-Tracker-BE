package live.vishsinh.expensetracker.entity;

import jakarta.persistence.*;
import java.util.List;


@Entity
@Table(name = "user_groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;

    @Column(nullable = false)
    private String groupName;


    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Member> members;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Expense> expenses;


    //Constructors

    public Group() {
    }


    public Group(Long groupId, String groupName, List<Member> members, List<Expense> expenses) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.members = members;
        this.expenses = expenses;
    }


    //Getters and Setters


    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }
}
