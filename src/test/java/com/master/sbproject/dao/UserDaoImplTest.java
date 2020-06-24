package com.master.sbproject.dao;

import static com.master.sbproject.model.User.Gender.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.master.sbproject.model.User;
import com.master.sbproject.model.User.Gender;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserDaoImplTest {

  private UserDaoImpl userDao;

  @BeforeEach
  void setUp() {
    userDao = new UserDaoImpl();
  }

  @Test
  void getAllUsers() {
    List<User> users = userDao.getAllUsers();
    assertThat(users).hasSize(1);

    User user = users.get(0);

    assertThat((user.getAge())).isEqualTo(22);
    assertThat(user.getFirstName()).isEqualTo("Joe");
    assertThat(user.getLastName()).isEqualTo("Jones");
    assertThat(user.getEmail()).isEqualTo("joe.jones@gmail.com");
    assertThat(user.getGender()).isEqualTo(MALE);
    assertThat(user.getId()).isNotNull();
  }
    @Test
  void getUserById() {
      UUID annaUserUuid = UUID.randomUUID();
      User anna = new User(annaUserUuid, "Anna", "Montana", Gender.FEMALE, 30, "anna@gmail.com");

      userDao.insertUser(annaUserUuid, anna);

      assertThat(userDao.getAllUsers()).hasSize(2);
      Optional<User> annaOptionnal = userDao.getUserById(annaUserUuid);
      assertThat(annaOptionnal.isPresent()).isTrue();
      assertThat(annaOptionnal.get()).isEqualToComparingFieldByField(anna);
  }

  @Test
  void updateUser() {
    UUID joeUserUuid = userDao.getAllUsers().get(0).getId();
    User newJoe = new User(joeUserUuid, "Anna", "Montana", Gender.FEMALE, 30, "anna@gmail.com");

    userDao.updateUser(newJoe);

    Optional<User> userById = userDao.getUserById(joeUserUuid);
    assertThat(userById.isPresent()).isTrue();
    assertThat(userDao.getAllUsers()).hasSize(1);
    assertThat(userById.get()).isEqualToComparingFieldByField(newJoe);
  }

  @Test
  void removeUser() {
    UUID joeUserUuid = userDao.getAllUsers().get(0).getId();

    userDao.removeUser(joeUserUuid);
    assertThat(userDao.getUserById(joeUserUuid).isPresent()).isFalse();
    assertThat(userDao.getAllUsers()).hasSize(0);
  }

  @Test
  void insertUser() {
    UUID newUserUuid= UUID.randomUUID();
    User newJoe = new User(newUserUuid, "Anna", "Montana", Gender.FEMALE, 30, "anna@gmail.com");

    userDao.insertUser(newUserUuid, newJoe);

    List<User> allUsers = userDao.getAllUsers();
    assertThat(allUsers).hasSize(2);
    assertThat(userDao.getUserById(newUserUuid).get()).isEqualToComparingFieldByField(newJoe);
  }
}