package dnf.itemauction.domain.dnf;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
class DnfServiceTest {

    @Spy
    DnfService dnfService;

    JSONParser jsonParser = new JSONParser();;
    ObjectMapper objectMapper = new ObjectMapper();;

    @Test
    @DisplayName("외부 API 통신을 통한 JSON Get")
    void apiTest() throws ParseException {
        // json 값 존재
        String characterInfo = dnfService.getCharacterInfo("카인", "명장동핵펀치");
        JSONObject jsonObject = (JSONObject) jsonParser.parse(characterInfo);
        Object rows = jsonObject.get("rows");
        JSONArray jsonArray = (JSONArray) rows;
        JSONObject jsonCharacter = (JSONObject) jsonArray.get(0);
        assertThat(jsonCharacter.get("characterId")).isEqualTo("1a2376644a47c856736677450be97da8");

        // json 값 존재x
        characterInfo = dnfService.getCharacterInfo("카인", "aaa명장동핵펀치");
        jsonObject = (JSONObject) jsonParser.parse(characterInfo);
        rows = jsonObject.get("rows");
        jsonArray = (JSONArray) rows;
        assertThat(jsonArray).isEmpty();
    }
}