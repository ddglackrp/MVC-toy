package dnf.itemauction.web.character;

import dnf.itemauction.domain.character.Character;
import dnf.itemauction.domain.character.CharacterService;
import dnf.itemauction.domain.member.Member;
import dnf.itemauction.web.character.form.CharacterSaveForm;
import dnf.itemauction.web.json.JsonService;
import dnf.itemauction.web.session.SessionConst;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CharacterController.class)
class CharacterControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    CharacterService characterService;

    @MockBean
    JsonService jsonService;

    MockHttpSession session = new MockHttpSession();

    @Test
    @DisplayName("보유중인 내 캐릭터 보기 - Get")
    void charactersGetTest() throws Exception {
        Character character1 = Character.builder()
                .name("젊음 자유 광란")
                .server("카인")
                .memberName("test")
                .build();

        Character character2 = Character.builder()
                .name("돈절래")
                .server("카인")
                .memberName("test")
                .build();

        List<Character> characters = new ArrayList<>();
        characters.add(character1);
        characters.add(character2);
        session.setAttribute(SessionConst.LOGIN_MEMBER,new Member());
        given(characterService.findCharactersByMember(any())).willReturn(characters);

        mvc.perform(get("/characters").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("characters/characters"))
                .andExpect(model().attribute("characters",characters))
                .andDo(print());
    }

    @Test
    @DisplayName("캐릭터 상세 정보 조회 - Get")
    void characterDetailGetTest() throws Exception {
        Long characterId = 40000L;
        Character character = Character.builder()
                .name("젊음 자유 광란")
                .server("카인")
                .job("e평q평r평")
                .fame(characterId)
                .level(Long.valueOf(1))
                .memberName("test")
                .build();

        session.setAttribute(SessionConst.LOGIN_MEMBER,new Member());
        given(characterService.findOneById(characterId)).willReturn(character);

        mvc.perform(get("/characters/{characterId}",characterId))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"))
                .andDo(print());

        mvc.perform(get("/characters/{characterId}",characterId).session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("characters/character"))
                .andExpect(model().attribute("character",character))
                .andDo(print());
    }

    @Test
    @DisplayName("add Form get")
    void addFormGet() throws Exception {
        session.setAttribute(SessionConst.LOGIN_MEMBER,new Member());
        mvc.perform(get("/characters/add").session(session))
                .andExpect(status().isOk())
                .andExpect(model().attribute("character",new Character()))
                .andExpect(view().name("characters/addForm"))
                .andDo(print());
    }

    @Test
    @DisplayName("add Character Post")
    void addCharacterPost() throws Exception {
        session.setAttribute(SessionConst.LOGIN_MEMBER,new Member());

        //HTML FORM BLANK
        mvc.perform(post("/characters/add").session(session))
                .andExpect(status().isOk())
                .andExpect(model().attribute("character",new CharacterSaveForm()))
                .andExpect(view().name("characters/addForm"));

        // NOT CORRECT CHARACTER NAME
        given(jsonService.jsonToCharacter(any(),any(),any())).willReturn(Optional.empty());
        CharacterSaveForm csf = CharacterSaveForm.builder()
                .name("돈절래")
                .server("cain")
                .build();

        mvc.perform(post("/characters/add").param("name","돈절래").param("server","cain").session(session).requestAttr("memberName","test"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("character",csf))
                .andExpect(view().name("characters/addForm"));

        // ALREADY HAVE CHARACTER
        Optional<Character> character = Optional.of(new Character());
        given(jsonService.jsonToCharacter(any(),any(),any())).willReturn(character);
        given(characterService.join(any(),any())).willReturn(null);

        mvc.perform(post("/characters/add").param("name","돈절래").param("server","cain").session(session).requestAttr("memberName","test"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("character",csf))
                .andExpect(view().name("characters/addForm"));

        //CORRECT
        given(characterService.join(any(),any())).willReturn(new Character());
        mvc.perform(post("/characters/add").param("name","돈절래").param("server","cain").session(session).requestAttr("memberName","test"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/characters"))
                .andExpect(redirectedUrl("/characters"))
                .andDo(print());
    }

    @Test
    @DisplayName("캐릭터 삭제 테스트")
    void deleteTest() throws Exception {
        session.setAttribute(SessionConst.LOGIN_MEMBER,new Member());
        Long characterId = 1L;
        Character character = Character.builder()
                .name("돈절래")
                .server("cain")
                .fame(10L)
                .build();
        given(characterService.deleteOneById(characterId)).willReturn(character);

        mvc.perform(post("/characters/delete/{characterId}",characterId).session(session))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/characters"))
                .andExpect(redirectedUrl("/characters"))
                .andDo(print());
    }

    @Test
    @DisplayName("캐릭터 랭킹 테스트")
    void rankTest() throws Exception {
        session.setAttribute(SessionConst.LOGIN_MEMBER,new Member());

        Character c1 = Character.builder()
                .name("c1")
                .fame(10L)
                .build();

        Character c2 = Character.builder()
                .name("c2")
                .fame(11L)
                .build();

        Character c3 = Character.builder()
                .name("c3")
                .fame(12L)
                .build();
        
        List<Character> characters = new ArrayList<>();
        characters.add(c1);
        characters.add(c2);
        characters.add(c3);

        given(characterService.findCharacters()).willReturn(characters);
        mvc.perform(get("/characters/rank").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("characters/characterRank"))
                .andExpect(model().attribute("characters",characters))
                .andDo(print());

    }


}