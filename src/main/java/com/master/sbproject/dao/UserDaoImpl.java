package com.master.sbproject.dao;

import com.master.sbproject.model.User;
import com.master.sbproject.model.User.Gender;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao{

  private Map<UUID, User> database;

  public UserDaoImpl() {
    database =  new HashMap<>();
    UUID joeUserUid = UUID.randomUUID();
    database.put(joeUserUid, new User(joeUserUid,"Joe","Jones", Gender.MALE, 22, "joe.jones@gmail.com"));
  }

  @Override
  public List<User> getAllUsers() {
    return new ArrayList<User>(database.values());
  }

  @Override
  public Optional<User> getUserById(UUID userId) {
    return Optional.ofNullable(database.get(userId));
  }

  @Override
  public int updateUser(User user) {
    database.put(user.getId(),user);
    return 1;
  }

  @Override
  public int removeUser(UUID userId) {
    database.remove(userId);
    return 1;
  }

  @Override
  public int insertUser(UUID userUuid, User user) {
    database.put(userUuid,user);
    return 1;
  }
}
