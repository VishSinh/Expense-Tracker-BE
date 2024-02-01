package live.vishsinh.expensetracker.entity;

import jakarta.persistence.*;

import java.util.Date;

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

    @Column(name = "user_id", nullable = false)
    private Long userId;

    // Constructors
    public Expense() {
    }

    public Expense(Long expenseId, Double amount, String description, Date date, Long userId) {
        this.expenseId = expenseId;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.userId = userId;
    }

    // Getters and Setters

    public Long getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Long expenseId) {
        this.expenseId = expenseId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
