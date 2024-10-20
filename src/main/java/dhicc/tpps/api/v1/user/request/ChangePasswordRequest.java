package dhicc.tpps.api.v1.user.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}
