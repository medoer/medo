package medo.payment.domain;

import java.io.Serializable;
import lombok.Data;

/** Buyer Value Object */
@Data
public class Buyer implements Serializable {
    private String buyerId;
    private String buyerEmail;
}
