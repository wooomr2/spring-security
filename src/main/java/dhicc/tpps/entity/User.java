package dhicc.tpps.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_user")
public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    //    TODO:: BaseEntity 상속시 Defualt nullable = false 풀릴 때 해결법 찾기
    private String email;

    private String password;

    //    TODO:: 다중롤
    @Enumerated(EnumType.STRING)
    private ENUM_ROLE role;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinTable(name = "tb_user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    @ToString.Exclude
    private Set<Role> userRoles = new HashSet<>();

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }
}
