package com.ecommerce.productservice.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sample")
public class SampleController {

    @GetMapping("/sayHello/{name}")
    public String sample(@PathVariable String name) {
        return "Hello World! " + name;
    }

    @GetMapping("/sayHello2")
    public String sample2(@RequestParam("x") int x) {
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < x; i++) {
            sb.append("Hello world\n");
        }

        return sb.toString();
    }
}
