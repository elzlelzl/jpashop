package com.example.jpashop.web;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NegativeOrZero;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrderForm {

    @NotNull(message = "test")
    private Long memberId;

    @NotNull(message = "test")
    private Long itemId;

    @NegativeOrZero(message = "test")
    private int count;
}
