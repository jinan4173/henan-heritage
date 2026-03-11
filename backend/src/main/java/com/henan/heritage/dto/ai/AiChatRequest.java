package com.henan.heritage.dto.ai;

import java.util.List;

public class AiChatRequest {
    private List<AiMessage> messages;

    public List<AiMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<AiMessage> messages) {
        this.messages = messages;
    }
}

