package br.senac.rj.api.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "codigo")
@Entity
@Table(name="livros")
public class Livro {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long codigo;

    private String titulo;

    private Double preco;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="lingua_codigo")
    private Lingua lingua;

    public Livro() {
    }

    public Livro(String titulo, Double preco) {
        this.titulo = titulo;
        this.preco = preco;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Lingua getLingua() {
        return lingua;
    }

    public void setLingua(Lingua lingua) {
        this.lingua = lingua;
    }
}
