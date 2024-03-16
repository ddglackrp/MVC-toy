package dnf.itemauction.domain.character;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CharacterService {

    private final CharacterRepository characterRepository;

    public Character join(Character character, String memberName){
        if(isExistCharacter(character, memberName)) return null;
        return characterRepository.save(character);
    }

    public Character findOneById(Long id){
        return characterRepository.findById(id);
    }

    public List<Character> findCharactersByMember(String memberName){
        return characterRepository.findByMember(memberName);
    }

    public List<Character> findCharacters(){
        return characterRepository.findAll();
    }

    public Character deleteOneById(Long id){
        return characterRepository.deleteById(id);
    }

    public boolean isExistCharacter(Character character, String memberName) {
        Optional<Character> firstCharacter = characterRepository.findAll().stream()
                .filter(m -> m.getCharacterId().equals(character.getCharacterId()) && m.getMemberName().equals(memberName))
                .findFirst();

        log.info("Optional = {}",firstCharacter);

        return firstCharacter.isPresent();
    }

}
