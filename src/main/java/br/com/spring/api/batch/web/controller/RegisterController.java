package br.com.spring.api.batch.web.controller;

import br.com.spring.api.batch.web.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/csv")
public class RegisterController {
    @Autowired(required=true)
    private RegisterService service;
    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity uploadCSVFile(@RequestParam("file") MultipartFile file) {
        return service.createRegister(file);
    }
}
