package dhicc.tpps.api.v1.user;

import dhicc.tpps.api.v1.user.request.ChangePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    
    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest req) {
        userService.changePassword(req);
        return ResponseEntity.ok("비밀번호 변경 성공");
    }
}
