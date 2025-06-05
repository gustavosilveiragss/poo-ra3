package br.com.puc.poo.controllers;

import br.com.puc.poo.entidades.Avaliacao;
import br.com.puc.poo.entidades.Avaliador;
import br.com.puc.poo.services.AvaliacaoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AvaliacaoController {
    private Stage stage;
    private final Stage stageOwner;
    private final AvaliacaoService service;
    private final ObservableList<Avaliacao> avaliacoes;
    private ListView<Avaliacao> listView;

    public AvaliacaoController(Stage stageOwner) {
        this.stageOwner = stageOwner;
        this.service = new AvaliacaoService();
        this.avaliacoes = FXCollections.observableArrayList();
        carregarAvaliacoes();
    }

    public void mostrar() {
        criarUI();
        stage.showAndWait();
    }

    private void criarUI() {
        stage = new Stage();
        stage.setTitle("Gerenciamento de Avaliações");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(stageOwner);

        listView = new ListView<>(avaliacoes);
        listView.setPrefHeight(300);

        Button btnInserir = new Button("Inserir");
        btnInserir.setOnAction(e -> inserirAvaliacao());

        Button btnConsultar = new Button("Consultar");
        btnConsultar.setOnAction(e -> consultarAvaliacao());

        Button btnAtualizar = new Button("Atualizar");
        btnAtualizar.setOnAction(e -> atualizarAvaliacao());

        Button btnExcluir = new Button("Excluir");
        btnExcluir.setOnAction(e -> excluirAvaliacao());

        Button btnFechar = new Button("Fechar");
        btnFechar.setOnAction(e -> stage.close());

        HBox botoes = new HBox(10, btnInserir, btnConsultar, btnAtualizar, btnExcluir, btnFechar);
        botoes.setAlignment(Pos.CENTER);

        // Estilos opcionais (como no AvaliadorController)
        btnInserir.setStyle("-fx-background-color: #ffb6c1; -fx-text-fill: white;");
        btnConsultar.setStyle("-fx-background-color: #ff69b4; -fx-text-fill: white;");
        btnAtualizar.setStyle("-fx-background-color: #ff1493; -fx-text-fill: white;");
        btnExcluir.setStyle("-fx-background-color: #c71585; -fx-text-fill: white;");
        btnFechar.setStyle("-fx-background-color: #db7093; -fx-text-fill: white;");

        VBox layout = new VBox(15, new Label("Bem-vindo à página de avaliações! Aqui você pode gerenciar todas as avaliações:"),
                listView, botoes);
        layout.setStyle("-fx-padding: 20;");

        stage.setScene(new Scene(layout, 600, 500));
    }

    private void carregarAvaliacoes() {
        try {
            List<Avaliacao> lista = service.listar();
            avaliacoes.setAll(lista);
        } catch (IOException | ClassNotFoundException e) {
            mostrarAlerta("Erro", "Erro ao carregar avaliações: " + e.getMessage());
        }
    }

    private void inserirAvaliacao() {
        Avaliacao nova = mostrarFormulario(null);
        if (nova != null) {
            try {
                service.inserir(nova);
                avaliacoes.add(nova);
                mostrarAlerta("Sucesso", "Avaliação inserida com sucesso!");
            } catch (IOException | ClassNotFoundException e) {
                mostrarAlerta("Erro", "Erro ao inserir: " + e.getMessage());
            }
        }
    }

    private void consultarAvaliacao() {
        Avaliacao selecionada = getAvaliacaoSelecionada();
        if (selecionada == null) return;

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Consultar Avaliação");
        alert.setHeaderText("Dados da Avaliação");
        alert.setContentText(
                "ID: " + selecionada.getId() + "\n" +
                        "Aderência: " + selecionada.getAderencia() + "\n" +
                        "Qualidade: " + selecionada.getQualidade() + "\n" +
                        "Originalidade: " + selecionada.getOriginalidade() + "\n" +
                        "Nota Final: " + selecionada.getNota()
        );
        alert.showAndWait();
    }

    private void atualizarAvaliacao() {
        Avaliacao selecionada = getAvaliacaoSelecionada();
        if (selecionada == null) return;

        Avaliacao atualizada = mostrarFormulario(selecionada);
        if (atualizada != null) {
            try {
                service.atualizar(atualizada);
                int index = avaliacoes.indexOf(selecionada);
                avaliacoes.set(index, atualizada);
                mostrarAlerta("Sucesso", "Avaliação atualizada com sucesso!");
            } catch (IOException | ClassNotFoundException e) {
                mostrarAlerta("Erro", "Erro ao atualizar: " + e.getMessage());
            }
        }
    }

    private void excluirAvaliacao() {
        Avaliacao selecionada = getAvaliacaoSelecionada();
        if (selecionada == null) return;

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Exclusão");
        confirmacao.setContentText("Deseja realmente excluir a avaliação ID " + selecionada.getId() + "?");

        if (confirmacao.showAndWait().get() == ButtonType.OK) {
            try {
                service.excluir(selecionada.getId());
                avaliacoes.remove(selecionada);
                mostrarAlerta("Sucesso", "Avaliação excluída com sucesso!");
            } catch (IOException | ClassNotFoundException e) {
                mostrarAlerta("Erro", "Erro ao excluir: " + e.getMessage());
            }
        }
    }

    private Avaliacao mostrarFormulario(Avaliacao avaliacao) {
        Stage formStage = new Stage();
        formStage.setTitle(avaliacao == null ? "Inserir Avaliação" : "Atualizar Avaliação");
        formStage.initModality(Modality.APPLICATION_MODAL);
        formStage.initOwner(stage);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setStyle("-fx-padding: 20; -fx-background-color: #fff0f5;");

        TextField txtAderencia = new TextField();
        TextField txtQualidade = new TextField();
        TextField txtOriginalidade = new TextField();
        TextField txtNota = new TextField();

        if (avaliacao != null) {
            txtAderencia.setText(String.valueOf(avaliacao.getAderencia()));
            txtQualidade.setText(String.valueOf(avaliacao.getQualidade()));
            txtOriginalidade.setText(String.valueOf(avaliacao.getOriginalidade()));
            txtNota.setText(String.valueOf(avaliacao.getNota()));
        }

        grid.addRow(0, new Label("Aderência:"), txtAderencia);
        grid.addRow(1, new Label("Qualidade:"), txtQualidade);
        grid.addRow(2, new Label("Originalidade:"), txtOriginalidade);
        grid.addRow(3, new Label("Nota Final:"), txtNota);

        Button btnSalvar = new Button("Salvar");
        Button btnCancelar = new Button("Cancelar");

        btnSalvar.setStyle("-fx-background-color: #ff69b4; -fx-text-fill: white;");
        btnCancelar.setStyle("-fx-background-color: #db7093; -fx-text-fill: white;");

        final Avaliacao[] resultado = {null};

        btnSalvar.setOnAction(e -> {
            try {
                int aderencia = Integer.parseInt(txtAderencia.getText());
                int qualidade = Integer.parseInt(txtQualidade.getText());
                int originalidade = Integer.parseInt(txtOriginalidade.getText());
                int nota = Integer.parseInt(txtNota.getText());

                if (avaliacao == null) {
                    resultado[0] = new Avaliacao(0, aderencia, qualidade, originalidade, nota);
                } else {
                    resultado[0] = new Avaliacao(avaliacao.getId(), aderencia, qualidade, originalidade, nota);
                }
                formStage.close();
            } catch (NumberFormatException ex) {
                mostrarAlerta("Erro", "Todos os campos devem ser números inteiros.");
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

        formStage.setScene(new Scene(root, 400, 300));
        formStage.showAndWait();

        return resultado[0];
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private Avaliacao getAvaliacaoSelecionada() {
        Avaliacao selecionada = listView.getSelectionModel().getSelectedItem();
        if (selecionada == null) {
            mostrarAlerta("Aviso", "Selecione uma avaliação.");
        }
        return selecionada;
    }
}
