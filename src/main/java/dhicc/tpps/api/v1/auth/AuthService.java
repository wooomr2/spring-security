package dhicc.tpps.api.v1.auth;

import dhicc.tpps.security.TokenService;
import dhicc.tpps.security.dto.AuthenticationRequest;
import dhicc.tpps.api.v1.auth.request.SignupRequest;
import dhicc.tpps.security.dto.AuthenticationResponse;
import dhicc.tpps.api.v1.user.UserService;
import dhicc.tpps.entity.RefreshToken;
import dhicc.tpps.entity.Role;
import dhicc.tpps.entity.User;
import dhicc.tpps.repository.UserRepository;
import dhicc.tpps.security.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final UserService userService;

    public void signup(SignupRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalStateException("Email already exists");
        }

        userRepository.save(User.builder()
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(Role.RESIDENT)
                .build());
    }

    //TODO:: 삭제
    public AuthenticationResponse login(AuthenticationRequest req) {
        log.info(">>> 로그인 요청: {}", req.getEmail());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );

        User user = userRepository.findByEmail(req.getEmail()).orElseThrow();

        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        tokenService.revokeAllRefreshTokens(user);
        tokenService.saveRefreshToken(user, refreshToken);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse tokenRefresh(HttpServletRequest request) {
        log.info(">>> 토큰 Refresh 요청");

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new JwtException("No refresh token provided");
        }

        final String refreshToken = authHeader.substring(7);

        User user = userService.getCurrentUser();

        List<RefreshToken> dbRefreshTokens = tokenService.findValidRefreshTokens(user);

        if (!jwtUtil.isTokenValid(refreshToken, user) || dbRefreshTokens.stream().noneMatch(token -> token.getRefreshToken().equals(refreshToken))) {
            throw new JwtException("Invalid refresh token");
        }

        return AuthenticationResponse.builder()
                .accessToken(jwtUtil.generateAccessToken(user))
                .refreshToken(refreshToken)
                .build();
    }
}
