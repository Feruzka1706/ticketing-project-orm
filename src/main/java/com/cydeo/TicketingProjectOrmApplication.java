package com.cydeo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(exclude = MessageSourceAutoConfiguration.class)
public class TicketingProjectOrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketingProjectOrmApplication.class, args);
    }

    /**
     * Since ModelMapper is not my class (3rd party library class,
     * I need to configure @Bean explicitly inside Configuration class
     * Spring Boot application annotation is coming together with
     * @Configuration and @ComponentScan we can use this Main RUN class
     * as Configuration and adding ModelMapper Bean configuration method
     * to create Bean object and add to Spring container as bean object.
     */
    @Bean
    public ModelMapper mapper(){
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
