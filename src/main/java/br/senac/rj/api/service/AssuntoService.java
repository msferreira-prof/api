package br.senac.rj.api.service;

import br.senac.rj.api.exceptions.ResourceNotFoundException;
import br.senac.rj.api.model.Assunto;
import br.senac.rj.api.repository.AssuntoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssuntoService {

    @Autowired
    private AssuntoRepository assuntoRepository;

    public List<Assunto> listarAssuntos() {
        List<Assunto> assuntos = this.assuntoRepository.findAll();
        if (assuntos.isEmpty()) {
            throw new ResourceNotFoundException("Nao ha assuntos cadastrados");
        }
        return assuntos;
    }

    public Assunto incluirAssunto(Assunto assunto) {
        return this.assuntoRepository.save(assunto);
    }

    public Assunto buscarAssuntoPorCodigo(Long codigo) {
        String mensagem = "Assunto com o codigo [" + codigo + "] nao encontrado";
        Optional<Assunto> assunto = this.assuntoRepository.findById(codigo);
        if (assunto.isEmpty()) {
            throw new ResourceNotFoundException(mensagem);
        }

        Assunto l = assunto.get();
        return l;
    }

    public void excluirAssunto(Long codigo) {
        try {
            this.assuntoRepository.deleteById(codigo);
        } catch (EmptyResultDataAccessException erdae) {
            throw new ResourceNotFoundException("Assunto com o codigo [" + codigo + "nao encontrado");
        }
    }

    public Assunto atualizarAssunto(Long codigo, Assunto assuntoAtualizado) {
        Optional<Assunto> assunto = this.assuntoRepository.findById(codigo);
        if (assunto.isPresent()) {
            Assunto assuntoAjustado = assunto.get();
            assuntoAjustado.setDescricao(assuntoAtualizado.getDescricao());
            return this.assuntoRepository.save(assuntoAjustado);
        } else {
            throw new ResourceNotFoundException("Assunto com o codigo [" + codigo + "nao encontrado");
        }
    }

}
