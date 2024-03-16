package dnf.itemauction.domain.character;

import dnf.itemauction.domain.member.Member;

import java.util.List;

public interface CharacterRepository {
    public Character findById(Long id);
    public List<Character> findAll();

    public List<Character> findByMember(String memberName);

    public Character deleteById(Long id);
    public Character save(Character character);
    public void update(Long characterId, Character character);


}
