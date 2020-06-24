package com.master.sbproject.service;

import com.master.sbproject.dao.UserDao;
import com.master.sbproject.model.User;
import com.master.sbproject.model.User.Gender;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserDao userDao;

  public UserService(UserDao userDao) {
    this.userDao = userDao;
  }
  public List<User> getAllUsers(Optional<String> gender){
    List<User> allUsers = userDao.getAllUsers();
    if(!gender.isPresent()){
      return allUsers;
    }
    try{
      Gender theGender = Gender.valueOf(gender.get().toUpperCase());
      return allUsers.stream()
          .filter( user -> user.getGender().equals(theGender))
          .collect(Collectors.toList());
    } catch (Exception e){
      throw new IllegalStateException("Invalid gender", e);
    }

  }

  public Optional<User> getUserById(UUID userId){

    return userDao.getUserById(userId);
  }

  public int updateUser(User user){
    Optional<User> userFromDb = userDao.getUserById(user.getId());
    if(userFromDb.isPresent()){
      return userDao.updateUser(user);
      //Va retourner 1

    } else {
      return -1;
    }

  }
  public int removeUser(UUID userId){
    Optional<User> userFromDb = userDao.getUserById(userId);
    if(userFromDb.isPresent()){
      return userDao.removeUser(userId);
      //Va retourner 1
    } else
      return -1;
  }

  public int insertUser(User user){
    UUID userUuid = UUID.randomUUID();
    user.setId(userUuid);
    return userDao.insertUser(userUuid, user);

  }
}
