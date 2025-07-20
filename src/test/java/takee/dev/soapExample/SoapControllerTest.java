package takee.dev.soapExample;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SoapController.class)
class SoapControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RestTemplateBuilder restTemplateBuilder;

    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        RestTemplate restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.createServer(restTemplate);
        Mockito.when(restTemplateBuilder.build()).thenReturn(restTemplate);
    }

    @Test
    @DisplayName("Should Return Capital City")
    void shouldReturnCapitalCity() throws Exception {

        String mockSoapResponse = """
            <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <m:CapitalCityResponse xmlns:m="http://www.oorsprong.org/websamples.countryinfo">
                  <m:CapitalCityResult>Bangkok</m:CapitalCityResult>
                </m:CapitalCityResponse>
              </soap:Body>
            </soap:Envelope>
            """;

        mockServer.expect(ExpectedCount.once(),
                        MockRestRequestMatchers.requestTo("http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
                .andRespond(MockRestResponseCreators.withSuccess(mockSoapResponse, MediaType.TEXT_XML));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/getRequest"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Bangkok"));

    }
}