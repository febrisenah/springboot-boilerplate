package javaApp.users.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private UUID id;
    private String email;
    private String password;
    private Boolean isActive;
    private Boolean isLogin;

    private RoleDetails role;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RoleDetails {
        private UUID id;
        private String roleName;
    }
}
