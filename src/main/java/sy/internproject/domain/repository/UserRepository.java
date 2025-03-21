package sy.internproject.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sy.internproject.domain.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
