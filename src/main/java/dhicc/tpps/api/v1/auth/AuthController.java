package dhicc.tpps.api.v1.auth;

import dhicc.tpps.security.dto.AuthenticationRequest;
import dhicc.tpps.api.v1.auth.request.SignupRequest;
import dhicc.tpps.security.dto.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signupResident(@RequestBody @Valid SignupRequest req) {
        authService.signup(req);
        return ResponseEntity.ok("입주민 회원가입 성공");
    }

    //TODO:: 삭제
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }

    @PostMapping("/token-refresh")
    public ResponseEntity<AuthenticationResponse> tokenRefresh(HttpServletRequest request) {
        return ResponseEntity.ok(authService.tokenRefresh(request));
    }
}
