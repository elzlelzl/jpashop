package com.example.jpashop.web;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class BookForm {
    private Long id;

    @NotEmpty(message = "우편번호는 필수 입니다")
    private String name;

    @NotNull(message = "우편번호는 필수 입니다")
    private int price;

    @NotNull(message = "우편번호는 필수 입니다")
    private int stockQuantity;

    @NotEmpty(message = "우편번호는 필수 입니다")
    private String author;

    @NotEmpty(message = "우편번호는 필수 입니다")
    private String isbn;
}