package br.senac.rj.api.service;

import br.senac.rj.api.exceptions.ResourceNotFoundException;
import br.senac.rj.api.model.Lingua;
import br.senac.rj.api.repository.LinguaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LinguaService {

    @Autowired
    private LinguaRepository linguaRepository;

    public List<Lingua> listarLinguas() {
        List<Lingua> linguas = this.linguaRepository.findAll();
        if (linguas.isEmpty()) {
            throw new ResourceNotFoundException("Nao ha linguas cadastradas");
        }
        return linguas;
    }

    public Lingua incluirLingua(Lingua lingua) {
        return this.linguaRepository.save(lingua);
    }

    public Lingua buscarLinguaPorCodigo(Long codigo) {
        String mensagem = "Lingua com o codigo [" + codigo + "] nao encontrado";
        Optional<Lingua> lingua = this.linguaRepository.findById(codigo);
        if (lingua.isEmpty()) {
            throw new ResourceNotFoundException(mensagem);
        }

        Lingua l = lingua.get();
        return l;
    }

    public void excluirLingua(Long codigo) {
        try {
            this.linguaRepository.deleteById(codigo);
        } catch (EmptyResultDataAccessException erdae) {
            throw new ResourceNotFoundException("Lingua com o codigo [" + codigo + "nao encontrado");
        }
    }

    public Lingua atualizarLingua(Long codigo, Lingua linguaAtualizado) {
        Optional<Lingua> lingua = this.linguaRepository.findById(codigo);
        if (lingua.isPresent()) {
            Lingua linguaAjustado = lingua.get();
            linguaAjustado.setDescricao(linguaAtualizado.getDescricao());
            return this.linguaRepository.save(linguaAjustado);
        } else {
            throw new ResourceNotFoundException("Lingua com o codigo [" + codigo + "nao encontrado");
        }
    }

}
