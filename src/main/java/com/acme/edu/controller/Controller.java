package com.acme.edu.controller;

import com.acme.edu.decorator.*;
import com.acme.edu.message.FlushMessage;
import com.acme.edu.message.Message;
import com.acme.edu.message.MessageType;
import com.acme.edu.saver.DefaultSaver;
import com.acme.edu.saver.Saver;
import com.acme.edu.saver.SavingException;

import java.util.Collection;
import java.util.EnumMap;
import java.util.LinkedList;

public class Controller {
    private Saver saver = new DefaultSaver();
    private Message prevMessage = new FlushMessage();
    private EnumMap<MessageType, Decorator> decoratorMap;
    private Collection<Message> messageList = new LinkedList<>();

    public Controller() {
        decoratorMap = new EnumMap<>(MessageType.class);
        decoratorMap.put(MessageType.BOOLEAN, new DefaultBooleanDecorator());
        decoratorMap.put(MessageType.BYTE, new DefaultByteDecorator());
        decoratorMap.put(MessageType.CHAR, new DefaultCharDecorator());
        decoratorMap.put(MessageType.FLUSH, new EmptyDecorator());
        decoratorMap.put(MessageType.INT, new DefaultIntDecorator());
        decoratorMap.put(MessageType.INTARRAY, new DefaultIntArrayDecorator());
        decoratorMap.put(MessageType.INTMATRIX, new DefaultIntMatrixDecorator());
        decoratorMap.put(MessageType.REFERENCE, new DefaultReferenceDecorator());
        decoratorMap.put(MessageType.STRING, new DefaultStringDecorator());
    }

    public Controller(Saver saver) {
        this();
        this.saver = saver;
    }

    public int log(Message message) {
        if (prevMessage.isAbleToAccumulate(message)) {
            messageList.add(message);
        } else {
            try {
                Message outputMessage;
                if (messageList.size() > 1) {
                    outputMessage = messageList.stream()
                            .reduce((message1, message2) -> message1.accumulate(message2))
                            .get();
                } else {
                    outputMessage = prevMessage;
                }
                messageList.clear();
                prevMessage = message;
                messageList.add(message);
                saver.save(outputMessage.decorate(decoratorMap));
            } catch (SavingException se) {
                if (se.getExceptionCode() != 1) {
                    return se.getExceptionCode();
                } else {
                    prevMessage = message;
                }
            }
        }
        return 0;
    }

    public int update(MessageType type, Decorator newDecorator) {
        if(newDecorator != null) {
            decoratorMap.put(type, newDecorator);
        } else {
            return 1;
        }
        return 0;
    }

    public void setSaver(Saver saver) {
        this.saver = saver;
    }
}
