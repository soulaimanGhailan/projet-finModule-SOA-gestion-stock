package fpl.soa.ordersservice.web;

import fpl.soa.ordersservice.dtos.CreateOrderRequest;
import fpl.soa.ordersservice.dtos.CreateOrderResponse;
import fpl.soa.ordersservice.entities.OrderHistoryEntity;
import fpl.soa.ordersservice.models.Customer;
import fpl.soa.ordersservice.service.OrderHistoryService;
import fpl.soa.ordersservice.service.OrdersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
@CrossOrigin(origins = "*")
public class OrderRestControllers {

    private final OrdersService ordersService;
    private final OrderHistoryService orderHistoryService;

    public OrderRestControllers(OrdersService ordersService, OrderHistoryService orderHistoryService) {
        this.ordersService = ordersService;
        this.orderHistoryService = orderHistoryService;
    }

    @Operation(summary = "Create a new order", description = "Place a new order and return the details of the created order.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order created successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid request body.")
    })
    @PostMapping
    public CreateOrderResponse createOrder(@RequestBody @Valid CreateOrderRequest createOrderRequest) {
        return ordersService.placeOrder(createOrderRequest);
    }

    @Operation(summary = "Get order history", description = "Retrieve the history of a specific order by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order history retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "Order not found.")
    })
    @GetMapping("/{orderId}/history")
    public List<OrderHistoryEntity> getOrderHistory(@PathVariable String orderId) {
        return orderHistoryService.findByOrderId(orderId);
    }

    @Operation(summary = "Get customer of an order", description = "Retrieve the customer associated with a specific order ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "Order or customer not found.")
    })
    @GetMapping("/customer/{orderId}")
    public Customer getCustomer(@PathVariable String orderId) {
        return ordersService.getCustomerOfOrder(orderId);
    }

    @Operation(summary = "Get customer by ID", description = "Retrieve customer details by their unique customer ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "Customer not found.")
    })
    @GetMapping("test/{customerId}")
    public Customer getCustomergg(@PathVariable String customerId) {
        return ordersService.getCustomer(customerId);
    }
}
