package com.koropets.eis.client.service;

import com.koropets.eis.common.Message;

public class MessageServiceImpl implements MessageService {
    @Override
    public Message sendMessage(Message message) {
        System.out.println(message.getWord());
        return null;
    }
}
