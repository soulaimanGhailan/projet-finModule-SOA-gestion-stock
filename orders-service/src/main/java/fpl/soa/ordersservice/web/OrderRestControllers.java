package fpl.soa.ordersservice.web;

import fpl.soa.ordersservice.dtos.CreateOrderRequest;
import fpl.soa.ordersservice.dtos.CreateOrderResponse;
import fpl.soa.ordersservice.service.OrdersService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("orders")
public class OrderRestControllers {

    private OrdersService ordersService ;

    public OrderRestControllers(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PostMapping
    public CreateOrderResponse createOrder(@RequestBody @Valid CreateOrderRequest createOrderRequest) {
        return ordersService.placeOrder(createOrderRequest) ;
    }

    @GetMapping
    public String gello(){
        return "helll" ;
    }
}
