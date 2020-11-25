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
    public String afterConverting(@RequestParam(value = "codeToEncode", required = false) String codeToEncode,
                                  @RequestParam(value = "spaghettiCode", required = false) String spaghettiCode,
                                  @RequestParam(value = "randomizeVariables", required = false) String randomizeVariables,
                                  @RequestParam(value = "length", required = false) Integer randomStringsLength,
                                  Model model) throws IOException {
        if (codeToEncode != null) {
            String finalCode = codeToEncode;
            if (randomizeVariables != null) {
                if (randomStringsLength != null) {
                    finalCode = service.encode(codeToEncode, randomStringsLength);
                } else {
                    finalCode = service.encode(codeToEncode, 6);
                }

            }

            if (spaghettiCode != null) {
                finalCode = service.convertCodeToSpaghetti(finalCode);
            }
            model.addAttribute("codeToEncode", codeToEncode);
            model.addAttribute("encodedCode", finalCode);
        }
        return "index";
    }
}