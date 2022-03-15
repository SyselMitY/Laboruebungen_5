package cf.soisi.springsecurity.domain;

import cf.soisi.springsecurity.auth.Roles;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class User {
    @Id
    private String email;

    @Enumerated
    @NotNull
    private Roles role;

    @NotBlank
    private String password;



}
