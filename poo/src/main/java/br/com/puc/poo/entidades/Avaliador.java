package br.com.puc.poo.entidades;

import java.io.Serializable;
import java.time.LocalDate;

public class Avaliador implements Serializable {
    private static final long serialVersionUID = 1L;
    private String email;
    private String nome;
    private String celular;
    private LocalDate dataDeInscricao;

    public Avaliador() {
    }

    public Avaliador(String email, String nome, String celular, LocalDate dataDeInscricao) {
        this.email = email;
        this.nome = nome;
        this.celular = celular;
        this.dataDeInscricao = dataDeInscricao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
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

