package br.com.puc.poo.entidades;

public class Avaliacao {
    private int aderencia;
    private int qualidade;
    private int originalidade;
    private int nota;

    public Avaliacao() {
    }

    public Avaliacao(int aderencia, int qualidade, int originalidade, int nota) {
        this.aderencia = aderencia;
        this.qualidade = qualidade;
        this.originalidade = originalidade;
        this.nota = nota;
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
