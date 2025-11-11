package br.senac.rj.api.controller;

import br.senac.rj.api.exceptions.ResourceNotFoundException;
import br.senac.rj.api.model.Lingua;
import br.senac.rj.api.model.Livro;
import br.senac.rj.api.service.LinguaService;
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
    private final LinguaService linguaService;

    private LivroWebController(LivroService livroService, LinguaService linguaService) {
        this.livroService=livroService;
        this.linguaService=linguaService;
    }

    @GetMapping
    public String mostrarLivros(Model model) {
        List<Livro> livros = livroService.listarLivros();
        model.addAttribute("livros", livros);
        return "livros/index";
    }

    @GetMapping("/registrar")
    public String mostrarFormIncluirLivro(Model model) {
        model.addAttribute("livro", new Livro());
        model.addAttribute("linguas", linguaService.listarLinguas());
        return "livros/registrar";
    }

    @PostMapping("/registrar")
    public String incluirLivro(@ModelAttribute Livro livro, RedirectAttributes redirectAttributes) {
        this.livroService.incluirLivro(livro);
        redirectAttributes.addFlashAttribute("msg", "Livro incluído com sucesso");
        return "redirect:/livros";
    }

    @GetMapping("/editar")
    public String mostrarFormEditarLivro(@RequestParam("codigo") Long codigo,  Model model) {
        Livro livro = this.livroService.buscarLivroPorCodigo(codigo);
        if (livro != null) {
            model.addAttribute("livro", livro);
            model.addAttribute("linguas", linguaService.listarLinguas());
            return "livros/editar";
        }
        return "redirect:/livros";
    }

    @PostMapping("/editar")
    public String editarLivro(@RequestParam("codigo") Long codigo, @ModelAttribute Livro livro, RedirectAttributes redirectAttributes) {
        try {
            this.livroService.atualizarLivro(codigo, livro);
            redirectAttributes.addFlashAttribute("msg", "Livro atualizado com sucesso");
        } catch (RuntimeException re) {
            redirectAttributes.addFlashAttribute("msg", "Livro não encontrado!");
        }
        return "redirect:/livros";
    }

    @GetMapping("/excluir")
    public String excluirLivro(@RequestParam("codigo") Long codigo, RedirectAttributes redirectAttributes) {
        try {
            this.livroService.excluirLivro(codigo);
            redirectAttributes.addFlashAttribute("msg", "Livro excluído com sucesso");
        } catch (RuntimeException re) {
            redirectAttributes.addFlashAttribute("msg", "Livro não encontrado!");
        }
        return "redirect:/livros";
    }

}













