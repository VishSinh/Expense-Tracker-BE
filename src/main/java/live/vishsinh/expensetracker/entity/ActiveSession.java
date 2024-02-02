package live.vishsinh.expensetracker.entity;


import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Getter
@Entity
@Table(name = "active_sessions")
public class ActiveSession {

    @Id
    @Column(name = "user_id_hash", nullable = false)
    private String userIdHash;

    @Column(nullable = false)
    private String token;

    @Column(name = "create_date_time", nullable = false)
    private Date createDateTime;

    public ActiveSession() {
    }

    public ActiveSession(String userIdHash, String token, Date createDateTime) {
        this.userIdHash = userIdHash;
        this.token = token;
        this.createDateTime = createDateTime;
    }

    public void setUserIdHash(String userIdHash) {
        this.userIdHash = userIdHash;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }
}
