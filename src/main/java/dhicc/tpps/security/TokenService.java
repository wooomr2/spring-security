package dhicc.tpps.security;

import dhicc.tpps.entity.RefreshToken;
import dhicc.tpps.entity.User;
import dhicc.tpps.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public List<RefreshToken> findValidRefreshTokens(User user) {
        return refreshTokenRepository.findAllByUserIdAndExpiredAndRevoked(user.getId(), false, false);
    }

    public void revokeAllRefreshTokens(User user) {
        List<RefreshToken> validTokens = this.findValidRefreshTokens(user);
        if (validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
        });

        refreshTokenRepository.saveAll(validTokens);
    }

    public void saveNewRefreshToken(User user, String refreshToken) {
        refreshTokenRepository.save(RefreshToken.builder()
                .userId(user.getId())
                .refreshToken(refreshToken)
                .expired(false)
                .revoked(false)
                .build());
    }
}
