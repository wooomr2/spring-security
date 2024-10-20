package dhicc.tpps.security.handler;

import dhicc.tpps.security.TokenService;
import dhicc.tpps.entity.User;
import dhicc.tpps.repository.UserRepository;
import dhicc.tpps.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        final String jwtToken = authHeader.substring(7);
        final String username = jwtUtil.extractUsername(jwtToken);

        User user = userRepository.findByEmail(username).orElseThrow();

        tokenService.revokeAllRefreshTokens(user);

        SecurityContextHolder.clearContext();
    }
}
