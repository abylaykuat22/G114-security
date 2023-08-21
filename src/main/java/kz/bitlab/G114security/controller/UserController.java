package kz.bitlab.G114security.controller;

import java.util.List;
import kz.bitlab.G114security.dto.UserCreate;
import kz.bitlab.G114security.dto.UserUpdate;
import kz.bitlab.G114security.dto.UserView;
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
  public List<UserView> getUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("{id}")
  public UserView getUser(@PathVariable Long id) {
    return userService.getUserById(id);
  }

  @PostMapping
  public UserView addUser(@RequestBody UserCreate userCreate) {
    return userService.addUser(userCreate);
  }

  @PutMapping
  public User editUser(@RequestBody UserUpdate userUpdate) {
    return userService.editUser(userUpdate);
  }

  @DeleteMapping("{id}")
  public void deleteUser(@PathVariable Long id) {
    userRepository.deleteById(id);
  }
}
