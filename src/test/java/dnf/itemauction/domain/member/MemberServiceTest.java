package dnf.itemauction.domain.member;

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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@Slf4j
class MemberServiceTest {

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    MemberService memberService;

    @Test
    @DisplayName("MemberService Join Test")
    void joinTest(){
        Member member = Member.builder()
                .loginId("test")
                .name("test")
                .password("test")
                .build();


        given(memberRepository.findByLoginId(member.getLoginId())).willReturn(Optional.of(new Member()));
        Member joinMember = memberService.join(member);
        assertThat(joinMember).isNull();

        given(memberRepository.findByLoginId(member.getLoginId())).willReturn(Optional.empty());
        given(memberRepository.save(member)).willReturn(member);
        joinMember = memberService.join(member);

        log.info("joinMember = {}",joinMember);
        assertThat(joinMember).isNotNull().isEqualTo(member);
    }

    @Test
    @DisplayName("MemberService Id로 member 검색 Test")
    void findByIdTest(){
        Member member = Member.builder()
                .loginId("test")
                .name("test")
                .password("test")
                .build();

        given(memberRepository.findById(member.getId())).willReturn(member);
        Member findMember = memberService.findOneById(member.getId());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    @DisplayName("MemberService LoginId로 member 검색 Test")
    void findByLoginIdTest(){
        Member member = Member.builder()
                .loginId("test")
                .name("test")
                .password("test")
                .build();

        given(memberRepository.findByLoginId(member.getLoginId())).willReturn(Optional.of(member));
        Optional<Member> findMember = memberService.findOneByLoginId(member.getLoginId());
        assertThat(findMember).isPresent();
        assertThat(findMember.get()).isEqualTo(member);
    }

    @Test
    @DisplayName("MemberService 모든 멤버 검색 Test")
    void findAllTest(){
        Member member1 = Member.builder()
                .loginId("test")
                .name("test")
                .password("test")
                .build();

        Member member2 = Member.builder()
                .loginId("test2")
                .name("test2")
                .password("test2")
                .build();

        List<Member> members = new ArrayList<>();
        members.add(member1);
        members.add(member2);

        given(memberRepository.findAll()).willReturn(members);
        List<Member> serviceMembers = memberService.findMembers();
        assertThat(serviceMembers).contains(member1,member2);
        assertThat(serviceMembers.size()).isEqualTo(2);

    }

}