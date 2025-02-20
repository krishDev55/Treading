package com.trade.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trade.domain.USER_ROLE;
import jakarta.persistence.*;


//@Data
@Entity
@Table(name = "userdetail")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String fullName;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Embedded
    private TwoFactorAuth twoFactorAuth = new TwoFactorAuth();

    public USER_ROLE getRole() {
        return role;
    }

    public void setRole(USER_ROLE role) {
        this.role = role;
    }

    @Enumerated(EnumType.STRING)
    private USER_ROLE role = USER_ROLE.ROLE_CUSTOMER;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
