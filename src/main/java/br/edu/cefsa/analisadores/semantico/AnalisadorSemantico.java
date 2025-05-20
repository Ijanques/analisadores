/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.cefsa.analisadores.semantico;

/**
 *
 * @author evand
 */
import br.edu.cefsa.analisadores.lexico.Token;
import br.edu.cefsa.analisadores.lexico.TipoToken;
import br.edu.cefsa.analisadores.sintatico.ArvoreSintatica;

public class AnalisadorSemantico {

    private TabelaSimbolos tabelaSimbolos;

    public AnalisadorSemantico() {
        this.tabelaSimbolos = new TabelaSimbolos();
    }

    public void analisar(ArvoreSintatica arvore) {
        analisarRecursivo(arvore);
    }

    private TipoVariavel analisarRecursivo(ArvoreSintatica no) {
        if (no == null) {
            return TipoVariavel.DESCONHECIDO;
        }

        Token token = no.getToken();

        if (token.getTipo() == TipoToken.IDENTIFICADOR) {
            tabelaSimbolos.verificarDeclaracao(token);
            return tabelaSimbolos.inferirTipo(token);
        } else if (token.getTipo() == TipoToken.NUMERO) {
            return TipoVariavel.INTEIRO;
        } else if (token.getTipo() == TipoToken.OP_IGUAL
                || token.getTipo() == TipoToken.OP_DIFERENTE) {
            TipoVariavel tipoEsq = analisarRecursivo(no.getEsquerda());
            TipoVariavel tipoDir = analisarRecursivo(no.getDireita());

            if (tipoEsq != tipoDir) {
                throw new RuntimeException("Tipos incompatíveis na operação " + token.getLexema()
                        + " na linha " + token.getLinha()
                        + ", coluna " + token.getColuna());
            }

            return TipoVariavel.BOOLEANO;
        } else if (token.getTipo() == TipoToken.OP_SOMA
                || token.getTipo() == TipoToken.OP_SUBTRACAO) {
            TipoVariavel tipoEsq = analisarRecursivo(no.getEsquerda());
            TipoVariavel tipoDir = analisarRecursivo(no.getDireita());

            if (tipoEsq != TipoVariavel.INTEIRO || tipoDir != TipoVariavel.INTEIRO) {
                throw new RuntimeException("Operação aritmética requer inteiros: " + token.getLexema()
                        + " na linha " + token.getLinha()
                        + ", coluna " + token.getColuna());
            }

            return TipoVariavel.INTEIRO;
        } else if (token.getTipo() == TipoToken.PARENTESE_ESQ
                || token.getTipo() == TipoToken.PARENTESE_DIR) {
            return analisarRecursivo(no.getEsquerda());
        }

        return TipoVariavel.DESCONHECIDO;
    }

    public void adicionarVariavel(String nome, TipoVariavel tipo) {
        if (!tabelaSimbolos.adicionarSimbolo(nome, tipo)) {
            throw new RuntimeException("Variável já declarada: " + nome);
        }
    }
    public void verificarDeclaracao(Token token){
        tabelaSimbolos.verificarDeclaracao(token);
    }
}
