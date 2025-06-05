package br.com.puc.poo.entidades;

import java.io.Serializable;

public class Avaliacao implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int aderencia;
    private int qualidade;
    private int originalidade;
    private int nota;

    public Avaliacao() {
    }

    public Avaliacao(int id, int aderencia, int qualidade, int originalidade, int nota) {
        this.id = id;
        this.aderencia = aderencia;
        this.qualidade = qualidade;
        this.originalidade = originalidade;
        this.nota = nota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAderencia() {
        return aderencia;
    }

    public void setAderencia(int aderencia) {
        this.aderencia = aderencia;
    }

    public int getQualidade() {
        return qualidade;
    }

    public void setQualidade(int qualidade) {
        this.qualidade = qualidade;
    }

    public int getOriginalidade() {
        return originalidade;
    }

    public void setOriginalidade(int originalidade) {
        this.originalidade = originalidade;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public void consultarNota() {
        System.out.println("Nota da avaliação: " + nota);
    }

    public void excluir() {
        System.out.println("Avaliação excluída do sistema");
    }

    public void editarAderencia() {
        System.out.println("Aderência editada para: " + aderencia);
    }

    @Override
    public String toString() {
        return "Avaliação - Nota: " + nota + ", Aderência: " + aderencia + ", Qualidade: " + qualidade;
    }
}
