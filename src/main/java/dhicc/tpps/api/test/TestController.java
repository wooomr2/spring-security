package dhicc.tpps.api.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController("/test")
@Slf4j
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @PostMapping("/")
    public ResponseEntity<String> test() {
        testService.test();
        return ResponseEntity.ok("테스트");
    }
}
