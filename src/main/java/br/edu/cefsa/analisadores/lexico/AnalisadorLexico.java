/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.cefsa.analisadores.lexico;

/**
 *
 * @author evand
 */
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalisadorLexico {

    private String input;
    private int posicaoAtual;
    private int linhaAtual;
    private int colunaAtual;

    private static final Pattern PATTERN_IDENTIFICADOR = Pattern.compile("^[a-zA-Z_][a-zA-Z0-9_]*");
    private static final Pattern PATTERN_NUMERO = Pattern.compile("^\\d+");
    private static final Pattern PATTERN_ESPACO = Pattern.compile("^\\s+");

    public AnalisadorLexico(String input) {
        this.input = input;
        this.posicaoAtual = 0;
        this.linhaAtual = 1;
        this.colunaAtual = 1;
    }

    public List<Token> analisar() {
        List<Token> tokens = new ArrayList<>();

        while (posicaoAtual < input.length()) {
            char caractereAtual = input.charAt(posicaoAtual);

            // Ignorar espaços em branco
            if (Character.isWhitespace(caractereAtual)) {
                if (caractereAtual == '\n') {
                    linhaAtual++;
                    colunaAtual = 1;
                } else {
                    colunaAtual++;
                }
                posicaoAtual++;
                continue;
            }

            // Verificar tokens
            Token token = null;

            // Identificadores
            Matcher matcherIdentificador = PATTERN_IDENTIFICADOR.matcher(input.substring(posicaoAtual));
            if (matcherIdentificador.find()) {
                String lexema = matcherIdentificador.group();
                token = new Token(TipoToken.IDENTIFICADOR, lexema, linhaAtual, colunaAtual);
                colunaAtual += lexema.length();
                posicaoAtual += lexema.length();
                tokens.add(token);
                continue;
            }

            // Números
            Matcher matcherNumero = PATTERN_NUMERO.matcher(input.substring(posicaoAtual));
            if (matcherNumero.find()) {
                String lexema = matcherNumero.group();
                token = new Token(TipoToken.NUMERO, lexema, linhaAtual, colunaAtual);
                colunaAtual += lexema.length();
                posicaoAtual += lexema.length();
                tokens.add(token);
                continue;
            }

            // Operadores e símbolos
            switch (caractereAtual) {
                case '=':
                    if (posicaoAtual + 1 < input.length() && input.charAt(posicaoAtual + 1) == '=') {
                        token = new Token(TipoToken.OP_IGUAL, "==", linhaAtual, colunaAtual);
                        posicaoAtual += 2;
                        colunaAtual += 2;
                    } else {
                        token = new Token(TipoToken.ERRO, "=", linhaAtual, colunaAtual);
                        posicaoAtual++;
                        colunaAtual++;
                    }
                    break;
                case '!':
                    if (posicaoAtual + 1 < input.length() && input.charAt(posicaoAtual + 1) == '=') {
                        token = new Token(TipoToken.OP_DIFERENTE, "!=", linhaAtual, colunaAtual);
                        posicaoAtual += 2;
                        colunaAtual += 2;
                    } else {
                        token = new Token(TipoToken.ERRO, "!", linhaAtual, colunaAtual);
                        posicaoAtual++;
                        colunaAtual++;
                    }
                    break;
                case '+':
                    token = new Token(TipoToken.OP_SOMA, "+", linhaAtual, colunaAtual);
                    posicaoAtual++;
                    colunaAtual++;
                    break;
                case '-':
                    token = new Token(TipoToken.OP_SUBTRACAO, "-", linhaAtual, colunaAtual);
                    posicaoAtual++;
                    colunaAtual++;
                    break;
                case '(':
                    token = new Token(TipoToken.PARENTESE_ESQ, "(", linhaAtual, colunaAtual);
                    posicaoAtual++;
                    colunaAtual++;
                    break;
                case ')':
                    token = new Token(TipoToken.PARENTESE_DIR, ")", linhaAtual, colunaAtual);
                    posicaoAtual++;
                    colunaAtual++;
                    break;
                default:
                    token = new Token(TipoToken.ERRO, String.valueOf(caractereAtual), linhaAtual, colunaAtual);
                    posicaoAtual++;
                    colunaAtual++;
                    break;
            }

            tokens.add(token);
        }

        tokens.add(new Token(TipoToken.EOF, "EOF", linhaAtual, colunaAtual));
        return tokens;
    }
}
