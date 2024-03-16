package dnf.itemauction.web.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import dnf.itemauction.domain.character.Character;
import dnf.itemauction.domain.character.CharacterService;
import dnf.itemauction.domain.member.Member;
import dnf.itemauction.web.character.form.CharacterSaveForm;
import dnf.itemauction.web.json.JsonService;
import dnf.itemauction.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/characters")
@RequiredArgsConstructor
@Slf4j
public class CharacterController {
    private final CharacterService characterService;

    // private final DnfApiService dnfApiService;
    // private final CharacterRepository characterRepository;

    private final JsonService jsonService;

    @GetMapping
    public String characters(HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        model.addAttribute("characters", characterService.findCharactersByMember(member.getName()));
        return "characters/characters";
    }

    @GetMapping("/{characterId}")
    public String character(@PathVariable long characterId, Model model){
        Character findCharacter = characterService.findOneById(characterId);
        model.addAttribute("character",findCharacter);
        return "characters/character";
    }

    @GetMapping("/add")
    public String addForm(Model model){
        model.addAttribute("character",new Character());
        return "characters/addForm";
    }

    @PostMapping("/add")
    public String addCharacter(@Validated @ModelAttribute("character") CharacterSaveForm characterSaveForm,
                               BindingResult bindingResult,
                               HttpServletRequest request) throws ParseException, JsonProcessingException {

        if(bindingResult.hasErrors()) return "characters/addForm";

        /*
        String characterInfo = dnfApiService.getCharacterInfo(characterSaveForm.getServer(), characterSaveForm.getName());
        */
        /*
        이전 코드
        HttpSession session = request.getSession(false);
        Member member = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
         */

        String memberName = (String) request.getAttribute("memberName");
        Optional<Character> character = jsonService.jsonToCharacter(characterSaveForm.getServer(), characterSaveForm.getName(), memberName);

        if(character.isEmpty()){
            bindingResult.reject("addFail","아이디 이름이 정확하지 않습니다.");
            return "characters/addForm";
        }

        Character existCharacter = characterService.join(character.get(), memberName);
//        boolean existCharacter = characterService.isExistCharacter(character.get(), memberName);

        if(existCharacter == null){
            bindingResult.reject("addFail","이미 존재하는 캐릭터 입니다.");
            return "characters/addForm";
        }

        return "redirect:/characters";
    }

    @PostMapping("delete/{characterId}")
    public String deleteCharacter(@PathVariable Long characterId){
        Character character = characterService.deleteOneById(characterId);
        log.info("삭제된 캐릭터 = {}",character);
        return "redirect:/characters";
    }

    @GetMapping("/rank")
    public String rank(Model model){
        List<Character> characters = characterService.findCharacters();
        Collections.sort(characters);
        model.addAttribute("characters",characters);
        return "characters/characterRank";
    }
}
