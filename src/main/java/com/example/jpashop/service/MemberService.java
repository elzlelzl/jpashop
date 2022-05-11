package com.example.jpashop.service;

import com.example.jpashop.domain.Member;
import com.example.jpashop.repository.MemberRepository;
import com.example.jpashop.web.LoginForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    boolean result = false;

    //회원가입
    @Transactional //변경
    public String join(Member member) {
        validateDuplicateMember(member);
        //중복 회원 검증
        if(this.result){
            memberRepository.save(member);
            return member.getName();
        }else {
            return null;
        }
    }
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers =
                memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            this.result = false;
        }else {
            this.result = true;
        }
    }
    public Member memberIdTest(LoginForm loginForm){
        System.out.println("service");
        return memberRepository.loginByMember(loginForm);
    }

    // 전체 회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
