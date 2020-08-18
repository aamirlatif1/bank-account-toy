package com.dkb.bankaccount.config;

import com.dkb.bankaccount.dto.TransactionDTO;
import com.dkb.bankaccount.entity.Transaction;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfigurations {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper mapper = new ModelMapper();
        PropertyMap<Transaction, TransactionDTO> transactionMap = new PropertyMap <Transaction, TransactionDTO>() {
            protected void configure() {
                map().setTransactionId(source.getId());
            }
        };

        mapper.addMappings(transactionMap);
        return mapper;
    }
}
