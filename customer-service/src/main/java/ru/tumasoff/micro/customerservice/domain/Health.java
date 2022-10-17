package ru.tumasoff.micro.customerservice.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Health {
  private HealthStatus status;

  public Health(HealthStatus status) {
    this.status = status;
  }
}
