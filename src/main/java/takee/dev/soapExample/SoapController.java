package takee.dev.soapExample;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import takee.dev.soapExample.service.SoapClientService;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
@Tag(name = "Soap Controller For Send To Soap", description = "Learning Soap")
public class SoapController {

    private final RestTemplate restTemplate;
    private final SoapClientService soapClientService;

    @GetMapping("/getRequest")
    @Operation(summary = "Send To Soap", description = "Test Send To Soap")
    public ResponseEntity getMethodName() throws Exception {

        String requestXml = setRequest();

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_XML);
        headers.add("SOAPAction", "http://www.oorsprong.org/websamples.countryinfo/CapitalCity");
        headers.setBasicAuth("username", "P@ssword");

        HttpEntity<String> entity = new HttpEntity<>(requestXml, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso",
                HttpMethod.POST,
                entity,
                String.class
        );

        String body = response.getBody();
        log.info(body);
        assert body != null;
        String capital = extractCapitalCity(body);
        return new ResponseEntity<>(capital, HttpStatus.OK);
    }

    private static String setRequest() {

        var countryCode = "TH";

        return String.format("""
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                                  xmlns:web="http://www.oorsprong.org/websamples.countryinfo">
                   <soapenv:Header/>
                   <soapenv:Body>
                      <web:CapitalCity>
                         <web:sCountryISOCode>%s</web:sCountryISOCode>
                      </web:CapitalCity>
                   </soapenv:Body>
                </soapenv:Envelope>
                """, countryCode);
    }
     private String extractCapitalCity(String xml) throws Exception {
        var doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                .parse(new java.io.ByteArrayInputStream(xml.getBytes()));


        XPath xpath = XPathFactory.newInstance().newXPath();
        String expression = "//*[local-name()='CapitalCityResult']/text()";
        return xpath.evaluate(expression, doc);
    }

    @GetMapping("/getSoap")
    @Operation(summary = "Send To Soap", description = "Test Send To Soap")
    public ResponseEntity getSoapAPI() {
        var response = soapClientService.capitalCity("TH");
        return new ResponseEntity<>(response.getCapitalCityResult(), HttpStatus.OK);
    } }
