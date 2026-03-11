package com.henan.heritage.controller;

import com.henan.heritage.common.Result;
import com.henan.heritage.dto.ai.AiChatRequest;
import com.henan.heritage.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/ai", produces = "application/json; charset=UTF-8")
public class AiController {

    @Autowired
    private AiService aiService;

    @PostMapping("/chat")
    public Result<String> chat(@RequestBody AiChatRequest request) {
        try {
            String reply = aiService.chat(request);
            return Result.success(reply);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }
}

