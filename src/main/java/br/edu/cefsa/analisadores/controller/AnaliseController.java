/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.cefsa.analisadores.controller;

/**
 *
 * @author evand
 */
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.cefsa.analisadores.lexico.AnalisadorLexico;
import br.edu.cefsa.analisadores.lexico.Token;
import br.edu.cefsa.analisadores.lexico.TipoToken;
import br.edu.cefsa.analisadores.semantico.AnalisadorSemantico;
import br.edu.cefsa.analisadores.semantico.TipoVariavel;
import br.edu.cefsa.analisadores.sintatico.AnalisadorSintatico;
import br.edu.cefsa.analisadores.sintatico.ArvoreSintatica;

import java.util.List;

@Controller
public class AnaliseController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/analisar")
    public String analisar(@RequestParam String codigo, Model model) {
        try {
            // Análise Léxica
            AnalisadorLexico lexico = new AnalisadorLexico(codigo);
            List<Token> tokens = lexico.analisar();
            
            // Análise Sintática
            AnalisadorSintatico sintatico = new AnalisadorSintatico(tokens);
            ArvoreSintatica arvore = sintatico.analisar();
            
            // Análise Semântica
            AnalisadorSemantico semantico = new AnalisadorSemantico();
            semantico.adicionarVariavel("x", TipoVariavel.INTEIRO);
            semantico.adicionarVariavel("y", TipoVariavel.INTEIRO);
            semantico.analisar(arvore);
            
            // Adiciona resultados ao modelo
            model.addAttribute("tokens", tokens);
            model.addAttribute("arvore", arvore.toString());
            model.addAttribute("semantica", "Análise semântica concluída sem erros!");
            model.addAttribute("erro", null);
            
        } catch (Exception e) {
            model.addAttribute("erro", e.getMessage());
        }
        
        model.addAttribute("codigo", codigo);
        return "index";
    }
}
