package dnf.itemauction;

import dnf.itemauction.domain.member.Member;
import dnf.itemauction.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InitData {

    private final MemberRepository memberRepository;

    @PostConstruct
    public void init() {
        Member member1 = new Member();
        member1.setLoginId("test");
        member1.setPassword("test!");
        member1.setName("테스터");
        memberRepository.save(member1);

        Member member2 = new Member();
        member2.setLoginId("test2");
        member2.setPassword("test!");
        member2.setName("테스터2");
        memberRepository.save(member2);
    }
}
