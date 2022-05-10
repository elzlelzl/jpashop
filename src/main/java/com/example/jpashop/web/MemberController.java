package com.example.jpashop.web;

import com.example.jpashop.domain.Address;
import com.example.jpashop.domain.Member;
import com.example.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping(value = "/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }
    @PostMapping(value = "/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }
        Address address = new Address(form.getCity(), form.getStreet(),form.getZipcode());
        Member member = new Member();
        member.setName(form.getName());
        member.setPwd(form.getPwd());
        member.setAddress(address);
        memberService.join(member);
        return "login";
    }
    @GetMapping(value = "/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }
    @PostMapping(value = "/login")
    public String login(@Valid LoginForm loginForm, BindingResult result, HttpServletRequest httpServletRequest) {
        if(result.hasErrors()){
            return "login";
        }
        HttpSession session = httpServletRequest.getSession();
        session.setMaxInactiveInterval(10*360);
        Member member = memberService.memberIdTest(loginForm);
        System.out.println("member");

        if(member==null){
            return "login";
        }
        session.setAttribute("sv", member);
        System.out.println(session.getId());
        return "redirect:/home";
    }
    @GetMapping(value = "/logout")
    public String logout(HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping(value = "/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}