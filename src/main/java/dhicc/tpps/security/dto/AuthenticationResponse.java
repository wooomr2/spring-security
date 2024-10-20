package dhicc.tpps.security.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthenticationResponse {
    String accessToken;
    String refreshToken;
}
