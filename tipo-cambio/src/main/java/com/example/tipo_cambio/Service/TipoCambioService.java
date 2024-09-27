package com.example.tipo_cambio.Service;
import com.example.tipo_cambio.Model.TipoCambio;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.*;

@Service
public class TipoCambioService {
    private static final String WSDL_URL = "https://www.banguat.gob.gt/variables/ws/TipoCambio.asmx";

    public TipoCambio obtenerTipoCambio() throws Exception {
        String soapAction = "http://www.banguat.gob.gt/variables/ws/TipoCambioDiaString";
        String requestBody =
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                        + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                        + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" "
                        + "xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                        + "<soap:Body>"
                        + "<TipoCambioDiaString xmlns=\"http://www.banguat.gob.gt/variables/ws/\" />"
                        + "</soap:Body>"
                        + "</soap:Envelope>";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_XML);
        headers.add("SOAPAction", soapAction);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(WSDL_URL, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return parseResponse(response.getBody());
        } else {
            throw new Exception("Error al consumir el servicio SOAP: " + response.getStatusCode());
        }
    }

    private TipoCambio parseResponse(String xmlResponse) throws Exception {
        TipoCambio tipoCambio = new TipoCambio();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new java.io.ByteArrayInputStream(xmlResponse.getBytes()));

        NodeList returnList = doc.getElementsByTagName("TipoCambioDiaStringResult");
        if (returnList.getLength() > 0) {
            String result = returnList.item(0).getTextContent();

            String[] parts = result.split("\\|");
            if (parts.length >= 4) {
                tipoCambio.setMoneda(parts[0]);
                tipoCambio.setCompra(parts[1]);
                tipoCambio.setVenta(parts[2]);
                tipoCambio.setFecha(parts[3]);
            }
        }

        return tipoCambio;
    }
}
