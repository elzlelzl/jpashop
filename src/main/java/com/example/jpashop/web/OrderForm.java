package com.example.jpashop.web;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderForm {

    private Long memberId;

    private Long itemId;

    private int count;
}
