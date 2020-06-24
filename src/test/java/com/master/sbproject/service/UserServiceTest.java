package com.master.sbproject.service;

import static com.master.sbproject.model.User.Gender.FEMALE;
import static com.master.sbproject.model.User.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import com.master.sbproject.dao.UserDaoImpl;
import com.master.sbproject.model.User;
import com.master.sbproject.model.User.Gender;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

class UserServiceTest {

  @Mock
  private UserDaoImpl userDao;

  private UserService userService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    userService = new UserService(userDao);

  }

  @Test
  void shouldGetAllUsers() throws Exception{
    UUID annaUserUuid = UUID.randomUUID();
    User anna = new User(annaUserUuid, "Anna", "Montana", FEMALE, 30, "anna@gmail.com");
    List<User> users = new ArrayList<>(Arrays.asList(anna));
    List<User> unmodifiableList = Collections.unmodifiableList(users);

    given(userDao.getAllUsers()).willReturn(unmodifiableList);

    List<User> allUsers = userService.getAllUsers(Optional.empty());
    User user = allUsers.get(0);
    assertThat(allUsers).hasSize(1);
    assertThat(user.getAge()).isEqualTo(30);
    assertThat(user.getFirstName()).isEqualTo("Anna");
    assertThat(user.getLastName()).isEqualTo("Montana");
    assertThat(user.getEmail()).isEqualTo("anna@gmail.com");
    assertThat(user.getGender()).isEqualTo(FEMALE);
    assertThat(user.getId()).isNotNull();
  }

  @Test
  public void shouldGetAllUsersByGender() throws Exception{

    UUID annaUserUuid = UUID.randomUUID();
    User anna = new User(annaUserUuid, "Anna", "Montana", FEMALE, 30, "anna@gmail.com");
    UUID joeUserUuid = UUID.randomUUID();
    User joe = new User(joeUserUuid, "Joe", "Jones", MALE, 22, "joe@gmail.com");

    List<User> users = new ArrayList<>(Arrays.asList(anna, joe));
    List<User> unmodifiableList = Collections.unmodifiableList(users);

    given(userDao.getAllUsers()).willReturn(unmodifiableList);
    List<User> filteredUsers = userService.getAllUsers(Optional.of("female"));

    assertThat(filteredUsers).hasSize(1);
    assertUserFields(filteredUsers.get(0));
  }

  @Test
  public void shouldThrowExceptionGenderIsInvalid()  throws Exception{
    assertThatThrownBy(() -> userService.getAllUsers(Optional.of("sdsqfdgfgdf")))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("Invalid gender");
  }

  @Test
  void shouldGetUserById() {
    UUID annaUserUuid = UUID.randomUUID();
    User anna = new User(annaUserUuid, "Anna", "Montana", FEMALE, 30, "anna@gmail.com");
    /* Je mocke le userDao: le userDao doit me retourner anna */
    given(userDao.getUserById(annaUserUuid)).willReturn(Optional.of(anna));

    Optional<User> userOptional = userService.getUserById(annaUserUuid);
    assertThat(userOptional.isPresent()).isTrue();

    assertThat(userOptional.get().getAge()).isEqualTo(30);
    assertThat(userOptional.get().getFirstName()).isEqualTo("Anna");
    assertThat(userOptional.get().getLastName()).isEqualTo("Montana");
    assertThat(userOptional.get().getEmail()).isEqualTo("anna@gmail.com");
    assertThat(userOptional.get().getGender()).isEqualTo(FEMALE);
    assertThat(userOptional.get().getId()).isNotNull();
  }

  @Test
  void shouldUpdateUser() {
    UUID annaUserUuid = UUID.randomUUID();
    User anna = new User(annaUserUuid, "Anna", "Montana", FEMALE, 30, "anna@gmail.com");

    //---------------Mocking part-----------
    //Je mocke le userDao qui doit me retourner anna
    given(userDao.getUserById(annaUserUuid)).willReturn(Optional.of(anna));
    //Je mocke le userDao/updateUser qui doit me retourner 1 (dans le user service)
    given(userDao.updateUser(anna)).willReturn(1);
    //------------------------------------------

    //Je crée un argumentCaptor de Mockito pour pouvoir capturer un élément class (Objet complexe)
    ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

    int update = userService.updateUser(anna);

    //je vérifie que le userDao me retourne bien annaUserId et que l'élément capturé est bien une instance de la classe User
    verify(userDao).getUserById(annaUserUuid);
    verify(userDao).updateUser(captor.capture());

    //J'affirme que le résultat est bien égal à 1
    assertThat(update).isEqualTo(1);
    User user = captor.getValue();
    assertUserFields(user);


  }

  @Test
  void shouldRemoveUser() {
    UUID annaUserUuid = UUID.randomUUID();
    User anna = new User(annaUserUuid, "Anna", "Montana", FEMALE, 30, "anna@gmail.com");
    //----Mocking part------
    given(userDao.getUserById(annaUserUuid)).willReturn(Optional.of(anna));
    given((userDao.removeUser(annaUserUuid))).willReturn(1);
    //----------------------
    int removeResult = userService.removeUser(annaUserUuid);
    verify(userDao).getUserById(annaUserUuid);
    assertThat(removeResult).isEqualTo(1);

  }

  @Test
  void shouldInsertUser() {
    UUID annaUserUuid = UUID.randomUUID();
    User anna = new User(annaUserUuid, "Anna", "Montana", FEMALE, 30, "anna@gmail.com");
    //--Mocking part----------on doit utiliser eq avec any
    given(userDao.insertUser(any(UUID.class), eq(anna))).willReturn(1);
    //----------------
    ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

    int insertedUser = userService.insertUser(anna);
    verify(userDao).insertUser(any(UUID.class), captor.capture());
    User user = captor.getValue();
    assertUserFields(user);
    assertThat(insertedUser).isEqualTo(1);


  }

  private void assertUserFields(User user){
    assertThat(user.getAge()).isEqualTo(30);
    assertThat(user.getFirstName()).isEqualTo("Anna");
    assertThat(user.getLastName()).isEqualTo("Montana");
    assertThat(user.getEmail()).isEqualTo("anna@gmail.com");
    assertThat(user.getGender()).isEqualTo(FEMALE);
    assertThat(user.getId()).isNotNull();
  }

}