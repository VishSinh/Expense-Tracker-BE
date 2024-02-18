package live.vishsinh.expensetracker.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "active_sessions")
public class ActiveSession {

    @Id
    @Column(name = "user_id_hash", nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private String token;

    @Column(name = "create_date_time", nullable = false)
    private Date createDateTime;

    public ActiveSession() {
    }

    public ActiveSession(UUID userIdHash, String token, Date createDateTime) {
        this.userId = userIdHash;
        this.token = token;
        this.createDateTime = createDateTime;
    }
}
