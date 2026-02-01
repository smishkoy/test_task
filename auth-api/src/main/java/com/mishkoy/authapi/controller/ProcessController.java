package com.mishkoy.authapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mishkoy.authapi.model.ProcessingLog;
import com.mishkoy.authapi.repository.ProcessingLogRepository;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ProcessController {

    @Value("${internal.token}")
    private String internalToken;

    @Value("${service.b.url}")
    private String serviceBUrl;
    private final ProcessingLogRepository logRepository;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/process")
    public ResponseEntity<?> process(@RequestBody Map<String, String> body) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Internal-Token", internalToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        Map<String, String> response = restTemplate.postForObject(serviceBUrl, entity, Map.class);

        saveToLog(body, response);

        return ResponseEntity.ok(response);
    }

    private void saveToLog(Map<String, String> request, Map<String, String> response) {
        try {
            ProcessingLog log = ProcessingLog.builder()
                    .requestData(objectMapper.writeValueAsString(request))
                    .responseData(objectMapper.writeValueAsString(response))
                    .build();

            logRepository.save(log);
        } catch (JsonProcessingException e) {
            System.err.println("Error serializing data for log: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error saving log to database: " + e.getMessage());
        }
    }
}