package ru.tumasoff.micro.orderservice.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import ru.tumasoff.micro.orderservice.domain.Order;
import ru.tumasoff.micro.orderservice.exceptions.CustomerOrderException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class OrderService {
  private final RestTemplate restTemplate;
  public OrderService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Value("${spring.application.microservice-customer.url}")
  private String customerBaseUrl;

  private static final String CUSTOMER_ORDER_URL = "customerOrders/";

  public void createOrder(Order order) {
    final var url = customerBaseUrl + CUSTOMER_ORDER_URL + order.getCustomerId();
    final var headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    log.info("Order Request URL: {}", url);
    try {
      final HttpEntity<?> request = new HttpEntity<>(order, headers);
      final var responseEntity = restTemplate.postForEntity(url, request, Order.class);
      if (responseEntity.getStatusCode().isError()) {
        log.error("For Order ID: {}, error response: {} is received to create Order in Customer Microservice", order.getId(), responseEntity.getStatusCode().getReasonPhrase());
        throw new CustomerOrderException(order.getId(), responseEntity.getStatusCodeValue());
      }
      if (responseEntity.hasBody()) {
        log.error("Order From Response: {}", responseEntity.getBody().getId());
      }
    } catch (Exception e) {
      log.error("For Order ID: {}, cannot create Order in Customer Microservice for reason: {}", order.getId(), ExceptionUtils.getRootCauseMessage(e));
      throw new CustomerOrderException(order.getId(), ExceptionUtils.getRootCauseMessage(e));
    }
  }

  public void updateOrder(Order order) {
    final String url = customerBaseUrl + CUSTOMER_ORDER_URL + order.getCustomerId();
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    log.info("Order Request URL: {}", url);
    try {
      final var request = new HttpEntity<>(order, headers);
      restTemplate.put(url, request);
    } catch (Exception e) {
      log.error("For Order ID: {}, cannot create Order in Customer Microservice for reason: {}", order.getId(), ExceptionUtils.getRootCauseMessage(e));
      throw new CustomerOrderException(order.getId(), ExceptionUtils.getRootCauseMessage(e));
    }
  }


  public void deleteOrder(Order order) {
    final String url = customerBaseUrl + CUSTOMER_ORDER_URL + order.getCustomerId() + "/" + order.getId();

    log.info("Order Request URL: {}", url);
    try {
      restTemplate.delete(url);
    } catch (Exception e) {
      log.error("For Order ID: {}, cannot create Order in Customer Microservice for reason: {}", order.getId(), ExceptionUtils.getRootCauseMessage(e));
      throw new CustomerOrderException(order.getId(), ExceptionUtils.getRootCauseMessage(e));
    }
  }
}
