package com.challenge.brokagefirmapp.controller;

import com.challenge.brokagefirmapp.request.MainRequest;
import com.challenge.brokagefirmapp.response.MainResponse;
import com.challenge.brokagefirmapp.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    private final MainService mainService;

    @Autowired
    MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping("/mainGet")
    public MainResponse mainGet(MainRequest mainRequest) {
        return mainService.mainOperation(mainRequest);
    }
}
