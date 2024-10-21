package dhicc.tpps.api.v1;

import dhicc.tpps.security.manager.CustomDynamicAuthorizationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TestController {

    private final CustomDynamicAuthorizationManager customDynamicAuthorizationManager;

    @GetMapping("/admin/test")
    public String admin() {
        return "admin test";
    }

    @GetMapping("/manager/test")
    public String manager() {
        return "manager test";
    }

    @GetMapping("/user/test")
    public String user() {
        return "user test";
    }

    @GetMapping("/reload")
    public String reload() {
        customDynamicAuthorizationManager.reload();
        return "reload";
    }
}
