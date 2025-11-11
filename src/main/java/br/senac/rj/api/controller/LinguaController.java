package br.senac.rj.api.controller;

import br.senac.rj.api.exceptions.ResourceNotFoundException;
import br.senac.rj.api.model.Lingua;
import br.senac.rj.api.service.LinguaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class LinguaController {

    private final LinguaService linguaService;

    public LinguaController(LinguaService linguaService) {
        this.linguaService = linguaService;
    }

    @GetMapping("/linguas")
    public ResponseEntity<?> listarLinguas() {
        try {
            return ResponseEntity.ok(this.linguaService.listarLinguas());
        } catch (ResourceNotFoundException rnfe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rnfe.getMessage());
        }
    }

    @PostMapping("/linguas")
    public ResponseEntity<?> incluirLingua(@RequestBody Lingua lingua) {
        try {
            Lingua linguaNovo = this.linguaService.incluirLingua(lingua);
            return ResponseEntity.status(HttpStatus.OK).body(linguaNovo);
        } catch (RuntimeException re) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(re.getMessage());
        }
    }

    @PutMapping("/linguas/{codigo}")
    public ResponseEntity<?> atualizarLingua(@PathVariable Long codigo, @RequestBody Lingua lingua) {
        try {
            Lingua linguaAtualizado = this.linguaService.atualizarLingua(codigo, lingua);
            return ResponseEntity.status(HttpStatus.OK).body(linguaAtualizado);
        } catch (ResourceNotFoundException rnfe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rnfe.getMessage());
        }
    }

    @DeleteMapping("/linguas/{codigo}")
    public ResponseEntity<?> excluirLingua(@PathVariable Long codigo) {
        try {
            this.linguaService.excluirLingua(codigo);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (ResourceNotFoundException rnfe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rnfe.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
        }
    }

    @GetMapping("/linguas/{codigo}")
    public ResponseEntity<?> buscarLinguaPorCodigo(@PathVariable Long codigo) {
        try {
            Lingua lingua = this.linguaService.buscarLinguaPorCodigo(codigo);
            return ResponseEntity.ok(lingua);
        } catch (ResourceNotFoundException rnfe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rnfe.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
        }
    }
}
