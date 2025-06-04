package br.com.puc.poo.controllers;

import br.com.puc.poo.entidades.Minicurso;
import br.com.puc.poo.services.MinicursoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Pos;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MinicursoController {
    private Stage stage;
    private final Stage stageOwner;
    private final MinicursoService service;
    private final ObservableList<Minicurso> minicursos;
    private ListView<Minicurso> listView;

    public MinicursoController(Stage stageOwner) {
        this.stageOwner = stageOwner;
        this.service = new MinicursoService();
        this.minicursos = FXCollections.observableArrayList();
        carregarMinicursos();
    }

    public void mostrar() {
        criarUI();
        stage.showAndWait();
    }

    private void criarUI() {
        stage = new Stage();
        stage.setTitle("Gerenciamento de Minicursos");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(stageOwner);

        listView = new ListView<>(minicursos);
        listView.setPrefHeight(300);

        Button btnInserir = new Button("Inserir");
        btnInserir.setOnAction(e -> inserirMinicurso());

        Button btnConsultar = new Button("Consultar");
        btnConsultar.setOnAction(e -> consultarMinicurso());

        Button btnAtualizar = new Button("Atualizar");
        btnAtualizar.setOnAction(e -> atualizarMinicurso());

        Button btnExcluir = new Button("Excluir");
        btnExcluir.setOnAction(e -> excluirMinicurso());

        Button btnFechar = new Button("Fechar");
        btnFechar.setOnAction(e -> stage.close());

        HBox botoes = new HBox(10, btnInserir, btnConsultar, btnAtualizar, btnExcluir, btnFechar);
        botoes.setAlignment(Pos.CENTER);

        btnInserir.setStyle("-fx-background-color: #ffb6c1; -fx-text-fill: white;");
        btnConsultar.setStyle("-fx-background-color: #ff69b4; -fx-text-fill: white;");
        btnAtualizar.setStyle("-fx-background-color: #ff1493; -fx-text-fill: white;");
        btnExcluir.setStyle("-fx-background-color: #c71585; -fx-text-fill: white;");
        btnFechar.setStyle("-fx-background-color: #db7093; -fx-text-fill: white;");

        VBox layout = new VBox(15, new Label("Bem-vindo a página de minicursos! Aqui você pode gerenciar todos os minicursos:"), listView, botoes);
        layout.setStyle("-fx-padding: 20;");

        stage.setScene(new Scene(layout, 600, 500));
    }

    private void carregarMinicursos() {
        try {
            List<Minicurso> lista = service.listar();
            minicursos.setAll(lista);
        } catch (IOException | ClassNotFoundException e) {
            mostrarAlerta("Erro", "Erro ao carregar minicursos: " + e.getMessage());
        }
    }

    private void inserirMinicurso() {
        Minicurso novoMinicurso = mostrarFormulario("Inserir Minicurso", null);
        if (novoMinicurso != null) {
            try {
                service.inserir(novoMinicurso);
                minicursos.add(novoMinicurso);
                mostrarAlerta("Sucesso", "Minicurso inserido com sucesso!");
            } catch (IOException | ClassNotFoundException e) {
                mostrarAlerta("Erro", "Erro ao inserir: " + e.getMessage());
            }
        }
    }

    private void consultarMinicurso() {
        Minicurso selecionado = getMinicursoSelecionado();
        if (selecionado == null) return;

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Consultar Minicurso");
        alert.setHeaderText("Dados do Minicurso");
        alert.setContentText(
                "Título: " + selecionado.getTitulo() + "\n" +
                "Currículo: " + selecionado.getCurriculo() + "\n" +
                "Duração: " + selecionado.getDuracao() + " horas\n" +
                "Justificativa: " + selecionado.getJustificativa() + "\n" +
                "Material: " + selecionado.getMaterial() + "\n" +
                "Objetivo: " + selecionado.getObjetivo() + "\n" +
                "Público Alvo: " + selecionado.getPublicoAlvo()
        );
        alert.showAndWait();
    }

    private void atualizarMinicurso() {
        Minicurso selecionado = getMinicursoSelecionado();
        if (selecionado == null) return;

        Minicurso atualizado = mostrarFormulario("Atualizar Minicurso", selecionado);
        if (atualizado != null) {
            try {
                service.atualizar(atualizado);
                int index = minicursos.indexOf(selecionado);
                minicursos.set(index, atualizado);
                mostrarAlerta("Sucesso", "Minicurso atualizado com sucesso!");
            } catch (IOException | ClassNotFoundException e) {
                mostrarAlerta("Erro", "Erro ao atualizar: " + e.getMessage());
            }
        }
    }

    private void excluirMinicurso() {
        Minicurso selecionado = getMinicursoSelecionado();
        if (selecionado == null) return;

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Exclusão");
        confirmacao.setContentText("Deseja realmente excluir o minicurso \"" + selecionado.getTitulo() + "\"?");

        if (confirmacao.showAndWait().get() == ButtonType.OK) {
            try {
                service.excluir(selecionado.getTitulo());
                minicursos.remove(selecionado);
                mostrarAlerta("Sucesso", "Minicurso excluído com sucesso!");
            } catch (IOException | ClassNotFoundException e) {
                mostrarAlerta("Erro", "Erro ao excluir: " + e.getMessage());
            }
        }
    }

    private Minicurso mostrarFormulario(String titulo, Minicurso minicurso) {
        Stage formStage = new Stage();
        formStage.setTitle(titulo);
        formStage.initModality(Modality.APPLICATION_MODAL);
        formStage.initOwner(stage);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setStyle("-fx-padding: 20;");

        TextField txtTitulo = new TextField();
        TextField txtCurriculo = new TextField();
        TextField txtDuracao = new TextField();
        TextArea txtJustificativa = new TextArea();
        TextArea txtMaterial = new TextArea();
        TextArea txtObjetivo = new TextArea();
        TextField txtPublicoAlvo = new TextField();

        txtJustificativa.setPrefRowCount(3);
        txtMaterial.setPrefRowCount(3);
        txtObjetivo.setPrefRowCount(3);
        txtJustificativa.setWrapText(true);
        txtMaterial.setWrapText(true);
        txtObjetivo.setWrapText(true);

        if (minicurso != null) {
            txtTitulo.setText(minicurso.getTitulo());
            txtCurriculo.setText(minicurso.getCurriculo());
            txtDuracao.setText(String.valueOf(minicurso.getDuracao()));
            txtJustificativa.setText(minicurso.getJustificativa());
            txtMaterial.setText(minicurso.getMaterial());
            txtObjetivo.setText(minicurso.getObjetivo());
            txtPublicoAlvo.setText(minicurso.getPublicoAlvo());
        }

        grid.addRow(0, new Label("Título:"), txtTitulo);
        grid.addRow(1, new Label("Currículo:"), txtCurriculo);
        grid.addRow(2, new Label("Duração (horas):"), txtDuracao);
        grid.addRow(3, new Label("Justificativa:"), txtJustificativa);
        grid.addRow(4, new Label("Material:"), txtMaterial);
        grid.addRow(5, new Label("Objetivo:"), txtObjetivo);
        grid.addRow(6, new Label("Público Alvo:"), txtPublicoAlvo);

        Button btnSalvar = new Button("Salvar");
        Button btnCancelar = new Button("Cancelar");

        grid.setStyle("-fx-padding: 20; -fx-background-color: #fff0f5;");

        btnSalvar.setStyle("-fx-background-color: #ff69b4; -fx-text-fill: white;");
        btnCancelar.setStyle("-fx-background-color: #db7093; -fx-text-fill: white;");

        // Para uso em lambda, precisa ser efetivamente final
        final Minicurso[] resultado = {null};

        btnSalvar.setOnAction(e -> {
            String tituloStr = txtTitulo.getText().trim();
            String curriculo = txtCurriculo.getText().trim();
            String justificativa = txtJustificativa.getText().trim();
            String material = txtMaterial.getText().trim();
            String objetivo = txtObjetivo.getText().trim();
            String publicoAlvo = txtPublicoAlvo.getText().trim();
            String duracaoStr = txtDuracao.getText().trim();

            if (tituloStr.isEmpty() || curriculo.isEmpty() || duracaoStr.isEmpty() ||
                    justificativa.isEmpty() || material.isEmpty() || objetivo.isEmpty() || publicoAlvo.isEmpty()) {
                mostrarAlerta("Erro", "Todos os campos são obrigatórios.");
                return;
            }

            try {
                int duracao = Integer.parseInt(duracaoStr);
                if (duracao <= 0) {
                    mostrarAlerta("Erro", "Duração deve ser um número positivo.");
                    return;
                }

                resultado[0] = new Minicurso(tituloStr, curriculo, duracao, justificativa, material, objetivo, publicoAlvo);
                formStage.close();
            } catch (NumberFormatException ex) {
                mostrarAlerta("Erro", "Duração deve ser um número válido.");
            }
        });

        btnCancelar.setOnAction(e -> formStage.close());

        HBox botoes = new HBox(10, btnSalvar, btnCancelar);
        botoes.setAlignment(Pos.CENTER);

        VBox conteudo = new VBox(15, grid, botoes);
        conteudo.setAlignment(Pos.CENTER);
        conteudo.setStyle("-fx-padding: 20;");

        BorderPane root = new BorderPane();
        root.setCenter(conteudo);

        formStage.setScene(new Scene(root, 800, 600));
        formStage.showAndWait();

        return resultado[0];
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private Minicurso getMinicursoSelecionado() {
        Minicurso selecionado = listView.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta("Aviso", "Selecione um minicurso.");
        }
        return selecionado;
    }
}