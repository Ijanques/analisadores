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

public class ArvoreSintatica {
    private Token token;
    private ArvoreSintatica esquerda;
    private ArvoreSintatica direita;
    
    public ArvoreSintatica(Token token) {
        this.token = token;
    }
    
    public ArvoreSintatica(Token token, ArvoreSintatica esquerda, ArvoreSintatica direita) {
        this.token = token;
        this.esquerda = esquerda;
        this.direita = direita;
    }
    
    // Getters e Setters
    public Token getToken() { return token; }
    public ArvoreSintatica getEsquerda() { return esquerda; }
    public ArvoreSintatica getDireita() { return direita; }
    
    public void setEsquerda(ArvoreSintatica esquerda) { this.esquerda = esquerda; }
    public void setDireita(ArvoreSintatica direita) { this.direita = direita; }
    
    @Override
    public String toString() {
        return toString(0);
    }
    
    private String toString(int nivel) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nivel; i++) {
            sb.append("  ");
        }
        sb.append(token.getLexema()).append("\n");
        
        if (esquerda != null) {
            sb.append(esquerda.toString(nivel + 1));
        }
        if (direita != null) {
            sb.append(direita.toString(nivel + 1));
        }
        
        return sb.toString();
    }
}
