package dnf.itemauction.domain.member;

import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class Member {
    private Long id;


    @NotBlank
    private String loginId;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    public Member() {};

    @Builder
    public Member(String loginId, String name, String password) {
        this.loginId = loginId;
        this.name = name;
        this.password = password;
    }
}
