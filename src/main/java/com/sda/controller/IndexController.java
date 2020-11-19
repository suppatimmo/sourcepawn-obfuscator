package com.sda.controller;

import com.sda.service.EncoderService;
import com.sda.service.EncoderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class IndexController {
    EncoderServiceImpl service;

    @Autowired
    public IndexController(EncoderServiceImpl service) {
        this.service = service;
    }

    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {
        return "index";
    }

    @PostMapping("/index")
    public String afterConverting(@RequestParam(value="codeToEncode", required = false) String codeToEncode, Model model) throws IOException {
        if (codeToEncode != null) {
            String encodedCode = service.encode(codeToEncode);
            model.addAttribute("codeToEncode", codeToEncode);
            model.addAttribute("encodedCode", encodedCode);
        }
        return "index";
    }
}