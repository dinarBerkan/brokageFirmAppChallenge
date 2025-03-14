package com.challenge.brokagefirmapp.service;

import com.challenge.brokagefirmapp.request.MainRequest;
import com.challenge.brokagefirmapp.response.MainResponse;
import org.springframework.stereotype.Service;

@Service
public class MainService {
    public MainResponse mainOperation(MainRequest mainRequest) {
        return MainResponse.builder().output("Hello " + mainRequest.getInput()).build();
    }
}
