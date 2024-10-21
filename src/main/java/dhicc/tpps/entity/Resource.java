package dhicc.tpps.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@ToString
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
@Table(name = "tb_resource")
public class Resource implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "resource_id")
    private Long id;

    @Column(name = "resource_name")
    private String resourceName;

    @Column(name = "http_method")
    private String httpMethod;

    @Column(name = "order_num")
    private int orderNum;

    @Column(name = "resource_type")
    private String resourceType;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "role_resource", joinColumns = {@JoinColumn(name = "resource_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    @ToString.Exclude
    private Set<Role> roleSet = new HashSet<>();
}
