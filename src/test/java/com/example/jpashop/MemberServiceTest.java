package com.example.jpashop;


import com.example.jpashop.domain.Member;
import com.example.jpashop.repository.MemberRepository;
import com.example.jpashop.service.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
//Given
        Member member = new Member();
        member.setName("boot");
//When
        String  saveId = memberService.join(member);
//Then
//        assertEquals(member, memberRepository.findOne(saveId));
    }
    @Test
    public void 중복_회원_예외() throws Exception {
//Given
        Member member1 = new Member();
        member1.setName("boot");
        Member member2 = new Member();
        member2.setName("boot");
//When
        memberService.join(member1);
        memberService.join(member2); //예외가 발생해야 한다.
//Then
        IllegalStateException thrown = Assertions.assertThrows(IllegalStateException.class, () -> {

        });
        Assertions.assertEquals("이미 존재하는 회원입니다.", thrown.getMessage());

    }
}