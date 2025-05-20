/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.cefsa.analisadores.sintatico;

/**
 *
 * @author evand
 */
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import br.edu.cefsa.analisadores.lexico.*;

import java.util.Arrays;
import java.util.List;

class AnalisadorSintaticoTest {

    private List<Token> criarTokens(Token... tokens) {
        return Arrays.asList(tokens);
    }

    @Test
    void testExpressaoValidaSimples() {
        List<Token> tokens = criarTokens(
            new Token(TipoToken.IDENTIFICADOR, "x", 1, 1),
            new Token(TipoToken.OP_IGUAL, "==", 1, 3),
            new Token(TipoToken.NUMERO, "10", 1, 6),
            new Token(TipoToken.EOF, "EOF", 1, 8)
        );
        
        AnalisadorSintatico sintatico = new AnalisadorSintatico(tokens);
        assertDoesNotThrow(() -> sintatico.analisar());
    }

    @Test
    void testExpressaoComParenteses() {
        List<Token> tokens = criarTokens(
            new Token(TipoToken.PARENTESE_ESQ, "(", 1, 1),
            new Token(TipoToken.IDENTIFICADOR, "x", 1, 2),
            new Token(TipoToken.OP_SOMA, "+", 1, 4),
            new Token(TipoToken.IDENTIFICADOR, "y", 1, 6),
            new Token(TipoToken.PARENTESE_DIR, ")", 1, 7),
            new Token(TipoToken.OP_IGUAL, "==", 1, 9),
            new Token(TipoToken.NUMERO, "10", 1, 12),
            new Token(TipoToken.EOF, "EOF", 1, 14)
        );
        
        AnalisadorSintatico sintatico = new AnalisadorSintatico(tokens);
        assertDoesNotThrow(() -> sintatico.analisar());
    }

    @Test
    void testExpressaoInvalidaDoisTermosConsecutivos() {
        List<Token> tokens = criarTokens(
            new Token(TipoToken.NUMERO, "10", 1, 1),
            new Token(TipoToken.NUMERO, "10", 1, 4),
            new Token(TipoToken.OP_IGUAL, "==", 1, 7),
            new Token(TipoToken.EOF, "EOF", 1, 9)
        );
        
        AnalisadorSintatico sintatico = new AnalisadorSintatico(tokens);
        
        Exception exception = assertThrows(RuntimeException.class, () -> {
            sintatico.analisar();
        });
        
        assertTrue(exception.getMessage().contains("Operador esperado entre os termos"));
    }

    @Test
    void testExpressaoInvalidaFaltaOperando() {
        List<Token> tokens = criarTokens(
            new Token(TipoToken.IDENTIFICADOR, "x", 1, 1),
            new Token(TipoToken.OP_SOMA, "+", 1, 3),
            new Token(TipoToken.EOF, "EOF", 1, 4)
        );
        
        AnalisadorSintatico sintatico = new AnalisadorSintatico(tokens);
        
        Exception exception = assertThrows(RuntimeException.class, () -> {
            sintatico.analisar();
        });
        
        assertTrue(exception.getMessage().contains("Token inesperado"));
    }

    @Test
    void testExpressaoInvalidaParenteseNaoFechado() {
        List<Token> tokens = criarTokens(
            new Token(TipoToken.PARENTESE_ESQ, "(", 1, 1),
            new Token(TipoToken.IDENTIFICADOR, "x", 1, 2),
            new Token(TipoToken.OP_SOMA, "+", 1, 4),
            new Token(TipoToken.IDENTIFICADOR, "y", 1, 6),
            new Token(TipoToken.EOF, "EOF", 1, 7)
        );
        
        AnalisadorSintatico sintatico = new AnalisadorSintatico(tokens);
        
        Exception exception = assertThrows(RuntimeException.class, () -> {
            sintatico.analisar();
        });
        
        assertTrue(exception.getMessage().contains("Esperado ')'"));
    }
}