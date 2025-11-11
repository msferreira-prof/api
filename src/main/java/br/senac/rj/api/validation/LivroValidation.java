package br.senac.rj.api.validation;

import br.senac.rj.api.model.Livro;

public class LivroValidation {
    public static boolean validarLivro(Livro livro) {
        boolean resultado = true;
        if (livro.getTitulo().isEmpty()) {
            resultado = false;
        } else if (null == livro.getLingua() || livro.getLingua().getCodigo() == 0 ) {
            resultado = false;
        } else if (livro.getPreco() <= 0d) {
            resultado = false;
        }
        return resultado;
    }
}
