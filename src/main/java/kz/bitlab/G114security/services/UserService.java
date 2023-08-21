package kz.bitlab.G114security.services;

import java.util.List;
import kz.bitlab.G114security.dto.UserCreate;
import kz.bitlab.G114security.dto.UserUpdate;
import kz.bitlab.G114security.dto.UserView;
import kz.bitlab.G114security.mappers.UserMapper;
import kz.bitlab.G114security.models.Role;
import kz.bitlab.G114security.models.User;
import kz.bitlab.G114security.repository.RoleRepository;
import kz.bitlab.G114security.repository.UserRepository;
import org.mapstruct.control.MappingControl.Use;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private RoleRepository roleRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email);
    if (user == null) {
      throw new UsernameNotFoundException("User not found!");
    }
    return user;
  }

  public UserView addUser(UserCreate userCreate) {
    User checkUser = userRepository.findByEmail(userCreate.getCustomEmail());
    if (checkUser != null) {
      return null;
    }
    if (userCreate.getCustomEmail() == null) {
      return null;
    }
    if (!userCreate.getPassword().equals(userCreate.getRePassword())) {
      return null;
    }

    User user = UserMapper.INSTANCE.toEntity(userCreate);
    var bcryptPassword = passwordEncoder.encode(userCreate.getPassword());
    user.setPassword(bcryptPassword);
    Role userRole = roleRepository.findRoleUser();
    user.setRoles(List.of(userRole));
    return UserMapper.INSTANCE.toView(userRepository.save(user));
  }

  public User getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication instanceof AnonymousAuthenticationToken) {
      return null;
    }
    return (User) authentication.getPrincipal();
  }


  public String updatePassword(String currentPassword, String newPassword, String reNewPassword) {
    User user = getCurrentUser();
    if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
      return "profile?currentPasswordError";
    }
    if (!newPassword.equals(reNewPassword)) {
      return "profile?passwordsError";
    }
    user.setPassword(passwordEncoder.encode(newPassword));
    userRepository.save(user);
    return "profile?success";
  }

  public UserView getUserById(Long id) {
    User user = userRepository.findById(id).orElseThrow();
    return UserMapper.INSTANCE.toView(user);
  }

  public User editUser(UserUpdate userUpdate) {
    if (userUpdate == null) {
      return null;
    }
    User currentUser = userRepository.findById(userUpdate.getId()).orElseThrow();
    if (!passwordEncoder.matches(userUpdate.getCurrentPassword(), currentUser.getPassword())) {
      return null;
    }
    if (!userUpdate.getNewPassword().equals(userUpdate.getReNewPassword())) {
      return null;
    }
    User user = UserMapper.INSTANCE.toEntity(userUpdate);
    var bcryptPassword = passwordEncoder.encode(userUpdate.getNewPassword());
    user.setPassword(bcryptPassword);
    return userRepository.save(user);
  }

  public List<UserView> getAllUsers() {
    List<User> users = userRepository.findAll();
    return UserMapper.INSTANCE.toViewList(users);
  }
}
