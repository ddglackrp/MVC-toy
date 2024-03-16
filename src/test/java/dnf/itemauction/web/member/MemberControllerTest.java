package dnf.itemauction.web.member;

import dnf.itemauction.domain.member.Member;
import dnf.itemauction.domain.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    MemberService memberService;

    @Test
    @DisplayName("회원가입 Get Form test")
    void memberAddFormTest() throws Exception {
        mvc.perform(get("/members/add"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("member",new Member()))
                .andExpect(view().name("/members/addMemberForm"))
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입 Post Html Form 공백")
    void memberAddFormBlankTest() throws Exception {
        mvc.perform(post("/members/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("/members/addMemberForm"))
                .andExpect(model().hasErrors())
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입 이미 존재하는 아이디")
    void memberAddDuplicateLoginIdTest() throws Exception {
        given(memberService.join(any())).willReturn(null);

        mvc.perform(post("/members/add").param("LoginId","test").param("name","test").param("password","test"))
                .andExpect(status().isOk())
                .andExpect(view().name("/members/addMemberForm"))
                .andExpect(model().hasErrors())
                .andDo(print());
    }

    @Test
    @DisplayName("회원 가입 성공")
    void memberAddTest() throws Exception {
        Member member = Member.builder()
                .loginId("test")
                .name("test")
                .password("test!")
                .build();

        given(memberService.join(any())).willReturn(member);

        mvc.perform(post("/members/add").param("LoginId","test").param("name","test").param("password","test!"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/"))
                .andExpect(redirectedUrl("/"))
                .andExpect(model().hasNoErrors())
                .andDo(print());

    }
}