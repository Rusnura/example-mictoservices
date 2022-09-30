package ru.tumasoff.micro.orderservice.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tumasoff.micro.orderservice.domain.Health;
import ru.tumasoff.micro.orderservice.domain.HealthStatus;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class HealthResource {
  @GetMapping(value = "/health", produces = "application/json")
  public ResponseEntity<Health> getHealth() {
    log.debug("Rest request to get the Health status");
    return ResponseEntity.ok(new Health(HealthStatus.UP));
  }
}
