package br.senac.rj.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.SSLEngineResult;

@RestController
@RequestMapping("/hello")
public class HelloWorldController {

    @GetMapping
    public String olaMundo() {
        return "<h1>Hello World again novamente!</h1>";
    }

    @GetMapping("/voce/{nome}")
    public ResponseEntity<?> olaVoce(@PathVariable String nome) {
        if (nome != null) {
            nome = nome + ".";
        } else {
            nome = "Não recebi nome";
        }
        return ResponseEntity.ok("Hello, " + nome);
    }

}
