package dnf.itemauction.web.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dnf.itemauction.domain.character.Character;
import dnf.itemauction.domain.dnf.DnfService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class JsonService {

    private final DnfService dnfService;
    private final JSONParser jsonParser = new JSONParser();;
    private final ObjectMapper objectMapper = new ObjectMapper();;

    public Optional<Character> jsonToCharacter(String server, String name, String memberName) throws ParseException, JsonProcessingException {
        String characterInfo = dnfService.getCharacterInfo(server, name);

        JSONObject jsonObject = (JSONObject) jsonParser.parse(characterInfo);
        Object rows = jsonObject.get("rows");
        JSONArray jsonArray = (JSONArray) rows;

        if(!jsonValidCheck(jsonArray)) return Optional.empty();

        String json = jsonArray.get(0).toString();
        JsonForm jsonForm = objectMapper.readValue(json, JsonForm.class);

        /*
        생성자 사용 코드
        Character character = new Character(
                jsonForm.getCharacterId(),
                jsonForm.getCharacterName(),
                jsonForm.getServerId(),
                jsonForm.getLevel(),
                jsonForm.getJobGrowName(),
                jsonForm.getFame(),
                memberName
        );
         */

        Character character = Character.builder()
                .characterId(jsonForm.getCharacterId())
                .name(jsonForm.getCharacterName())
                .server(jsonForm.getServerId())
                .level(jsonForm.getLevel())
                .job(jsonForm.getJobGrowName())
                .fame(jsonForm.getFame())
                .memberName(memberName)
                .createdAt(new Date())
                .updateAt(new Date())
                .build();

        log.info("character = {}",character);

        return Optional.of(character);
    }

    public Boolean jsonValidCheck(JSONArray jsonArray){
        if(jsonArray.isEmpty()) return false;
        return true;
    }

}
