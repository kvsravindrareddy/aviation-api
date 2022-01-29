package com.ravindra.controller;

import com.ravindra.data.AviationData;
import com.ravindra.service.AviationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AviationRestController {

    @Autowired
    private AviationService aviationService;

    @GetMapping("/aviationdata")
    public List<AviationData> aviationData(@RequestParam("ICAO") String icao)
    {
        return aviationService.getAviationData(icao);
    }
}
