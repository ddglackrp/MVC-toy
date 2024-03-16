package dnf.itemauction.domain.character;

import dnf.itemauction.domain.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@Slf4j
class CharacterServiceTest {
    @Mock
    CharacterRepository characterRepository;
    @InjectMocks
    CharacterService characterService;

    @Test
    @DisplayName("캐릭터 저장 Test")
    void join() {

        List<Character> characters = new ArrayList<>();

        Character character = Character.builder()
                .characterId("test1")
                .memberName("test")
                .name("캐릭터1")
                .build();

        // 저장 성공
        given(characterRepository.findAll()).willReturn(characters);
        given(characterRepository.save(character)).willReturn(character);
        Character testCharacter = characterService.join(character, "test");
        assertThat(testCharacter).isEqualTo(character);


        // 이미 저장된 캐릭터 저장 실패
        characters.add(character);
        given(characterRepository.findAll()).willReturn(characters);
        testCharacter = characterService.join(character, "test");
        assertThat(testCharacter).isNull();
    }

    @Test
    @DisplayName("중복 확인 로직 TEST")
    void isExistCharacterTest(){
        Character character = Character.builder()
                .characterId("test1")
                .memberName("test")
                .name("캐릭터1")
                .build();

        Character character2 = Character.builder()
                .characterId("test2")
                .memberName("test")
                .name("캐릭터2")
                .build();

        List<Character> characters = new ArrayList<>();

        // 존재하지 않는 캐릭터
        given(characterRepository.findAll()).willReturn(characters);
        boolean test = characterService.isExistCharacter(character, "test");
        assertThat(test).isFalse();

        // 존재하는 캐릭터
        characters.add(character);
        characters.add(character2);
        given(characterRepository.findAll()).willReturn(characters);
        boolean test2 = characterService.isExistCharacter(character, "test");
        assertThat(test2).isTrue();
    }

    @Test
    void findOneById() {
        Character character = Character.builder()
                .name("캐릭터1")
                .build();

        given(characterRepository.findById(any())).willReturn(character);
        Character findCharacter = characterService.findOneById(1L);
        Assertions.assertThat(findCharacter).isEqualTo(character);
    }

    @Test
    void findCharactersByMember() {
        Member member = Member.builder()
                .loginId("test")
                .name("test")
                .build();

        Character character = Character.builder()
                .memberName(member.getName())
                .name("캐릭터1")
                .build();

        Character character2 = Character.builder()
                .memberName(member.getName())
                .name("캐릭터2")
                .build();

        List<Character> characters = new ArrayList<>();
        characters.add(character);
        characters.add(character2);

        given(characterRepository.findByMember(member.getName())).willReturn(characters);

        List<Character> characterList = characterService.findCharactersByMember(member.getName());

        log.info("characterList = {}",characterList);
        assertThat(characterList.size()).isEqualTo(2);
        assertThat(characterList).contains(character,character2);
    }

    @Test
    void findCharacters() {
        Character character = Character.builder()
                .name("캐릭터1")
                .build();

        Character character2 = Character.builder()
                .name("캐릭터2")
                .build();

        List<Character> characters = new ArrayList<>();
        characters.add(character);
        characters.add(character2);

        given(characterRepository.findAll()).willReturn(characters);
        List<Character> list = characterService.findCharacters();
        assertThat(list.size()).isEqualTo(2);
        assertThat(list).contains(character,character2);
    }

    @Test
    void deleteOneById() {
        Character character = Character.builder()
                .name("캐릭터1")
                .build();

        given(characterRepository.deleteById(any())).willReturn(character);

        Character deleteCharacter = characterService.deleteOneById(1L);
        assertThat(deleteCharacter).isEqualTo(character);

    }
}