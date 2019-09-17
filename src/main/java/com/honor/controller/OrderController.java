package com.honor.controller;

import com.honor.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rongyaowen
 * on 2019/9/17.
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 模拟订单提交
     *
     * @return
     */
    @RequestMapping("/ordercommit")
    public String orderCommit() {
        for (int i = 1; i < 100; i++) {
            orderService.orderCommit(i);
        }
        return "success";
    }
}
