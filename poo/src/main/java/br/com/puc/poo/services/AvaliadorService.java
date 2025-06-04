package br.com.puc.poo.services;

import br.com.puc.poo.entidades.Avaliador;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AvaliadorService {
    private static final String ARQUIVO = "avaliadores.dat";

    public void inserir(Avaliador avaliador) throws IOException, ClassNotFoundException {
        List<Avaliador> avaliadores = listar();
        avaliadores.add(avaliador);
        salvarTodos(avaliadores);
    }

    public List<Avaliador> listar() throws IOException, ClassNotFoundException {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Avaliador>) ois.readObject();
        } catch (EOFException e) {
            return new ArrayList<>();
        }
    }

    public void atualizar(Avaliador avaliadorAtualizado) throws IOException, ClassNotFoundException {
        List<Avaliador> avaliadores = listar();
        for (int i = 0; i < avaliadores.size(); i++) {
            if (avaliadores.get(i).getEmail().equals(avaliadorAtualizado.getEmail())) {
                avaliadores.set(i, avaliadorAtualizado);
                break;
            }
        }
        salvarTodos(avaliadores);
    }

    public void excluir(String email) throws IOException, ClassNotFoundException {
        List<Avaliador> avaliadores = listar();
        avaliadores.removeIf(avaliador -> avaliador.getEmail().equals(email));
        salvarTodos(avaliadores);
    }

    private void salvarTodos(List<Avaliador> avaliadores) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(avaliadores);
        }
    }
}
