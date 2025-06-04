package br.com.puc.poo.entidades;

public class Minicurso {
    private String titulo;
    private String curriculo;
    private int duracao;
    private String justificativa;
    private String material;
    private String objetivo;
    private String publicoAlvo;

    public Minicurso() {
    }

    public Minicurso(String titulo, String curriculo, int duracao, String justificativa, String material, String objetivo, String publicoAlvo) {
        this.titulo = titulo;
        this.curriculo = curriculo;
        this.duracao = duracao;
        this.justificativa = justificativa;
        this.material = material;
        this.objetivo = objetivo;
        this.publicoAlvo = publicoAlvo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCurriculo() {
        return curriculo;
    }

    public void setCurriculo(String curriculo) {
        this.curriculo = curriculo;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getPublicoAlvo() {
        return publicoAlvo;
    }

    public void setPublicoAlvo(String publicoAlvo) {
        this.publicoAlvo = publicoAlvo;
    }

    public void consultarCurriculo() {
        System.out.println("Currículo do minicurso: " + curriculo);
    }

    public void consultarDuracao() {
        System.out.println("Duração do minicurso: " + duracao + " horas");
    }

    public void excluir() {
        System.out.println("Minicurso " + titulo + " excluído do sistema");
    }

    public void editarMaterial() {
        System.out.println("Material do minicurso editado: " + material);
    }

    @Override
    public String toString() {
        return titulo + " - Duração: " + duracao + "h";
    }
}