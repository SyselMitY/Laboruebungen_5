package cf.soisi.springsecurity.domain;

import cf.soisi.springsecurity.auth.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Email
    private String email;

    @Enumerated
    @NotNull
    private Roles role;

    @NotBlank
    private String password;



}
