package live.vishsinh.expensetracker.controller;

import live.vishsinh.expensetracker.entity.Member;
import live.vishsinh.expensetracker.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService;


    @GetMapping
    public Iterable<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    @PostMapping
    public Member createMember(@RequestBody Member member) {
        return memberService.createMember(member);
    }
}
