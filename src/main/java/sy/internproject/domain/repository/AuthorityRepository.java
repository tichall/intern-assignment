package sy.internproject.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sy.internproject.domain.entity.Authority;
import sy.internproject.domain.enums.AuthorityEnum;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByAuthorityEnum(AuthorityEnum authorityEnum);
}
