package br.senac.rj.api.service;

import br.senac.rj.api.exceptions.ResourceNotFoundException;
import br.senac.rj.api.model.Livro;
import br.senac.rj.api.repository.LivroRepository;
import br.senac.rj.api.validation.LivroValidation;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public List<Livro> listarLivros() {
        List<Livro> livros = this.livroRepository.findAll();
        if (livros.isEmpty()) {
            throw new ResourceNotFoundException("Nao ha livros cadastrados");
        }
        return livros;
    }

    public Livro incluirLivro(Livro livro) {
        if (LivroValidation.validarLivro(livro)) {
            return this.livroRepository.save(livro);
        } else {
            throw new RuntimeException("Dados do livro invalidos");
        }
    }

    public Livro buscarLivroPorCodigo(Long codigo) {
        String mensagem = "Livro com o codigo [" + codigo + "] nao encontrado";
        Optional<Livro> livro = this.livroRepository.findById(codigo);
        if (livro.isEmpty()) {
            throw new ResourceNotFoundException(mensagem);
        }

        Livro l = livro.get();
        return l;
    }

    public void excluirLivro(Long codigo) {
        try {
            this.livroRepository.deleteById(codigo);
        } catch (EmptyResultDataAccessException erdae) {
            throw new ResourceNotFoundException("Livro com o codigo [" + codigo + "nao encontrado");
        }
    }

    public Livro atualizarLivro(Long codigo, Livro livroAtualizado) {
        Optional<Livro> livro = this.livroRepository.findById(codigo);
        if (livro.isPresent()) {
            Livro livroAjustado = livro.get();
            livroAjustado.setTitulo(livroAtualizado.getTitulo());
            livroAjustado.setPreco(livroAtualizado.getPreco());
            return this.livroRepository.save(livroAjustado);
        } else {
            throw new ResourceNotFoundException("Livro com o codigo [" + codigo + "nao encontrado");
        }
    }

}
