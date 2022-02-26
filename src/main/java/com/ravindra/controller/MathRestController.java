package com.ravindra.controller;

import com.ravindra.exceptions.CustomDataException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/math")
public class MathRestController {
    @GetMapping("/multiply")
    public int multiply(@RequestParam("a") String a, @RequestParam("b") String b)
    {
        return multiplyLogic(a, b);
    }

    private int multiplyLogic(String a, String b)//10, Test
    {
        int result=0;
        try
        {
            result = Integer.parseInt(a)*Integer.parseInt(b);
        } catch(NumberFormatException e)
        {
            System.out.println(e.getMessage());
            throw new CustomDataException("There is issue with the input data. please correct the data.");
        }
        return result;
    }

}