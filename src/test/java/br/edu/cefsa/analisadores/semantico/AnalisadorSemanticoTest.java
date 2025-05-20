/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.cefsa.analisadores.semantico;

/**
 *
 * @author evand
 */
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import br.edu.cefsa.analisadores.lexico.*;
import br.edu.cefsa.analisadores.sintatico.*;
import org.junit.jupiter.api.BeforeEach;

class AnalisadorSemanticoTest {

    @BeforeEach
    void setUp() {
        AnalisadorSemantico semantico = new AnalisadorSemantico();
        // Pré-declara a variável 'x' antes de cada teste
        semantico.adicionarVariavel("x", TipoVariavel.INTEIRO);
    }

    @Test
    void testVariavelDeclarada() {
        AnalisadorSemantico semantico = new AnalisadorSemantico();
        TabelaSimbolos simbolos = new TabelaSimbolos();
        assertDoesNotThrow(() -> {
            semantico.adicionarVariavel("x", TipoVariavel.INTEIRO);
            semantico.verificarDeclaracao(
                    new Token(TipoToken.IDENTIFICADOR, "x", 1, 1));
        });
    }

    @Test
    void testVariavelNaoDeclarada() {
        AnalisadorSemantico semantico = new AnalisadorSemantico();
        TabelaSimbolos simbolos = new TabelaSimbolos();
        Exception exception = assertThrows(RuntimeException.class, () -> {
            simbolos.verificarDeclaracao(
                    new Token(TipoToken.IDENTIFICADOR, "x", 1, 1));
        });

        assertTrue(exception.getMessage().contains("Variável não declarada"));
    }

    @Test
    void testTiposCompativeis() {
        // Árvore para: x + y (ambos inteiros)
        Token x = new Token(TipoToken.IDENTIFICADOR, "x", 1, 1);
        Token mais = new Token(TipoToken.OP_SOMA, "+", 1, 3);
        Token y = new Token(TipoToken.IDENTIFICADOR, "y", 1, 5);

        ArvoreSintatica arvore = new ArvoreSintatica(mais);
        arvore.setEsquerda(new ArvoreSintatica(x));
        arvore.setDireita(new ArvoreSintatica(y));

        AnalisadorSemantico semantico = new AnalisadorSemantico();
        semantico.adicionarVariavel("x", TipoVariavel.INTEIRO);
        semantico.adicionarVariavel("y", TipoVariavel.INTEIRO);

        assertDoesNotThrow(() -> semantico.analisar(arvore));
    }

    @Test
    void testTiposIncompativeis() {
        // Árvore para: x == y (x inteiro, y booleano)
        Token x = new Token(TipoToken.IDENTIFICADOR, "x", 1, 1);
        Token igual = new Token(TipoToken.OP_IGUAL, "==", 1, 3);
        Token y = new Token(TipoToken.IDENTIFICADOR, "y", 1, 6);

        ArvoreSintatica arvore = new ArvoreSintatica(igual);
        arvore.setEsquerda(new ArvoreSintatica(x));
        arvore.setDireita(new ArvoreSintatica(y));

        AnalisadorSemantico semantico = new AnalisadorSemantico();
        semantico.adicionarVariavel("x", TipoVariavel.INTEIRO);
        semantico.adicionarVariavel("y", TipoVariavel.BOOLEANO);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            semantico.analisar(arvore);
        });

        assertTrue(exception.getMessage().contains("Tipos incompatíveis"));
    }

    @Test
    void testOperacaoAritmeticaComBoolean() {
        // Árvore para: x + y (x inteiro, y booleano)
        Token x = new Token(TipoToken.IDENTIFICADOR, "x", 1, 1);
        Token mais = new Token(TipoToken.OP_SOMA, "+", 1, 3);
        Token y = new Token(TipoToken.IDENTIFICADOR, "y", 1, 5);

        ArvoreSintatica arvore = new ArvoreSintatica(mais);
        arvore.setEsquerda(new ArvoreSintatica(x));
        arvore.setDireita(new ArvoreSintatica(y));

        AnalisadorSemantico semantico = new AnalisadorSemantico();
        semantico.adicionarVariavel("x", TipoVariavel.INTEIRO);
        semantico.adicionarVariavel("y", TipoVariavel.BOOLEANO);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            semantico.analisar(arvore);
        });

        assertTrue(exception.getMessage().contains("Operação aritmética requer inteiros"));
    }
}
