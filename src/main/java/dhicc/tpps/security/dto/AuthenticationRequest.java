package dhicc.tpps.security.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationRequest {
    //    TODO:: validation
    private String email;
    private String password;
}
