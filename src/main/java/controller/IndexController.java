package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import service.EncoderService;

@Controller
public class IndexController {
    private EncoderService service;

    @Autowired
    public IndexController(EncoderService service) {
        this.service = service;
    }

    @GetMapping(value = {"/", "/index"})
    public String index() {
        return "index";
    }
}

