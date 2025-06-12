package br.com.puc.poo.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class Autor implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nome;
    private LocalDate dataDeNascimento;
    private String formacao;
    private String email;


    public Autor() {
    }

    public Autor(String nome, LocalDate dataDeNascimento, String formacao, String email) {
        this.nome = nome;
        this.dataDeNascimento = dataDeNascimento;
        this.formacao = formacao;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public String getFormacao() {
        return formacao;
    }

    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void consultarFormacao() {
        System.out.println("Formação do autor " + nome + ": " + formacao);
    }

    public void consultarNome() {
        System.out.println("Nome do autor: " + nome);
    }

    public void editarEmail() {
        System.out.println("Email editado para: " + email);
    }

    public void excluir() {
        System.out.println("Autor " + nome + " excluído do sistema");
    }

    @Override
    public String toString() {
        return nome + " - " + email + " - " + formacao;
    }
}