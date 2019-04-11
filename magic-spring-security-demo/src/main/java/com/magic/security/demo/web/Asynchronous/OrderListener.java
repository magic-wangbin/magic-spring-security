package com.magic.security.demo.web.Asynchronous;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class OrderListener implements ApplicationListener<ContextRefreshedEvent> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SimulateQueue simulateQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //单独一个线程做监听
        new Thread(() -> {
            while (true) {
                if(StringUtils.isNotBlank(simulateQueue.getSendMessage())){
                    String  orderNumber = simulateQueue.getSendMessage();
                    logger.info("订单处理结果：{}",orderNumber);
                    deferredResultHolder.getMap().get(orderNumber).setResult("[SUCCESS]");
                    //处理完成清空
                    simulateQueue.setSendMessage(null);
                }else{
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }).start();
    }
}
