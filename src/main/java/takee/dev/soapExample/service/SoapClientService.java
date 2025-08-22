package takee.dev.soapExample.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import takee.dev.soapExample.soap.CapitalCity;
import takee.dev.soapExample.soap.CapitalCityResponse;
import takee.dev.soapExample.soap.CountryName;
import takee.dev.soapExample.soap.CountryNameResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class SoapClientService {

    private final WebServiceTemplate webServiceTemplate;

    public CapitalCityResponse capitalCity(String countryISOCode) {

        var request = new CapitalCity();
        request.setSCountryISOCode(countryISOCode);

        var response = sendSoapRequest(
                request,
                "http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso",
                "http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso/CapitalCity",
                CapitalCityResponse.class
        );
        log.info("Capital of {} = {}", countryISOCode, response.getCapitalCityResult());

        var countryName = new CountryName();
        countryName.setSCountryISOCode(countryISOCode);
        var countryNameResponse = sendSoapRequest(
                countryName,
                "http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso",
                "http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso/CountryName",
                CountryNameResponse.class
        );
        log.info("Country Name of {} = {}", countryISOCode, countryNameResponse.getCountryNameResult());

        return response;
    }

    private <T> T sendSoapRequest(Object request, String url, String soapAction, Class<T> responseType) {
        var callback = new SoapActionCallback(soapAction);
        Object rawResponse = webServiceTemplate.marshalSendAndReceive(url, request, callback);
        return responseType.cast(rawResponse);
    }

}
