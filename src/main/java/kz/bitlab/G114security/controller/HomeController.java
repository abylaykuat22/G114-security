package kz.bitlab.G114security.controller;

import kz.bitlab.G114security.models.User;
import kz.bitlab.G114security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
  @Autowired
  private UserService userService;

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/")
  public String homePage() {
    return "home";
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/profile")
  public String profilePage() {
    System.out.println("zahodit");
    return "profile";
  }

  @PreAuthorize("isAnonymous()")
  @GetMapping("/sign-in")
  public String signInPage() {
    return "sign-in";
  }

  @PreAuthorize("isAnonymous()")
  @GetMapping("/register")
  public String signUpPage() {
    return "sign-up";
  }

//  @PreAuthorize("isAnonymous()")
//  @PostMapping("/register")
//  public String register(User user, @RequestParam String rePassword) {
////    String result = userService.addUser(user, rePassword);
//    return "redirect:/"+result;
//  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/update-password")
  public String updatePassword(@RequestParam String currentPassword,
      @RequestParam String newPassword,
      @RequestParam String reNewPassword) {
    String result = userService.updatePassword(currentPassword, newPassword, reNewPassword);
    return "redirect:/"+result;
  }

}
