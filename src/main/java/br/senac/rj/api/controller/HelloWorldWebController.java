package br.senac.rj.api.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorldWebController {

    @GetMapping("/olamundo")
    @ResponseBody
    public String olaMundo() {
        return "Olá, Mundo!";
    }
}
