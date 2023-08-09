package kz.bitlab.G114security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/")
  public String homePage() {
    return "home";
  }

  @PreAuthorize("isAnonymous()")
  @GetMapping("/sign-in")
  public String signInPage() {
    return "sign-in";
  }

}
