package dnf.itemauction.domain.login;

import dnf.itemauction.domain.member.Member;
import dnf.itemauction.domain.member.MemberRepository;
import dnf.itemauction.domain.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@Slf4j
class LoginServiceTest {

    @InjectMocks
    LoginService loginService;
    @Mock
    MemberRepository memberRepository;

    @Test
    @DisplayName("Login Test")
    void loginTest(){
        Member member = Member.builder()
                .loginId("test")
                .password("test")
                .name("test")
                .build();

        // Not Found
        given(memberRepository.findByLoginId(member.getLoginId())).willReturn(Optional.empty());
        Member loginMember = loginService.login(member.getLoginId(), member.getPassword());
        log.info("loginMember = {}",loginMember);
        assertThat(loginMember).isNull();

        // Not Equal Password
        given(memberRepository.findByLoginId(member.getLoginId())).willReturn(Optional.of(new Member("test","test","test!")));
        loginMember = loginService.login(member.getLoginId(), member.getPassword());
        log.info("loginMember = {}",loginMember);
        assertThat(loginMember).isNull();

        //Found
        given(memberRepository.findByLoginId(member.getLoginId())).willReturn(Optional.of(member));
        loginMember = loginService.login(member.getLoginId(), member.getPassword());
        log.info("loginMember = {}",loginMember);
        assertThat(member).isEqualTo(loginMember);
    }
}