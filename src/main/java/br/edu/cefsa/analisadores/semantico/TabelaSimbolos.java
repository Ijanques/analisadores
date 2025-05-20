/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.cefsa.analisadores.semantico;

/**
 *
 * @author evand
 */
import br.edu.cefsa.analisadores.lexico.TipoToken;
import br.edu.cefsa.analisadores.lexico.Token;
import java.util.Objects;
import java.util.Stack;

public class TabelaSimbolos {

    private Stack<Escopo> pilhaEscopos;

    // Construtor
    public TabelaSimbolos() {
        this.pilhaEscopos = new Stack<>();
        this.pilhaEscopos.push(new Escopo(null)); // Escopo global
    }

    // Método para entrar em um novo escopo
    public void entrarEscopo() {
        pilhaEscopos.push(new Escopo(pilhaEscopos.peek()));
    }

    // Método para sair do escopo atual
    public void sairEscopo() {
        if (pilhaEscopos.size() > 1) {
            pilhaEscopos.pop();
        }
    }

    // Método para adicionar um símbolo
    public boolean adicionarSimbolo(String nome, TipoVariavel tipo) {
        return pilhaEscopos.peek().adicionarSimbolo(nome, tipo);
    }

    // Método para buscar um símbolo
    public TipoVariavel buscarSimbolo(String nome) {
        return pilhaEscopos.peek().buscarSimbolo(nome);
    }

    // Método para verificar declaração
    public void verificarDeclaracao(Token token) {
        if (token.getTipo() != TipoToken.IDENTIFICADOR) {
            return;
        }

        if (buscarSimbolo(token.getLexema()) == null) {
            throw new RuntimeException("Variável não declarada: " + token.getLexema()
                    + " na linha " + token.getLinha()
                    + ", coluna " + token.getColuna());
        }
    }

    public TipoVariavel inferirTipo(Token token) {
        if (token.getTipo() == TipoToken.NUMERO) {
            return TipoVariavel.INTEIRO;
        } else if (token.getTipo() == TipoToken.IDENTIFICADOR) {
            TipoVariavel tipo = buscarSimbolo(token.getLexema());
            if (tipo == null) {
                return TipoVariavel.DESCONHECIDO;
            }
            return tipo;
        }
        return TipoVariavel.DESCONHECIDO;
    }
    
}
