package dnf.itemauction.web.login;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginForm {

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

    public LoginForm() {}

    public LoginForm(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
