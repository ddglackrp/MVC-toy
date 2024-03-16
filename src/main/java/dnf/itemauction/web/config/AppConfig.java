package dnf.itemauction.web.config;

import dnf.itemauction.domain.character.CharacterRepository;
import dnf.itemauction.domain.character.MemoryCharacterRepository;
import dnf.itemauction.domain.member.MemberRepository;
import dnf.itemauction.domain.member.MemoryMemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class AppConfig {
    @Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }

    @Bean
    public CharacterRepository characterRepository(){
        return new MemoryCharacterRepository();
    }
}
