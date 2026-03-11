package com.henan.heritage.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.henan.heritage.dto.ai.AiChatRequest;
import com.henan.heritage.dto.ai.AiMessage;
import com.henan.heritage.service.AiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import java.util.HashMap;
import java.util.Map;

@Service
public class AiServiceImpl implements AiService {

    @Value("${ai.base-url:https://api.openai.com}")
    private String baseUrl;

    @Value("${ai.api-key:}")
    private String apiKey;

    @Value("${ai.model:gpt-4o-mini}")
    private String model;

    @Value("${ai.timeout-ms:60000}")
    private int timeoutMs;

    @Override
    public String chat(AiChatRequest request) {
        if (request == null || request.getMessages() == null || request.getMessages().isEmpty()) {
            throw new IllegalArgumentException("messages 不能为空");
        }
        if (apiKey == null || apiKey.isBlank()) {
            return "AI 功能暂不可用：未配置 ai.api-key（可用环境变量 AI_API_KEY 注入到 application.yml）";
        }

        String url = buildChatCompletionsUrl(baseUrl);

        // 最多重试3次
        int maxRetries = 3;
        int retryCount = 0;
        
        while (retryCount < maxRetries) {
            try {
                // 每次调用时设置超时时间，避免修改全局RestTemplate
                SimpleClientHttpRequestFactory rf = new SimpleClientHttpRequestFactory();
                rf.setConnectTimeout(timeoutMs);
                rf.setReadTimeout(timeoutMs);
                RestTemplate tempRestTemplate = new RestTemplate(rf);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setAccept(MediaType.parseMediaTypes("application/json"));
                headers.setBearerAuth(apiKey.trim());

                // 构建消息列表
                Map<String, Object> payload = new HashMap<>();
                payload.put("model", model);
                payload.put("messages", request.getMessages());
                payload.put("temperature", 0.7);

                HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

                ResponseEntity<JsonNode> resp = tempRestTemplate.exchange(url, HttpMethod.POST, entity, JsonNode.class);
                if (!resp.getStatusCode().is2xxSuccessful() || resp.getBody() == null) {
                    return "AI 调用失败: " + resp.getStatusCode();
                }

                JsonNode root = resp.getBody();
                JsonNode contentNode = root.path("choices").path(0).path("message").path("content");
                if (contentNode.isMissingNode() || contentNode.isNull()) {
                    return "AI 返回为空";
                }
                return contentNode.asText("");
            } catch (Exception e) {
                retryCount++;
                if (retryCount >= maxRetries) {
                    return "AI 调用失败: " + e.getMessage();
                }
                // 等待一段时间后重试
                try {
                    Thread.sleep(1000 * retryCount);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        
        return "AI 调用失败: 重试次数达到上限";
    }

    private static String normalizeBaseUrl(String baseUrl) {
        if (baseUrl == null || baseUrl.isBlank()) return "https://api.openai.com";
        String s = baseUrl.trim();
        while (s.endsWith("/")) s = s.substring(0, s.length() - 1);
        return s;
    }

    /**
     * 兼容两种写法：
     * - https://dashscope.aliyuncs.com/compatible-mode
     * - https://dashscope.aliyuncs.com/compatible-mode/v1
     * 最终都应命中：.../v1/chat/completions
     */
    private static String buildChatCompletionsUrl(String baseUrl) {
        String normalized = normalizeBaseUrl(baseUrl);
        if (normalized.endsWith("/v1")) {
            return normalized + "/chat/completions";
        }
        return normalized + "/v1/chat/completions";
    }
}

