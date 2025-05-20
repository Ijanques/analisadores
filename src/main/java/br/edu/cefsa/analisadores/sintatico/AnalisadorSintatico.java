/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.cefsa.analisadores.sintatico;

/**
 *
 * @author evand
 */
import br.edu.cefsa.analisadores.lexico.Token;
import br.edu.cefsa.analisadores.lexico.TipoToken;
import java.util.List;
import java.util.Stack;

public class AnalisadorSintatico {

    private List<Token> tokens;
    private int posicaoAtual;
    private Stack<ArvoreSintatica> pilha;

    public AnalisadorSintatico(List<Token> tokens) {
        this.tokens = tokens;
        this.posicaoAtual = 0;
        this.pilha = new Stack<>();
    }

    public ArvoreSintatica analisar() {
        return E();
    }

    private ArvoreSintatica E() {
        ArvoreSintatica t = T();

        if (posicaoAtual < tokens.size()) {
            Token tokenAtual = tokens.get(posicaoAtual);

            // Verificar se o próximo token é um operador de comparação
            if (tokenAtual.getTipo() == TipoToken.OP_IGUAL
                    || tokenAtual.getTipo() == TipoToken.OP_DIFERENTE) {
                posicaoAtual++;
                ArvoreSintatica e = new ArvoreSintatica(tokenAtual);
                e.setEsquerda(t);
                e.setDireita(T());  // Deve consumir o próximo termo
                return e;
            }

            // Adicionar verificação para tokens inesperados
            if (tokenAtual.getTipo() == TipoToken.IDENTIFICADOR
                    || tokenAtual.getTipo() == TipoToken.NUMERO) {
                throw new RuntimeException("Operador esperado entre os termos na linha "
                        + tokenAtual.getLinha() + ", coluna "
                        + tokenAtual.getColuna());
            }
        }

        return t;
    }

    private ArvoreSintatica T() {
        ArvoreSintatica f = F();

        if (posicaoAtual < tokens.size()) {
            Token tokenAtual = tokens.get(posicaoAtual);

            if (tokenAtual.getTipo() == TipoToken.OP_SOMA
                    || tokenAtual.getTipo() == TipoToken.OP_SUBTRACAO) {
                posicaoAtual++;
                ArvoreSintatica t = new ArvoreSintatica(tokenAtual);
                t.setEsquerda(f);
                t.setDireita(F());
                return t;
            }
        }

        return f;
    }

    private ArvoreSintatica F() {
        Token tokenAtual = tokens.get(posicaoAtual);

        if (tokenAtual.getTipo() == TipoToken.PARENTESE_ESQ) {
            posicaoAtual++;
            ArvoreSintatica e = E();

            if (tokens.get(posicaoAtual).getTipo() != TipoToken.PARENTESE_DIR) {
                throw new RuntimeException("Esperado ')' na linha " + tokenAtual.getLinha()
                        + ", coluna " + tokenAtual.getColuna());
            }

            posicaoAtual++;
            return e;
        } else if (tokenAtual.getTipo() == TipoToken.IDENTIFICADOR
                || tokenAtual.getTipo() == TipoToken.NUMERO) {
            posicaoAtual++;
            return new ArvoreSintatica(tokenAtual);
        } else {
            throw new RuntimeException("Token inesperado: " + tokenAtual.getLexema()
                    + " na linha " + tokenAtual.getLinha()
                    + ", coluna " + tokenAtual.getColuna());
        }
    }
}
