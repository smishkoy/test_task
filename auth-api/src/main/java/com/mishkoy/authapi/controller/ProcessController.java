package com.mishkoy.authapi.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProcessController {

    @Value("${internal.token}") private String internalToken;
    @Value("${service.b.url}") private String serviceBUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/process")
    public ResponseEntity<?> process(@RequestBody Map<String, String> body) {
        // 1. Створюємо заголовок для Service B
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Internal-Token", internalToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        // 2. Викликаємо Service B
        Map<String, String> response = restTemplate.postForObject(serviceBUrl, entity, Map.class);

        // 3. Тут має бути логіка збереження в Postgres (processing_log)

        return ResponseEntity.ok(response);
    }
}