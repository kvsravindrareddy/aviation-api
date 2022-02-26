package com.ravindra.service;

import com.ravindra.common.ApiConstants;
import com.ravindra.config.AviationConfig;
import com.ravindra.data.AviationData;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AviationService {
    //private static final Logger log = LoggerFactory.getLogger(AviationService.class);

    @Autowired
    private AviationConfig aviationConfig;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * This is to get the AVI api response
     * @param icao
     * @return
     */
    public List<AviationData> getAviationData(String icao) {
        log.info("Begin method getAviationData()");
        List<AviationData> aviationDataList = null;
        //String url = aviationConfig.getBaseUrl()+"/v1/airports?apt="+icao;
        String url = buildAviationUrl(icao);//https://api.aviationapi.com/v1/airports?apt=KSPI
        log.debug("Aviation Url : ",url);
        String apiResponse = apiResponse(url);//json string
        if(null!=apiResponse)
        {
            aviationDataList = new ArrayList<>();
            JSONObject mainJsonObj = new JSONObject(apiResponse);
            JSONArray mainJsonArray = mainJsonObj.getJSONArray(icao.toUpperCase());
            AviationData aviationData = null;
            for(Object genericObj : mainJsonArray) {
                aviationData = new AviationData();
                JSONObject innerJsonObj = (JSONObject) genericObj;
                aviationData.setSiteNumber(innerJsonObj.getString("site_number"));
                aviationData.setCity(innerJsonObj.getString("city"));
                aviationData.setState(innerJsonObj.getString("state_full"));
                aviationData.setCounty(innerJsonObj.getString("county"));
                aviationData.setLatitude(innerJsonObj.getString("latitude"));
                aviationData.setLongitude(innerJsonObj.getString("longitude"));
                aviationData.setType(innerJsonObj.getString("type"));
                aviationData.setMilitaryLanding(innerJsonObj.getString("military_landing"));
                aviationDataList.add(aviationData);
            }
        }
        return aviationDataList;
    }

    /**
     * This will consume the api and convert the response into json string
     * @param url
     * @return
     */
    private String apiResponse(String url) {

        String apiResponse = null;
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
            if (responseEntity.getStatusCodeValue() >= 200 && responseEntity.getStatusCodeValue() < 300) {
                apiResponse = responseEntity.getBody();
            } else {
                log.warn("There is no response body");
            }
        }catch (Exception e)
        {
            log.error("Exception occurred"+e.getMessage());
        }
        return apiResponse;
    }

    /**
     * It uses URIComponent builder and convert the url as a string
     * @param icao
     * @return
     */
    private String buildAviationUrl(String icao) {
        return UriComponentsBuilder.fromHttpUrl(aviationConfig.getBaseUrl()).path(ApiConstants.AVIATION_PATH).queryParam("apt",icao).build().toUriString();
    }
}