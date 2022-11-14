package codeview.main.common.infra;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebJsonConfig {

    @Bean
    public JacksonJsonParser jacksonJsonParser() {
        return new JacksonJsonParser();
    }
}
