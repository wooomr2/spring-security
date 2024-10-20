package dhicc.tpps.api.v1.auth;

import dhicc.tpps.api.v1.auth.request.SignupRequest;
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
}
