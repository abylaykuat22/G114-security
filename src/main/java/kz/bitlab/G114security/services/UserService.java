package kz.bitlab.G114security.services;

import java.util.ArrayList;
import java.util.List;
import kz.bitlab.G114security.models.Role;
import kz.bitlab.G114security.models.User;
import kz.bitlab.G114security.repository.RoleRepository;
import kz.bitlab.G114security.repository.UserRepository;
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

  public String addUser(User newUser, String rePassword) {
    User checkUser = userRepository.findByEmail(newUser.getEmail());
    if (checkUser != null) {
      return "register?emailError";
    }
    if (!newUser.getPassword().equals(rePassword)) {
      return "register?passwordsError";
    }
    newUser.setPassword(passwordEncoder.encode(rePassword));
    Role userRole = roleRepository.findRoleUser();
    newUser.setRoles(List.of(userRole));
    userRepository.save(newUser);
    return "sign-in?success";
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
}
