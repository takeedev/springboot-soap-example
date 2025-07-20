package takee.dev.soapExample;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

@WebMvcTest(SoapController.class)
class SoapControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RestTemplate restTemplate;

    @Test
    @DisplayName("Should Return Capital City")
    void shouldReturnCapitalCity() throws Exception {

        ResponseEntity<String> mockEntity = getStringResponseEntity();

        Mockito.when(restTemplate.exchange(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.eq(HttpMethod.POST),
                        Mockito.any(HttpEntity.class),
                        ArgumentMatchers.eq(String.class)))
                .thenReturn(mockEntity);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/getRequest"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Bangkok"));

    }

    private static ResponseEntity<String> getStringResponseEntity() {

        String mockSoapResponse = """
            <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <m:CapitalCityResponse xmlns:m="http://www.oorsprong.org/websamples.countryinfo">
                  <m:CapitalCityResult>Bangkok</m:CapitalCityResult>
                </m:CapitalCityResponse>
              </soap:Body>
            </soap:Envelope>
            """;

        return new ResponseEntity<>(mockSoapResponse, HttpStatus.OK);
    }
}