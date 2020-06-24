package com.master.sbproject.dao;

import com.master.sbproject.model.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface UserDao {

  List<User> getAllUsers();
  Optional<User> getUserById(UUID userId);
  int updateUser(User user);
  int removeUser(UUID userId);
  int insertUser(UUID userUuid,User user);

}
