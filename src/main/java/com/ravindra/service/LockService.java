package com.ravindra.service;

import org.springframework.stereotype.Service;

@Service
public class LockService {
    public String aquireLock(String connector) {
        synchronized (connector) {
            if(null!=connector) {
                System.out.println("....Before....");
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("...After.....");
            }
        }
        return "Done";
    }
}
