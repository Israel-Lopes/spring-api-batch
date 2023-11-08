package br.com.spring.api.batch.web.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class RegisterService {
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job importUserJob;
    public ResponseEntity createRegister(MultipartFile file) {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("jobParam", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();

        try {
            JobExecution jobExecution = jobLauncher.run(importUserJob, jobParameters);
            // Verifique o status do jobExecution e retorne a resposta apropriada com base nisso

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
