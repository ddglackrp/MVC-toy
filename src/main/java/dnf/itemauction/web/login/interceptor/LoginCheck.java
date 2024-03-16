package dnf.itemauction.web.login.interceptor;

import dnf.itemauction.domain.member.Member;
import dnf.itemauction.web.session.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheck implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null){
            log.info("로그인 필요한 사용자");
            response.sendRedirect("/");
            return false;
        }

        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        request.setAttribute("memberName", member.getName());
        return true;
    }
}
