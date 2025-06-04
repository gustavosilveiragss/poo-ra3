package br.com.puc.poo.services;

import br.com.puc.poo.entidades.Minicurso;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MinicursoService {
    private static final String ARQUIVO = "minicursos.dat";

    public void inserir(Minicurso minicurso) throws IOException, ClassNotFoundException {
        List<Minicurso> minicursos = listar();
        System.out.println("Inserindo minicurso: " + minicurso.getTitulo());
        minicursos.add(minicurso);
        System.out.println("Minicurso inserido com sucesso.");
        salvarTodos(minicursos);
    }

    public List<Minicurso> listar() throws IOException, ClassNotFoundException {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Minicurso>) ois.readObject();
        } catch (EOFException e) {
            return new ArrayList<>();
        }
    }

    public void atualizar(Minicurso minicursoAtualizado) throws IOException, ClassNotFoundException {
        List<Minicurso> minicursos = listar();
        for (int i = 0; i < minicursos.size(); i++) {
            if (minicursos.get(i).getTitulo().equals(minicursoAtualizado.getTitulo())) {
                minicursos.set(i, minicursoAtualizado);
                break;
            }
        }
        salvarTodos(minicursos);
    }

    public void excluir(String titulo) throws IOException, ClassNotFoundException {
        List<Minicurso> minicursos = listar();
        minicursos.removeIf(minicurso -> minicurso.getTitulo().equals(titulo));
        salvarTodos(minicursos);
    }

    private void salvarTodos(List<Minicurso> minicursos) throws IOException {
        System.out.println("Salvando minicursos no arquivo: " + ARQUIVO);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            System.out.println("Número de minicursos a serem salvos: " + minicursos.size());
            oos.writeObject(minicursos);
        }
    }
}