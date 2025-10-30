package br.senac.rj.api.controller;

import br.senac.rj.api.exceptions.ResourceNotFoundException;
import br.senac.rj.api.model.Livro;
import br.senac.rj.api.service.LivroService;
import br.senac.rj.api.validation.LivroValidation;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping("/livros")
    public ResponseEntity<?> listarLivros() {
        try {
            return ResponseEntity.ok(this.livroService.listarLivros());
        } catch (ResourceNotFoundException rnfe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rnfe.getMessage());
        }
    }

    @PostMapping("/livros")
    public ResponseEntity<?> incluirLivro(@RequestBody Livro livro) {
        try {
            Livro livroNovo = this.livroService.incluirLivro(livro);
            return ResponseEntity.status(HttpStatus.OK).body(livroNovo);
        } catch (RuntimeException re) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(re.getMessage());
        }
    }

    @PutMapping("/livros/{codigo}")
    public ResponseEntity<?> atualizarLivro(@PathVariable Long codigo, @RequestBody Livro livro) {
        try {
            Livro livroAtualizado = this.livroService.atualizarLivro(codigo, livro);
            return ResponseEntity.status(HttpStatus.OK).body(livroAtualizado);
        } catch (ResourceNotFoundException rnfe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rnfe.getMessage());
        }
    }

    @DeleteMapping("/livros/{codigo}")
    public ResponseEntity<?> excluirLivro(@PathVariable Long codigo) {
        try {
            this.livroService.excluirLivro(codigo);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (ResourceNotFoundException rnfe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rnfe.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
        }
    }

    @GetMapping("/livros/{codigo}")
    public ResponseEntity<?> buscarLivroPorCodigo(@PathVariable Long codigo) {
        try {
            Livro livro = this.livroService.buscarLivroPorCodigo(codigo);
            return ResponseEntity.ok(livro);
        } catch (ResourceNotFoundException rnfe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rnfe.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
        }
    }
}
