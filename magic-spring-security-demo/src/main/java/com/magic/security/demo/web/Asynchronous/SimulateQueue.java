package com.magic.security.demo.web.Asynchronous;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 模拟一个消息队列.
 */
@Component
public class SimulateQueue {

    private static final Logger logger = LoggerFactory.getLogger(SimulateQueue.class);

    //消息队列接收的消息
    private String receiveMessage;

    //处理完成发出的消息
    private String sendMessage;

    public void receive(String message) throws InterruptedException {
        //处理完成之后发送消息
        new Thread(()->{
            logger.info("接到下单请求, " + message);
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.sendMessage = message;
            logger.info("下单请求处理完毕," + message);
        }).start();
    }

    //如果是真实中间件，监听服务为多台时，需要全部监听到，此处仅为一个demo
    public String getSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(String sendMessage) {
        this.sendMessage = sendMessage;
    }
}
