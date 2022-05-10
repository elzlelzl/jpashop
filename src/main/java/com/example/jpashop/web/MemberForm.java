package com.example.jpashop.web;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {
    @NotEmpty(message = "회원 이름은 필수 입니다")
    private String name;

    @NotEmpty(message = "회원 패스워드는 필수 입니다")
    private String pwd;

    @NotEmpty(message = "회원 도시는 필수 입니다")
    private String city;

    @NotEmpty(message = "회원 거리는 필수 입니다")
    private String street;

    @NotEmpty(message = "우편번호는 필수 입니다")
    private String zipcode;
}