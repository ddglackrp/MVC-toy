package dnf.itemauction.web.member;

import dnf.itemauction.domain.member.Member;
import dnf.itemauction.domain.member.MemberRepository;
import dnf.itemauction.domain.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/add")
    public String add(@ModelAttribute("member") Member member){
        return "/members/addMemberForm";
    }

    @PostMapping("/add")
    public String save(@Validated @ModelAttribute("member") Member member, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "/members/addMemberForm";

        Member joinedMember = memberService.join(member);

        if(joinedMember == null){
            bindingResult.reject("joinFail","이미 존재하는 아이디 입니다.");
            return "/members/addMemberForm";
        }

        log.info("join member = {}",joinedMember);
        return "redirect:/";
    }
}

