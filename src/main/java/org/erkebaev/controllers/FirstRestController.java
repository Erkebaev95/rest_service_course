package org.erkebaev.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class FirstRestController {

    //@ResponseBody  - аннотация возвращает данные
    @GetMapping("/sayHello")
    public String sayHello() {

        return "Hello";
    }


}
