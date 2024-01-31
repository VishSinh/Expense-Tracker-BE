package live.vishsinh.expensetracker.service;

import live.vishsinh.expensetracker.entity.Member;
import live.vishsinh.expensetracker.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Iterable<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    public Member createMember(Member member) {
        return memberRepository.save(member);
    }
}
