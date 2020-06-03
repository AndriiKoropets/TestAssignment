package com.koropets.eis.processor.controller;

import com.koropets.eis.common.Word;
import com.koropets.eis.processor.service.WordProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/message")
public class SentenceTestController {

    @Autowired
    private WordProcessor wordProcessor;

    @PostMapping(value = "/send")
    public void sendMessage(@RequestBody Word word) {
        wordProcessor.processWord(word);
    }


}
