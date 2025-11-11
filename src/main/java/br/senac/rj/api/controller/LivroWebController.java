package br.senac.rj.api.controller;

import br.senac.rj.api.model.Livro;
import br.senac.rj.api.service.LivroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/livros")
public class LivroWebController {

    private final LivroService livroService;

    public LivroWebController(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "livros/index";
    }

    @GetMapping()
    public String mostrarLivros(Model model) {
        List<Livro> livros = this.livroService.listarLivros();
        model.addAttribute("livros", livros);
        return "livros/index";
    }

    @PostMapping("/registrar")
    public String incluirLivro(@ModelAttribute Livro livro, RedirectAttributes redirectAttributes) {
        this.livroService.incluirLivro(livro);
        redirectAttributes.addFlashAttribute("msg", "Livro incluido com sucesso!");
        return "redirect:/livros";
    }


    @GetMapping("/registrar")
    public String mostrarFormIncluirLivro(Model model) {
        model.addAttribute("livro", new Livro());
        return "livros/registrar";
    }


    @GetMapping("/editar")
    public String mostrarFormEditarLivro(@RequestParam("codigo") Long codigo, Model model) {
        Livro livro = this.livroService.buscarLivroPorCodigo(codigo);
        if (livro != null) {
            model.addAttribute("livro", livro);
            return "livros/editar";
        }
        return "redirect:/livros";
    }

    @PostMapping("/editar")
    public String editarLivro(@RequestParam("codigo") Long codigo, @ModelAttribute Livro livro, RedirectAttributes redirectAttributes) {
        try {
            this.livroService.atualizarLivro(codigo, livro);
            redirectAttributes.addFlashAttribute("msg", "Livro atualizado com sucesso!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("erro", "Livro não encontrado!");
        }
        return "redirect:/livros";
    }

    @GetMapping("/excluir")
    public String editarLivro(@RequestParam("codigo") Long codigo, RedirectAttributes redirectAttributes) {
        try {
            this.livroService.excluirLivro(codigo);
            redirectAttributes.addFlashAttribute("msg", "Livro excluido com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir!");
        }
        return "redirect:/livros";
    }
}
