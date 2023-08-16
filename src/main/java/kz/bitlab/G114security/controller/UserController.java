package kz.bitlab.G114security.controller;

import java.io.PrintWriter;
import java.util.List;
import kz.bitlab.G114security.models.User;
import kz.bitlab.G114security.repository.UserRepository;
import kz.bitlab.G114security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
  @Autowired
  private UserService userService;
  @Autowired
  private UserRepository userRepository;

  @GetMapping
  public List<User> getUsers() {
    return userRepository.findAll();
  }

  @GetMapping("{id}")
  public User getUser(@PathVariable Long id) {
    return userRepository.findById(id).orElse(null);
  }

  @PostMapping
  public User addUser(@RequestBody User user,
      @RequestParam(name = "re_password") String rePassword) {
    return userService.addUser(user, rePassword);
  }

  @PutMapping
  public User editUser(@RequestBody User user) {
    return userRepository.save(user);
  }

  @DeleteMapping("{id}")
  public void deleteUser(@PathVariable Long id) {
    userRepository.deleteById(id);
  }
}
