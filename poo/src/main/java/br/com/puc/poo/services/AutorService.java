package br.com.puc.poo.services;

import br.com.puc.poo.entidades.Autor;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AutorService {
    private static final String ARQUIVO = "autores.dat";

    public void inserir(Autor autor) throws IOException, ClassNotFoundException {
        List<Autor> autores = listar();
        autores.add(autor);
        salvarTodos(autores);
    }

    public List<Autor> listar() throws IOException, ClassNotFoundException {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Autor>) ois.readObject();
        } catch (EOFException e) {
            return new ArrayList<>();
        }
    }

    public void atualizar(Autor autorAtualizado) throws IOException, ClassNotFoundException {
        List<Autor> autores = listar();
        for (int i = 0; i < autores.size(); i++) {
            if (autores.get(i).getEmail().equals(autorAtualizado.getEmail())) {
                autores.set(i, autorAtualizado);
                break;
            }
        }
        salvarTodos(autores);
    }

    public void excluir(String email) throws IOException, ClassNotFoundException {
        List<Autor> autores = listar();
        autores.removeIf(autor -> autor.getEmail().equals(email));
        salvarTodos(autores);
    }

    private void salvarTodos(List<Autor> autores) throws IOException {
        List<Autor> listaLimpa = autores.stream()
                .map(a -> new Autor(a.getNome(), a.getDataDeNascimento(), a.getFormacao(), a.getEmail()))
                .collect(Collectors.toList());

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(listaLimpa);
        }
    }
}
