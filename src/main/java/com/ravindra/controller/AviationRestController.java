package com.ravindra.controller;

import com.ravindra.data.AviationData;
import com.ravindra.service.AviationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aviation")
public class AviationRestController {

    @Autowired
    private AviationService aviationService;

    //localhost:8080/product/getdata (product)
    //localhost:8080/catalog/getdata (catalog)

    //localhost:8080/aviation/aviationdata?ICAO=testairportauth
    //localhost:8080/aviation/aviationdata/testairportauth
    //@GetMapping(value = "/aviationdata", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @RequestMapping(value="/aviationdata", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<AviationData> aviationData(@RequestParam(value="ICAO", required = false) String icao)
    {
        return aviationService.getAviationData(icao);
    }

    @RequestMapping(value = "aviationsdata", method = RequestMethod.GET)
    public List<AviationData> findAllFromDB()
    {
        return aviationService.getAllDataForMilataryLanding();
    }
}