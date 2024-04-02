package com.study;

import com.study.domain.Order;
import com.study.domain.Product;
import com.study.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // -> 자동 init
public class OrderTest {

    @Mock
    private OrderService orderServiceMock;

//    public OrderTest() {
//        MockitoAnnotations.initMocks(this);
//    }

    @Test
    @DisplayName("주문 생성 테스트")
    public void 주문생성() {
        // Given
        Product book = new Product("1", "책", 10000);
        Product pencil = new Product("2", "연필", 500);
        List<Product> products = new ArrayList<>();
        products.add(book);
        products.add(pencil);

//        OrderService orderService = new OrderService();

        // 모의 객체를 사용하여 createOrder 메서드가 호출될 때 Order 객체를 반환하도록 설정
        when(orderServiceMock.createOrder(products)).thenReturn(new Order(1L, products));

        // When
        Order order = orderServiceMock.createOrder(products);

        // Then
        assertNotNull(order);
        assertNotEquals(1, order.getProducts().size());
        assertEquals(2, order.getProducts().size());
        assertEquals("책", order.getProducts().get(0).getName());
        assertEquals("연필", order.getProducts().get(1).getName());
    }

}
