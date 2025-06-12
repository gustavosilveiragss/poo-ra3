package br.com.puc.poo.services;

import br.com.puc.poo.entidades.Avaliador;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AvaliadorService {
    private static final String ARQUIVO = "avaliadores.dat";

    public void inserir(Avaliador avaliador) throws IOException, ClassNotFoundException {
        List<Avaliador> avaliadores = listar();
        for (Avaliador a: avaliadores){
            if(a.getEmail().equalsIgnoreCase(avaliador.getEmail())){
                throw new IOException("Ja existe um avaliador com este e-mail.");
            }
            if(a.getCelular().equals(avaliador.getCelular())){
                throw new IOException("Ja existe um avaliador com este numero de celular");
            }
        }
        avaliadores.add(avaliador);
        salvarAvaliadores(avaliadores);
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

    public void atualizar(Avaliador original, Avaliador atualizado) throws IOException, ClassNotFoundException {
        List<Avaliador> avaliadores = listar();

        for (Avaliador a : avaliadores) {
            if (!a.getEmail().equalsIgnoreCase(original.getEmail())) {
                if (a.getEmail().equalsIgnoreCase(atualizado.getEmail())) {
                    throw new IOException("Já existe um avaliador com este e-mail.");
                }
                if (a.getCelular().equals(atualizado.getCelular())) {
                    throw new IOException("Já existe um avaliador com este número de celular");
                }
            }
        }

        for (int i = 0; i < avaliadores.size(); i++) {
            if (avaliadores.get(i).getEmail().equalsIgnoreCase(original.getEmail())) {
                avaliadores.set(i, atualizado);
                break;
            }
        }

        salvarAvaliadores(avaliadores);
    }


    public void excluir(String email) throws IOException, ClassNotFoundException {
        List<Avaliador> avaliadores = listar();
        avaliadores.removeIf(avaliador -> avaliador.getEmail().equals(email));
        salvarAvaliadores(avaliadores);
    }

    private void salvarAvaliadores(List<Avaliador> avaliadores) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(avaliadores);
        }
    }
}
