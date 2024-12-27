package fpl.soa.customerservice.service;


import fpl.soa.customerservice.entities.ShoppingCart;
import fpl.soa.customerservice.entities.ShoppingCartItem;
import fpl.soa.customerservice.exceptions.CustomerNotFoundException;
import fpl.soa.customerservice.model.AddItemRequest;

public interface ShoppingCartService {
    ShoppingCartItem createItem(AddItemRequest addItemRequest ) ;
    ShoppingCart addItemToCart(AddItemRequest addItemRequest) throws CustomerNotFoundException;
    ShoppingCart removeItemFromCart(String customerId , String productId) throws CustomerNotFoundException;
    ShoppingCart updateItemInCart(ShoppingCartItem item, AddItemRequest addItemRequest, ShoppingCart cart) ;
}
