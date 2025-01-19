package sy.internproject.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import sy.internproject.domain.enums.AuthorityEnum;

@Entity
@Getter
public class Authority extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authority_id")
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthorityEnum authorityEnum;
}
