package com.ravindra.controller;

import com.ravindra.service.LockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lock")
public class LockController {

    @Autowired
    private LockService lockService;

    @GetMapping("/aquire")
    public String aquireLock(@RequestParam("connector") String connector)
    {
        return lockService.aquireLock(connector);
    }
}
