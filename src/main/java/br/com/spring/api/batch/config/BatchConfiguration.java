package br.com.spring.api.batch.config;

import br.com.spring.api.batch.persistence.entity.RegisterEntity;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;



@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<RegisterEntity> reader() {
        FlatFileItemReader<RegisterEntity> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("seuarquivo.csv"));
        reader.setLineMapper(new DefaultLineMapper<RegisterEntity>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("name", "price");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<RegisterEntity>() {{
                setTargetType(RegisterEntity.class);
            }});
        }});
        return reader;
    }
    @Bean
    public RegisterItemProcessor processor() {
        return new RegisterItemProcessor();
    }
    @Bean
    public JpaItemWriter<RegisterEntity> writer(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<RegisterEntity> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
    @Bean
    public Step step1(ItemReader<RegisterEntity> reader, ItemWriter<RegisterEntity> writer,
                      ItemProcessor<RegisterEntity, RegisterEntity> processor) {
        return stepBuilderFactory
                .get("step1")
                .<RegisterEntity, RegisterEntity>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory
                .get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }
}
