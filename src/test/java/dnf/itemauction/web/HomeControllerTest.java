package dnf.itemauction.web;

import dnf.itemauction.domain.member.Member;
import dnf.itemauction.web.session.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomeController.class)
@Slf4j
class HomeControllerTest {

    @Autowired
    MockMvc mvc;
    MockHttpSession session = new MockHttpSession();

    @Test
    @DisplayName("Home 로그인 전 테스트")
    void homeBeforeLoginTest() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andDo(print());
    }

    @Test
    @DisplayName("Home 로그인 Member = null 테스트")
    void homeAfterLoginNullTest() throws Exception {
        session.setAttribute(SessionConst.LOGIN_MEMBER,null);
        mvc.perform(get("/").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andDo(print());
    }

    @Test
    @DisplayName("Home 로그인 성공 후 테스트")
    void homeAfterLoginTest() throws Exception {
        Member testMember = new Member();
        testMember.setLoginId("test1");
        testMember.setName("tester");
        testMember.setPassword("test!");

        session.setAttribute(SessionConst.LOGIN_MEMBER, testMember);

        mvc.perform(get("/").session(session))
                .andExpect(status().isOk())
                .andExpect(model().attribute("member",testMember))
                .andDo(print());
    }


}