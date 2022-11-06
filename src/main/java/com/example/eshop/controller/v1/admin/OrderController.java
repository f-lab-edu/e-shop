package com.example.eshop.controller.v1.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("AdminOrderController")
@RequestMapping(value="/v1/admin/orders")
@RequiredArgsConstructor
public class OrderController {
}