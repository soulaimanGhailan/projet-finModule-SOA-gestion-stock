package fpl.soa.ordersservice.web;

import fpl.soa.ordersservice.dtos.CreateOrderRequest;
import fpl.soa.ordersservice.dtos.CreateOrderResponse;
import fpl.soa.ordersservice.entities.OrderHistoryEntity;
import fpl.soa.ordersservice.service.OrderHistoryService;
import fpl.soa.ordersservice.service.OrdersService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("orders")
public class OrderRestControllers {

    private OrdersService ordersService ;
    private OrderHistoryService orderHistoryService ;

    public OrderRestControllers(OrdersService ordersService, OrderHistoryService orderHistoryService) {
        this.ordersService = ordersService;
        this.orderHistoryService = orderHistoryService;
    }

    @PostMapping
    public CreateOrderResponse createOrder(@RequestBody @Valid CreateOrderRequest createOrderRequest) {
        return ordersService.placeOrder(createOrderRequest) ;
    }

    @GetMapping("/{orderId}/history")
    public List<OrderHistoryEntity> getOrderHistory(@PathVariable String orderId) {
        return orderHistoryService.findByOrderId(orderId) ;
    }
}
