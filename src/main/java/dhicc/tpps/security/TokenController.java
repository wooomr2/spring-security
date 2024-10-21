package dhicc.tpps.security;

import dhicc.tpps.api.v1.user.UserService;
import dhicc.tpps.entity.RefreshToken;
import dhicc.tpps.entity.User;
import dhicc.tpps.security.dto.AuthenticationResponse;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/token")
@Slf4j
@RequiredArgsConstructor
public class TokenController {

    private final UserService userService;
    private final TokenService tokenService;
    private final JwtUtil jwtUtil;

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> tokenRefresh(HttpServletRequest request) {
        log.info("=== 토큰 Refresh 요청 ===");

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new JwtException("No refresh token provided");
        }

        User user = userService.getCurrentUser();

        final String refreshToken = authHeader.substring(7);
        List<RefreshToken> dbRefreshTokens = tokenService.findValidRefreshTokens(user);

        if (!jwtUtil.isTokenValid(refreshToken, user) ||
                dbRefreshTokens.stream().noneMatch(token -> token.getRefreshToken().equals(refreshToken))) {
            throw new JwtException("Invalid refresh token");
        }

        return ResponseEntity.ok(AuthenticationResponse.builder()
                .accessToken(jwtUtil.generateAccessToken(user))
                .refreshToken(refreshToken)
                .build());
    }
}
