package br.com.puc.poo.entidades;

import java.time.LocalDate;

public class Avaliador {
    private int email;
    private int nome;
    private int celular;
    private LocalDate dataDeInscricao;

    public Avaliador() {
    }

    public Avaliador(int email, int nome, int celular, LocalDate dataDeInscricao) {
        this.email = email;
        this.nome = nome;
        this.celular = celular;
        this.dataDeInscricao = dataDeInscricao;
    }

    public int getEmail() {
        return email;
    }

    public void setEmail(int email) {
        this.email = email;
    }

    public int getNome() {
        return nome;
    }

    public void setNome(int nome) {
        this.nome = nome;
    }

    public int getCelular() {
        return celular;
    }

    public void setCelular(int celular) {
        this.celular = celular;
    }

    public LocalDate getDataDeInscricao() {
        return dataDeInscricao;
    }

    public void setDataDeInscricao(LocalDate dataDeInscricao) {
        this.dataDeInscricao = dataDeInscricao;
    }

    public void consultarNome() {
        System.out.println("Nome do avaliador: " + nome);
    }

    public void consultarEmail() {
        System.out.println("Email do avaliador: " + email);
    }

    public void consultarCelular() {
        System.out.println("Celular do avaliador: " + celular);
    }

    public void editarCelular() {
        System.out.println("Celular editado para: " + celular);
    }

    public void excluir() {
        System.out.println("Avaliador " + nome + " excluído do sistema");
    }

    @Override
    public String toString() {
        return "Avaliador: " + nome + " - Email: " + email;
    }
}
