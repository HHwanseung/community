package comu.community.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Sample {

    @RequestMapping("/sample")
    public String sample() {
        return "sample";
    }
}
