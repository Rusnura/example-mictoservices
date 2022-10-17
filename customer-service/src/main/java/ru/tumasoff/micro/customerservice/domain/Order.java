package ru.tumasoff.micro.customerservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;

@Document(collection = "order")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Order implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  private String id;

  @NotBlank
  @Field("customer_id")
  private String customerId;

  @Field("created_at")
  @CreatedDate
  private Instant createdAt;

  @Field("updated_at")
  @LastModifiedDate
  private Instant updatedAt;

  @Version
  public Integer version;

  @Field("status")
  private OrderStatus status = OrderStatus.CREATED;

  @Field("payment_status")
  private Boolean paymentStatus = Boolean.FALSE;

  @NotNull
  @Field("payment_method")
  private PaymentType paymentMethod;

  @NotNull
  @Field("payment_details")
  private String paymentDetails;

  @Field("shipping_address")
  private Address shippingAddress;

  @Field("products")
  @NotEmpty
  private Set<@Valid Product> products;


  public Order addProduct(Product product) {
    this.products.add(product);
    return this;
  }

  public Order removeProduct(Product product) {
    this.products.remove(product);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Order order = (Order) o;
    return Objects.equals(id, order.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
