package com.bromel.ejb;

import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;

@Stateless
@Remote(HelloRemote.class)
public class HelloBean implements HelloRemote {
    public String sayHello(String name) {
        return "Hello, " + name + "!";
    }
}
