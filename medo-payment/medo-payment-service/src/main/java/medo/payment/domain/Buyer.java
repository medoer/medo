package medo.payment.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * Buyer Value Object
 */
@Data
public class Buyer implements Serializable {
    private String buyerId;
    private String buyerEmail;
}
