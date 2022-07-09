package br.com.itau.pixexample;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
public class PixExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(PixExampleApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
//        modelMapper.addConverter(uuidConverter());
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return new ModelMapper();
    }

    private Converter uuidConverter() {
        return (Converter<String, UUID>) context -> context.getSource() == null ? null : UUID.fromString(context.getSource());
    }

}
