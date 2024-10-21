package dhicc.tpps.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_refresh_token")
public class RefreshToken {

    @Id
    @GeneratedValue
    @Column(name = "refresh_token_id")
    public Long id;

    @Column(name = "user_id", nullable = false)
    public Long userId;

    @Column(unique = true, name = "refresh_token", nullable = false)
    public String refreshToken;

    @Column(nullable = false)
    public boolean revoked = false;

    @Column(nullable = false)
    public boolean expired = false;
}
