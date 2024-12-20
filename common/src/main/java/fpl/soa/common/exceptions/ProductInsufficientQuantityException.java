package fpl.soa.common.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;


@Getter @AllArgsConstructor
public class ProductInsufficientQuantityException extends RuntimeException {
    private final Long productId;
    private final String orderId;
}
