package com.acme.edu.message;

import com.acme.edu.decorator.Decorator;

import java.util.Map;

/**
 * Class representing pseudo-message which is sent to logger to trigger flushing
 */
public class FlushMessage extends Message {
    @Override
    public Message accumulate(Message message) {
        return null;
    }

    @Override
    public String decorate(Map<MessageType, Decorator> decoratorMap) {
        return decorate(decoratorMap.get(MessageType.FLUSH));
    }

    @Override
    public boolean isAbleToAccumulate(Message message) {
        return false;
    }
}
