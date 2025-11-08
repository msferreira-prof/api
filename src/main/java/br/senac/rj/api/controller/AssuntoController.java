package br.senac.rj.api.controller;

import br.senac.rj.api.exceptions.ResourceNotFoundException;
import br.senac.rj.api.model.Assunto;
import br.senac.rj.api.service.AssuntoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AssuntoController {

    private final AssuntoService assuntoService;

    public AssuntoController(AssuntoService assuntoService) {
        this.assuntoService = assuntoService;
    }

    @GetMapping("/assuntos")
    public ResponseEntity<?> listarAssuntos() {
        try {
            return ResponseEntity.ok(this.assuntoService.listarAssuntos());
        } catch (ResourceNotFoundException rnfe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rnfe.getMessage());
        }
    }

    @PostMapping("/assuntos")
    public ResponseEntity<?> incluirAssunto(@RequestBody Assunto assunto) {
        try {
            Assunto assuntoNovo = this.assuntoService.incluirAssunto(assunto);
            return ResponseEntity.status(HttpStatus.OK).body(assuntoNovo);
        } catch (RuntimeException re) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(re.getMessage());
        }
    }

    @PutMapping("/assuntos/{codigo}")
    public ResponseEntity<?> atualizarAssunto(@PathVariable Long codigo, @RequestBody Assunto assunto) {
        try {
            Assunto assuntoAtualizado = this.assuntoService.atualizarAssunto(codigo, assunto);
            return ResponseEntity.status(HttpStatus.OK).body(assuntoAtualizado);
        } catch (ResourceNotFoundException rnfe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rnfe.getMessage());
        }
    }

    @DeleteMapping("/assuntos/{codigo}")
    public ResponseEntity<?> excluirAssunto(@PathVariable Long codigo) {
        try {
            this.assuntoService.excluirAssunto(codigo);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (ResourceNotFoundException rnfe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rnfe.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
        }
    }

    @GetMapping("/assuntos/{codigo}")
    public ResponseEntity<?> buscarAssuntoPorCodigo(@PathVariable Long codigo) {
        try {
            Assunto assunto = this.assuntoService.buscarAssuntoPorCodigo(codigo);
            return ResponseEntity.ok(assunto);
        } catch (ResourceNotFoundException rnfe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rnfe.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
        }
    }
}
