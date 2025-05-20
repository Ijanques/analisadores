/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.cefsa.analisadores.lexico;

/**
 *
 * @author evand
 */
public enum TipoToken {
    IDENTIFICADOR, NUMERO, 
    OP_IGUAL, OP_DIFERENTE, OP_SOMA, OP_SUBTRACAO,
    PARENTESE_ESQ, PARENTESE_DIR,
    EOF, ERRO
}
