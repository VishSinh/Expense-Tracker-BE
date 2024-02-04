package live.vishsinh.expensetracker.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Getter
@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id")
    private Long expenseId;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    public Date date;

    @Column(name = "user_id_hash", nullable = false)
    private String userIdHash;

//    @Column(name = "group_id")
//    private Long groupId;

    // Constructors
    public Expense() {
    }

    public Expense(Long expenseId, Double amount, String description, Date date, String userIdHash) {
        this.expenseId = expenseId;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.userIdHash = userIdHash;
//        this.groupId = groupId;
    }

    // Getters and Setters

    public void setExpenseId(Long expenseId) {
        this.expenseId = expenseId;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setUserId(String userIdHash) {
        this.userIdHash = userIdHash;
    }

//    public void setGroupId(Long groupId) { this.groupId = groupId; }
}
