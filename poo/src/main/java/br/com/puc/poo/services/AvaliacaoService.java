package br.com.puc.poo.services;

import br.com.puc.poo.entidades.Avaliacao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoService {
    private static final String ARQUIVO = "avaliacoes.dat";

    public void inserir(Avaliacao avaliacao) throws IOException, ClassNotFoundException {
        List<Avaliacao> avaliacoes = listar();

        // Gera ID automático se for zero
        if (avaliacao.getId() == 0) {
            int novoId = avaliacoes.stream()
                    .mapToInt(Avaliacao::getId)
                    .max()
                    .orElse(0) + 1;
            avaliacao.setId(novoId);
        }

        avaliacoes.add(avaliacao);
        salvarTodos(avaliacoes);
    }

    public List<Avaliacao> listar() throws IOException, ClassNotFoundException {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Avaliacao>) ois.readObject();
        } catch (EOFException e) {
            return new ArrayList<>();
        }
    }

    public void atualizar(Avaliacao avaliacaoAtualizado) throws IOException, ClassNotFoundException {
        List<Avaliacao> avaliacoes = listar();
        for (int i = 0; i < avaliacoes.size(); i++) {
            if (avaliacoes.get(i).getId() == avaliacaoAtualizado.getId()) {
                avaliacoes.set(i, avaliacaoAtualizado);
                break;
            }
        }
        salvarTodos(avaliacoes);
    }

    public void excluir(int id) throws IOException, ClassNotFoundException {
        List<Avaliacao> avaliacoes = listar();
        avaliacoes.removeIf(avaliacao -> avaliacao.getId() == id);
        salvarTodos(avaliacoes);
    }

    private void salvarTodos(List<Avaliacao> avaliacoes) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(avaliacoes);
        }
    }
}
