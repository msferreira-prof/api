package br.senac.rj.api.model;

import jakarta.persistence.*;
import java.util.List;

@Entity(name="linguas")
public class Lingua {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    private String descricao;

    @OneToMany(
            mappedBy = "lingua",
            cascade = CascadeType.ALL
    )
    private List<Livro> livros;

    public Lingua() {

    }

    public Lingua(Long codigo) {
        this.codigo = codigo;
    }

    public Lingua(String descricao) {
        this.descricao = descricao;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }
}
