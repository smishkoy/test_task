package com.mishkoy.dataapi.controller;

import com.mishkoy.dataapi.dto.TransformRequest;
import com.mishkoy.dataapi.dto.TransformResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TransformController {

    @PostMapping("/transform")
    public TransformResponse transform(@RequestBody TransformRequest request) {
        String transformed = new StringBuilder(request.text().toUpperCase())
                .reverse()
                .toString();

        return new TransformResponse(transformed);
    }
}