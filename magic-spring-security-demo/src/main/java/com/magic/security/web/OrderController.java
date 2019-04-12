package com.magic.security.web;

import com.magic.security.web.Asynchronous.DeferredResultHolder;
import com.magic.security.web.Asynchronous.SimulateQueue;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Date;
import java.util.concurrent.Callable;

/**
 * 异步订单处理模拟.
 */
@RestController
@RequestMapping("order")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    @Autowired
    private SimulateQueue simulateQueue;

    @GetMapping("/process")
    public Callable<String> processOrder() throws InterruptedException {
        String result = "doing";//订单处理结果

        String orderId = String.valueOf(new Date().getTime());

        logger.info("订单【{}】开行进行处理", orderId);


        Callable<String> callableResult = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(2000);
                return "success";
            }
        };

        logger.info("订单【{}】处理完成！", orderId);
        return callableResult;
    }

    @GetMapping("/process/deferred")
    public DeferredResult<String> processOrderDeferred() throws InterruptedException {
        //
        logger.info("主线程开始！");
        String orderNumber = RandomStringUtils.randomNumeric(10);

        // 发送消息队列
        simulateQueue.receive(orderNumber);

        //
        DeferredResult<String> result = new DeferredResult<>();
        deferredResultHolder.getMap().put(orderNumber, result);

        //
        return result;

    }
}
