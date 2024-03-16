package dnf.itemauction.domain.character;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


public class MemoryCharacterRepository implements CharacterRepository{

    private static final Map<Long, Character> store = new ConcurrentHashMap<>();
    private static long sequence = 0L;

    @Override
    public Character findById(Long id) {
        return store.get(id);
    }

    @Override
    public List<Character> findAll() {
        return new ArrayList<>(store.values());
    }

    /*
    service 로 이동
    @Override
    public Character isExistCharacter(Character character, String memberName) {
        return findAll().stream()
                .filter(m -> m.getCharacterId().equals(character.getCharacterId()) && m.getMemberName().equals(memberName))
                .findFirst()
                .orElse(null);
    }
     */

    @Override
    public List<Character> findByMember(String memberName){
        return findAll().stream()
                .filter(character -> character.getMemberName().equals(memberName))
                .collect(Collectors.toList());
    }

    @Override
    public Character save(Character character) {
        character.setId(++sequence);
        store.put(character.getId(),character);
        return character;
    }

    @Override
    public Character deleteById(Long id){
        Character character = findById(id);
        store.remove(character.getId());
        return character;
    }


    @Override
    public void update(Long characterId, Character character) {
        Character findCharacter = findById(characterId);
        findCharacter.setName(character.getName());
        findCharacter.setJob(character.getJob());
        findCharacter.setLevel(character.getLevel());
        findCharacter.setFame(character.getFame());
        findCharacter.setServer(character.getServer());
    }

}
