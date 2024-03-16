package dnf.itemauction.web.login;

import dnf.itemauction.domain.login.LoginService;
import dnf.itemauction.domain.member.Member;
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

@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    LoginService loginService;

    @Test
    @DisplayName("Login 화면 Get")
    void loginGetTest() throws Exception {
        mvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Login Post Http Form 공백")
        void loginPostBlankTest() throws Exception {
        mvc.perform(post("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("/login/loginForm"))
                .andExpect(model().hasErrors())
                .andDo(print());
    }

    @Test
    @DisplayName("Login Post 아이디 비밀번호 오류")
    void loginPostFailTest() throws Exception {
        given(loginService.login(any(),any())).willReturn(null);

        mvc.perform(post("/login").param("loginId","test1").param("password","test!"))
                .andExpect(status().isOk())
                .andExpect(view().name("/login/loginForm"))
                .andExpect(model().attribute("loginForm",new LoginForm("test1","test!")))
                .andDo(print());
    }

    @Test
    @DisplayName("Login Post 성공 테스트")
    void loginPostTest() throws Exception {
        Member member = Member.builder()
                .loginId("test1")
                .name("tester")
                .password("test!")
                .build();

        given(loginService.login("test1","test!")).willReturn(member);

        mvc.perform(post("/login").param("loginId","test1").param("password","test!"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/"))
                .andExpect(redirectedUrl("/"))
                .andDo(print());
    }

}