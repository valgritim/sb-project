package com.master.sbproject.controller;

import com.master.sbproject.model.User;
import com.master.sbproject.service.UserService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;
import javax.ws.rs.QueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {

  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

 /* @GetMapping("")
  public List<User> fetchUsers(){
    return userService.getAllUsers();
  }*/

  @GetMapping("")
  public List<User> fetchUsers(@QueryParam("gender") String gender){
    System.out.println(gender);
    return userService.getAllUsers(Optional.ofNullable(gender));
  }

  @GetMapping("/{uuid}")
  public ResponseEntity<?> getUserById(@PathVariable("uuid") UUID uuid){
    Optional<User> userById = userService.getUserById(uuid);
    return userById.<ResponseEntity<?>>map(ResponseEntity::ok)
        .orElseGet(()-> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sorry! User " + uuid + " was not found"));
  }
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Integer>insertUser(@RequestBody @Valid User user){
    int result = userService.insertUser(user);
    if(result == 1){
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.badRequest().build();
  }
  @PutMapping("/")
  public ResponseEntity<Integer> updateUser(@RequestBody User user){
    int result = userService.updateUser(user);

    if(result == 1){
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.badRequest().build();
  }
  @DeleteMapping("{uuid}")
  public ResponseEntity<Integer> deleteUser(@PathVariable ("uuid") UUID uuid){
    int result = userService.removeUser(uuid);
    if(result == 1){
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.badRequest().build();
  }

}


