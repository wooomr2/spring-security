package dhicc.tpps.repository;

import dhicc.tpps.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    List<RefreshToken> findAllByUserIdAndExpiredAndRevoked(Long id, boolean expired, boolean revoked);
}
