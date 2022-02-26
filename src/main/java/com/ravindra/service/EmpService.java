package com.ravindra.service;

import com.ravindra.data.Employee;
import com.ravindra.data.Name;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class EmpService {

    public List<Employee> emps(boolean isNameNotNull) {
        List<Employee> employees = new ArrayList<>();
        Employee e1 = new Employee();
        e1.setName("Veera Kakarla");
        e1.setOrgName("INF");
        e1.setAddress("Hyd");
        if(isNameNotNull)
        {
            Name nameObj = new Name();
            nameObj.setLName("Kakarla");
            nameObj.setMName("Ravindra");
            nameObj.setFName("Veera");
            e1.setNameobj(nameObj);
        }
        employees.add(e1);
        return employees;
    }

    public List<Employee> empDetails() {
        return emps(false);
    }

    public List<Employee> empDetailsV2() {
        return emps(true);
    }
}