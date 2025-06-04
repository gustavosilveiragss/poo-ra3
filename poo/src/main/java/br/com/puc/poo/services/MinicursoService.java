package br.com.puc.poo.services;

import br.com.puc.poo.entidades.Minicurso;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MinicursoService {
    private static final String ARQUIVO = "minicursos.txt";

    public void inserir(Minicurso minicurso) throws IOException {
        List<Minicurso> minicursos = listar();
        minicursos.add(minicurso);
        salvarTodos(minicursos);
    }

    public List<Minicurso> listar() throws IOException {
        List<Minicurso> minicursos = new ArrayList<>();
        File arquivo = new File(ARQUIVO);

        if (!arquivo.exists()) {
            return minicursos;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 7) {
                    Minicurso minicurso = new Minicurso();
                    minicurso.setTitulo(dados[0]);
                    minicurso.setCurriculo(dados[1]);
                    minicurso.setDuracao(Integer.parseInt(dados[2]));
                    minicurso.setJustificativa(dados[3]);
                    minicurso.setMaterial(dados[4]);
                    minicurso.setObjetivo(dados[5]);
                    minicurso.setPublicoAlvo(dados[6]);
                    minicursos.add(minicurso);
                }
            }
        }
        return minicursos;
    }

    public void atualizar(Minicurso minicursoAtualizado) throws IOException {
        List<Minicurso> minicursos = listar();
        for (int i = 0; i < minicursos.size(); i++) {
            if (minicursos.get(i).getTitulo().equals(minicursoAtualizado.getTitulo())) {
                minicursos.set(i, minicursoAtualizado);
                break;
            }
        }
        salvarTodos(minicursos);
    }

    public void excluir(String titulo) throws IOException {
        List<Minicurso> minicursos = listar();
        minicursos.removeIf(minicurso -> minicurso.getTitulo().equals(titulo));
        salvarTodos(minicursos);
    }

    private void salvarTodos(List<Minicurso> minicursos) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO))) {
            for (Minicurso minicurso : minicursos) {
                writer.println(minicurso.getTitulo() + ";" +
                minicurso.getCurriculo() + ";" +
                minicurso.getDuracao() + ";" +
                minicurso.getJustificativa() + ";" +
                minicurso.getMaterial() + ";" +
                minicurso.getObjetivo() + ";" +
                minicurso.getPublicoAlvo());
            }
        }
    }
}