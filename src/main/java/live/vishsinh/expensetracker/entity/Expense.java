package live.vishsinh.expensetracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "expense_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID expenseId;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    public Date date;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "group_id")
    private UUID groupId;

    // Constructors
    public Expense() {
    }

    public Expense(Double amount, String description, Date date, UUID userId) {
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.userId = userId;
    }

    public Expense(Double amount, String description, Date date, UUID userId, UUID groupId) {
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.userId = userId;
        this.groupId = groupId;
    }

}
