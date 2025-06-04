package br.com.puc.poo.controllers;

import br.com.puc.poo.entidades.Minicurso;
import br.com.puc.poo.services.MinicursoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
        this.stage = new Stage();
        stage.setTitle("Gerenciamento de Minicursos");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(this.stageOwner);

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

        HBox botoesBox = new HBox(10);
        botoesBox.getChildren().addAll(btnInserir, btnConsultar, btnAtualizar, btnExcluir, btnFechar);

        VBox layout = new VBox(15);
        layout.setStyle("-fx-padding: 20;");
        layout.getChildren().addAll(new Label("Lista de Minicursos:"), listView, botoesBox);

        Scene cena = new Scene(layout, 600, 500);
        this.stage.setScene(cena);
    }

    private void carregarMinicursos() {
        try {
            List<Minicurso> listaMinicursos = service.listar();
            minicursos.clear();
            minicursos.addAll(listaMinicursos);
        } catch (Exception e) {
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
            } catch (Exception e) {
                mostrarAlerta("Erro", "Erro ao inserir minicurso: " + e.getMessage());
            }
        }
    }

    private void consultarMinicurso() {
        Minicurso selecionado = listView.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta("Aviso", "Selecione um minicurso para consultar.");
            return;
        }

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
        Minicurso selecionado = listView.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta("Aviso", "Selecione um minicurso para atualizar.");
            return;
        }

        Minicurso minicursoAtualizado = mostrarFormulario("Atualizar Minicurso", selecionado);
        if (minicursoAtualizado != null) {
            try {
                service.atualizar(minicursoAtualizado);
                int index = minicursos.indexOf(selecionado);
                minicursos.set(index, minicursoAtualizado);
                mostrarAlerta("Sucesso", "Minicurso atualizado com sucesso!");
            } catch (Exception e) {
                mostrarAlerta("Erro", "Erro ao atualizar minicurso: " + e.getMessage());
            }
        }
    }

    private void excluirMinicurso() {
        Minicurso selecionado = listView.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta("Aviso", "Selecione um minicurso para excluir.");
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Exclusão");
        confirmacao.setContentText("Deseja realmente excluir o minicurso \"" + selecionado.getTitulo() + "\"?");

        if (confirmacao.showAndWait().get() == ButtonType.OK) {
            try {
                service.excluir(selecionado.getTitulo());
                minicursos.remove(selecionado);
                mostrarAlerta("Sucesso", "Minicurso excluído com sucesso!");
            } catch (Exception e) {
                mostrarAlerta("Erro", "Erro ao excluir minicurso: " + e.getMessage());
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

        grid.add(new Label("Título:"), 0, 0);
        grid.add(txtTitulo, 1, 0);
        grid.add(new Label("Currículo:"), 0, 1);
        grid.add(txtCurriculo, 1, 1);
        grid.add(new Label("Duração (horas):"), 0, 2);
        grid.add(txtDuracao, 1, 2);
        grid.add(new Label("Justificativa:"), 0, 3);
        grid.add(txtJustificativa, 1, 3);
        grid.add(new Label("Material:"), 0, 4);
        grid.add(txtMaterial, 1, 4);
        grid.add(new Label("Objetivo:"), 0, 5);
        grid.add(txtObjetivo, 1, 5);
        grid.add(new Label("Público Alvo:"), 0, 6);
        grid.add(txtPublicoAlvo, 1, 6);

        Button btnSalvar = new Button("Salvar");
        Button btnCancelar = new Button("Cancelar");

        final Minicurso[] resultado = {null};

        btnSalvar.setOnAction(e -> {
            try {
                String tituloStr = txtTitulo.getText().trim();
                String curriculo = txtCurriculo.getText().trim();
                String justificativa = txtJustificativa.getText().trim();
                String material = txtMaterial.getText().trim();
                String objetivo = txtObjetivo.getText().trim();
                String publicoAlvo = txtPublicoAlvo.getText().trim();
                int duracao = Integer.parseInt(txtDuracao.getText().trim());

                if (tituloStr.isEmpty()) {
                    mostrarAlerta("Erro", "Título é obrigatório!");
                    return;
                }
                if (curriculo.isEmpty()) {
                    mostrarAlerta("Erro", "Currículo é obrigatório!");
                    return;
                }
                if (duracao <= 0) {
                    mostrarAlerta("Erro", "Duração deve ser um número positivo!");
                    return;
                }

                resultado[0] = new Minicurso(tituloStr, curriculo, duracao, justificativa, material, objetivo, publicoAlvo);
                formStage.close();
            } catch (NumberFormatException ex) {
                mostrarAlerta("Erro", "Duração deve conter um número válido!");
            } catch (Exception ex) {
                mostrarAlerta("Erro", "Dados inválidos: " + ex.getMessage());
            }
        });

        btnCancelar.setOnAction(e -> formStage.close());

        HBox botoes = new HBox(10);
        botoes.getChildren().addAll(btnSalvar, btnCancelar);

        VBox layout = new VBox(15);
        layout.getChildren().addAll(grid, botoes);

        Scene formScene = new Scene(layout, 500, 550);
        formStage.setScene(formScene);
        formStage.showAndWait();

        return resultado[0];
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}