package com.example.security_live_coding;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  // @Column(unique=true)
  private String username;
  // private String firstName; YVens
  // private String lastName; Belaston

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  private List<RoleEntity> roles = new ArrayList<>();

  public UserEntity() {
  };

  public UserEntity(String username) {
    this.username = username;
  };

  public int getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<RoleEntity> getRoles() {
    return roles;
  }

  public void setRoles(List<RoleEntity> roles) {
    this.roles = roles;
  }

}
