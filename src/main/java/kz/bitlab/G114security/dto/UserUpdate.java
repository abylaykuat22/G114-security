package kz.bitlab.G114security.dto;

import java.util.List;
import kz.bitlab.G114security.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdate {
  private Long id;
  private String email;
  private String currentPassword;
  private String newPassword;
  private String reNewPassword;
  private String fullName;
  private List<Role> roles;
}
