package dnf.itemauction.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member join(Member member){
        if(memberExistByLoginId(member)) return null;

        return memberRepository.save(member);
    }

    private boolean memberExistByLoginId(Member member) {
        return memberRepository.findByLoginId(member.getLoginId()).isPresent();
    }

    public Member findOneById(Long id){
        return memberRepository.findById(id);
    }

    public Optional<Member> findOneByLoginId(String loginId){
        return memberRepository.findByLoginId(loginId);
    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }


}
