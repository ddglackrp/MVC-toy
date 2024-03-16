package dnf.itemauction.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemberRepository memberRepository = new MemoryMemberRepository();
    @Test
    @DisplayName("repository save")
    void saveTest(){
        Member member = Member.builder()
                .loginId("test")
                .name("test1")
                .password("test!")
                .build();
        Member savedMember = memberRepository.save(member);
        assertThat(savedMember).isEqualTo(member);
    }

}