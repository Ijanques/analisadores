/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.cefsa.analisadores.semantico;

/**
 *
 * @author evand
 */
import java.util.HashMap;
import java.util.Map;

import java.util.HashMap;
import java.util.Map;

public class Escopo {
    private Map<String, TipoVariavel> simbolos; // MAPA de símbolos
    private Escopo escopoPai;
    
    public Escopo(Escopo escopoPai) {
        this.simbolos = new HashMap<>(); // Inicializa o mapa aqui
        this.escopoPai = escopoPai;
    }
    
    public boolean adicionarSimbolo(String nome, TipoVariavel tipo) {
        if (simbolos.containsKey(nome)) {
            return false; // Já existe no escopo atual
        }
        simbolos.put(nome, tipo);
        return true;
    }
    
    public TipoVariavel buscarSimbolo(String nome) {
        // Busca no escopo atual
        TipoVariavel tipo = simbolos.get(nome);
        if (tipo != null) {
            return tipo;
        }
        // Se não encontrou, busca no escopo pai (se existir)
        if (escopoPai != null) {
            return escopoPai.buscarSimbolo(nome);
        }
        return null; // Não encontrado em nenhum escopo
    }
        public boolean existeSimbolo(String nome) {
        return simbolos.containsKey(nome);
    }
}
