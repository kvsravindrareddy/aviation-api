package com.ravindra.util;

import lombok.EqualsAndHashCode;

import java.util.Objects;

public class Employee {
    private String id;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) && Objects.equals(name, employee.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        Employee chandra = new Employee();
        chandra.setId("123");
        chandra.setName("Chandra");
        Employee chandra2 = new Employee();
        chandra2.setId("123");
        chandra2.setName("Chandra");
        Employee naga = new Employee();

        System.out.println("chandra ref : "+chandra);
        System.out.println("chandra2 ref : "+chandra2);
        if(chandra.equals(chandra2))
        {
            System.out.println("It is equal");
        } else {
            System.out.println("not equal");
        }
        //Chandra --> Equal, Dhandapa --> Equal, Srinivas-->Equal, Naga--> Not equal, Ashok--> Equals
    }
}
