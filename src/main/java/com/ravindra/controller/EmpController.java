package com.ravindra.controller;

import com.ravindra.data.Employee;
import com.ravindra.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/emp")
public class EmpController {

    @Autowired
    private EmpService empService;

    @GetMapping("/empdetails")
    public List<Employee> empDetails()
    {
        return empService.empDetails();
    }

    //@GetMapping("/v2/empdetails")
    @GetMapping(value = "/empdetails", params = "version=v2")
    public List<Employee> empDetailsV2()
    {
        return empService.empDetailsV2();
    }
}