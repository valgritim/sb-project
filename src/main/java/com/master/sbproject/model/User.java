package com.master.sbproject.model;

import java.util.UUID;

public class User {

  private UUID id;
  private String firstName;
  private String lastName;
  private Gender gender;
  private Integer age;
  private String email;

  public enum Gender {
    MALE, FEMALE
  }

  public User(UUID id, String firstName, String lastName,
      Gender gender, Integer age, String email) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.age = age;
    this.email = email;
  }

  public UUID getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public Gender getGender() {
    return gender;
  }

  public Integer getAge() {
    return age;
  }

  public String getEmail() {
    return email;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", gender=" + gender +
        ", age=" + age +
        ", email='" + email + '\'' +
        '}';
  }
}