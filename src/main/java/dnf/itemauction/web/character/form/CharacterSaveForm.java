package dnf.itemauction.web.character.form;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CharacterSaveForm {

    @NotBlank
    private String name;

    @NotBlank
    private String server;

    public CharacterSaveForm(){}

    @Builder
    public CharacterSaveForm(String name, String server) {
        this.name = name;
        this.server = server;
    }
}
