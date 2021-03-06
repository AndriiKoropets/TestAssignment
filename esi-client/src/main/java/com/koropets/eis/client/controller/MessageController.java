package com.koropets.eis.client.controller;

import com.koropets.eis.client.service.MessageService;
import com.koropets.eis.common.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping(value = "/send")
    public Word sendMessage(@RequestBody Word word) {
        return messageService.sendMessage(word);
    }
}
