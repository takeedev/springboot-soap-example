package takee.dev.soapExample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SoapConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
