/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.cefsa.analisadores.lexico;

/**
 *
 * @author evand
 */
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class AnalisadorLexicoTest {

    @Test
    void testAnalisarIdentificador() {
        AnalisadorLexico lexico = new AnalisadorLexico("abc");
        List<Token> tokens = lexico.analisar();

        assertEquals(2, tokens.size());
        assertEquals(TipoToken.IDENTIFICADOR, tokens.get(0).getTipo());
        assertEquals("abc", tokens.get(0).getLexema());
        assertEquals(1, tokens.get(0).getLinha());
        assertEquals(1, tokens.get(0).getColuna());
    }

    @Test
    void testAnalisarNumeroInteiro() {
        AnalisadorLexico lexico = new AnalisadorLexico("123");
        List<Token> tokens = lexico.analisar();

        assertEquals(2, tokens.size());
        assertEquals(TipoToken.NUMERO, tokens.get(0).getTipo());
        assertEquals("123", tokens.get(0).getLexema());
    }

    @Test
    void testAnalisarOperadorIgual() {
        AnalisadorLexico lexico = new AnalisadorLexico("==");
        List<Token> tokens = lexico.analisar();

        assertEquals(2, tokens.size());
        assertEquals(TipoToken.OP_IGUAL, tokens.get(0).getTipo());
        assertEquals("==", tokens.get(0).getLexema());
    }

    @Test
    void testAnalisarOperadorDiferente() {
        AnalisadorLexico lexico = new AnalisadorLexico("!=");
        List<Token> tokens = lexico.analisar();

        assertEquals(2, tokens.size());
        assertEquals(TipoToken.OP_DIFERENTE, tokens.get(0).getTipo());
        assertEquals("!=", tokens.get(0).getLexema());
    }

    @Test
    void testAnalisarExpressaoCompleta() {
        AnalisadorLexico lexico = new AnalisadorLexico("x + y == 10");
        List<Token> tokens = lexico.analisar();

        assertEquals(6, tokens.size());
        assertEquals(TipoToken.IDENTIFICADOR, tokens.get(0).getTipo());
        assertEquals(TipoToken.OP_SOMA, tokens.get(1).getTipo());
        assertEquals(TipoToken.IDENTIFICADOR, tokens.get(2).getTipo());
        assertEquals(TipoToken.OP_IGUAL, tokens.get(3).getTipo());
        assertEquals(TipoToken.NUMERO, tokens.get(4).getTipo());
    }

    @Test
    void testAnalisaParameters() {
        AnalisadorLexico lexico = new AnalisadorLexico("(x)");
        List<Token> tokens = lexico.analisar();

        assertEquals(4, tokens.size()); // Incluindo EOF
        assertEquals(TipoToken.PARENTESE_ESQ, tokens.get(0).getTipo());
        assertEquals(TipoToken.IDENTIFICADOR, tokens.get(1).getTipo());
        assertEquals(TipoToken.PARENTESE_DIR, tokens.get(2).getTipo());
        assertEquals(TipoToken.EOF, tokens.get(3).getTipo());
    }
}
