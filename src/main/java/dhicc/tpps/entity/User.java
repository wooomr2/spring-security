package dhicc.tpps.entity;

import dhicc.tpps.enums.ENUM_ROLE;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_member")
public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String password;

    //    TODO:: 다중롤
    @Enumerated(EnumType.STRING)
    private ENUM_ROLE role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public String getUsername() {
        return email;
    }
}
