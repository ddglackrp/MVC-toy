package dnf.itemauction.web.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import dnf.itemauction.domain.character.Character;
import dnf.itemauction.domain.dnf.DnfService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
class JsonServiceTest {

    @InjectMocks
    JsonService jsonService;

    @Spy
    DnfService dnfService;

    @Test
    @DisplayName("Json to Character Test")
    void jsonTest() throws ParseException, JsonProcessingException {
        Optional<Character> emptyCharacter = jsonService.jsonToCharacter("카인", "asdf갈릴레오", "test");

        assertThat(emptyCharacter).isEmpty();

        Optional<Character> character = jsonService.jsonToCharacter("카인", "명장동핵펀치", "test");

        assertThat(character).isPresent();
        assertThat(character.get().getName()).isEqualTo("명장동핵펀치");
        assertThat(character.get().getServer()).isEqualTo("cain");
        assertThat(character.get().getMemberName()).isEqualTo("test");
    }
}