package dnf.itemauction.web;

import dnf.itemauction.domain.member.Member;
import dnf.itemauction.web.session.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
@Slf4j
public class HomeController {

    @GetMapping
    public String home(HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);
        if(session == null) return "home";

        Member member = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
        if(member == null) return "home";

        model.addAttribute("member",member);
        return "loginHome";
    }
}
