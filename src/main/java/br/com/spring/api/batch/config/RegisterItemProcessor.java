package br.com.spring.api.batch.config;

import br.com.spring.api.batch.persistence.entity.RegisterEntity;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class RegisterItemProcessor implements ItemProcessor<RegisterEntity, RegisterEntity> {
    @Override
    public RegisterEntity process(RegisterEntity item) throws Exception {
        // Você pode adicionar lógica de processamento aqui, se necessário
        return item;
    }
}
