package com.example.utils.demo.disrupMq.mq;

import com.lmax.disruptor.EventFactory;

public class HelloEventFactory implements EventFactory<MessageModel> {
    @Override
    public MessageModel newInstance() {
        return new MessageModel();
    }
}
