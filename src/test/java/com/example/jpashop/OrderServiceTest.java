package com.example.jpashop;

import com.example.jpashop.domain.Address;
import com.example.jpashop.domain.Member;
import com.example.jpashop.domain.Order;
import com.example.jpashop.domain.OrderStatus;
import com.example.jpashop.domain.item.Book;
import com.example.jpashop.domain.item.Item;
import com.example.jpashop.repository.OrderRepository;
import com.example.jpashop.service.OrderService;
import com.example.jpashop.web.OrderForm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {
    @PersistenceContext
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
//Given
        Member member = createMember("name", new Address("city", "street", "zipcode"));
        Item item = createBook("시골 JPA", 10000, 10); //이름, 가격, 재고

        int orderCount = 2;
//When
        Long orderId = orderService.order(new OrderForm());
//Then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.ORDER, getOrder.getStatus()); //"상품 주문시 상태는 ORDER",
        assertEquals(1, getOrder.getOrderItems().size());    //"주문한 상품 종류 수가 정확해야 한다."
        assertEquals(10000 * 2, getOrder.getTotalPrice());   //"주문 가격은 가격 * 수량이다.",
        assertEquals(8, item.getStockQuantity());   //"주문 수량만큼 재고가 줄어야 한다.",
    }


    private Member createMember(String name, Address address) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(address);
        em.persist(member);
        return member;
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setStockQuantity(stockQuantity);
        book.setPrice(price);
        em.persist(book);
        return book;
    }

    @Test
    public void 상품주문_재고수량초과() throws Exception{
        //Given
        Member member = createMember("name", new Address("city", "street", "zipcode"));
        Item item = createBook("시골 JPA", 10000, 10); //이름, 가격, 재고
        int orderCount = 11; //재고보다 많은 수량
        //When
        orderService.order(new OrderForm());
        //Then
        IllegalStateException thrown = Assertions
                .assertThrows(IllegalStateException.class, () -> {        });
        Assertions.assertEquals("재고 수량 부족 예외가 발생해야 한다.", thrown.getMessage());
    }

    @Test
    public void 주문취소() {
        //Given
        Member member = createMember("name", new Address("city", "street", "zipcode"));
        Item item = createBook("시골 JPA", 10000, 10); //이름, 가격, 재고
        int orderCount = 2;
        Long orderId = orderService.order(new OrderForm());
        //When
        orderService.cancelOrder(orderId);
        //Then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.CANCEL,
                getOrder.getStatus());  //"주문 취소시 상태는 CANCEL 이다.",
        assertEquals( 10,
                item.getStockQuantity());  //"주문이 취소된 상품은 그만큼 재고가 증가해야 한다.",
    }
}


