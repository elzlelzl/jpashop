package com.example.jpashop.web;

import com.example.jpashop.domain.Member;
import com.example.jpashop.domain.Order;
import com.example.jpashop.domain.OrderSearch;
import com.example.jpashop.domain.item.Item;
import com.example.jpashop.service.ItemService;
import com.example.jpashop.service.MemberService;
import com.example.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping(value = "/order")
    public String createForm(Model model, HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        System.out.println(session.getAttribute("sv"));
        Member member = (Member) session.getAttribute("sv");
        System.out.println("mid   :  " + member.getId());
        Member members = memberService.findOne(member.getId());
        List<Item> items = itemService.findItems();
        model.addAttribute("members", members);
        model.addAttribute("items", items);
        model.addAttribute("orderForm", new OrderForm());
        return "order/orderForm";

    }
    @PostMapping(value = "/order")
    public String order(@Valid OrderForm orderForm, BindingResult result) {
        if(result.hasErrors()){
            return "order/orderForm";
        }
        orderService.order(orderForm);
        return "redirect:/orders";
    }

    @GetMapping(value = "/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch,
                            Model model) {
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);
        return "order/orderList";
    }

    @PostMapping(value = "/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }

}