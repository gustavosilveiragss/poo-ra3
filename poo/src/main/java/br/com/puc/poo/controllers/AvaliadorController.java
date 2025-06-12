package br.com.puc.poo.controllers;

import br.com.puc.poo.entidades.Avaliador;
import br.com.puc.poo.services.AvaliadorService;
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
import java.time.LocalDate;
import java.util.List;

public class AvaliadorController {
    private Stage stage;
    private final Stage stageOwner;
    private final AvaliadorService service;
    private final ObservableList<Avaliador> avaliadores;
    private ListView<Avaliador> listView;

    public AvaliadorController(Stage stageOwner) {
        this.stageOwner = stageOwner;
        this.service = new AvaliadorService();
        this.avaliadores = FXCollections.observableArrayList();
        carregarAvaliadores();
    }

    public void mostrar() {
        criarUI();
        stage.showAndWait();
    }

    private void criarUI() {
        stage = new Stage();
        stage.setTitle("Gerenciamento de Avaliadores");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(stageOwner);

        listView = new ListView<>(avaliadores);
        listView.setPrefHeight(300);

        Button btnInserir = new Button("Inserir");
        btnInserir.setOnAction(e -> inserirAvaliador());

        Button btnConsultar = new Button("Consultar");
        btnConsultar.setOnAction(e -> consultarAvaliador());

        Button btnAtualizar = new Button("Atualizar");
        btnAtualizar.setOnAction(e -> atualizarAvaliador());

        Button btnExcluir = new Button("Excluir");
        btnExcluir.setOnAction(e -> excluirAvaliador());

        Button btnFechar = new Button("Fechar");
        btnFechar.setOnAction(e -> stage.close());

        HBox botoes = new HBox(10, btnInserir, btnConsultar, btnAtualizar, btnExcluir, btnFechar);
        botoes.setAlignment(Pos.CENTER);

        btnInserir.setStyle("-fx-background-color: #ffb6c1; -fx-text-fill: white;");
        btnConsultar.setStyle("-fx-background-color: #ff69b4; -fx-text-fill: white;");
        btnAtualizar.setStyle("-fx-background-color: #ff1493; -fx-text-fill: white;");
        btnExcluir.setStyle("-fx-background-color: #c71585; -fx-text-fill: white;");
        btnFechar.setStyle("-fx-background-color: #db7093; -fx-text-fill: white;");

        VBox layout = new VBox(15, new Label("Bem-vindo a pagina de avaliadores! Aqui voce pode gerenciar todos os " +
                "avaliadores:"),
                listView, botoes);
        layout.setStyle("-fx-padding: 20;");

        stage.setScene(new Scene(layout, 600, 500));
    }

    private void carregarAvaliadores() {
        try {
            List<Avaliador> lista = service.listar();
            avaliadores.setAll(lista);
        } catch (IOException | ClassNotFoundException e) {
            mostrarAlerta("Erro", "Erro ao carregar avaliadores: " + e.getMessage());
        }
    }

    private void inserirAvaliador() {
        Avaliador novo = mostrarFormulario("Inserir Avaliador", null);
        if (novo != null) {
            try {
                service.inserir(novo);
                avaliadores.add(novo);
                mostrarAlerta("Sucesso", "Avaliador inserido com sucesso!");
            } catch (IOException | ClassNotFoundException e) {
                mostrarAlerta("Erro", "Erro ao inserir: " + e.getMessage());
            }
        }
    }

    private void consultarAvaliador() {
        Avaliador selecionado = getAvaliadorSelecionado();
        if (selecionado == null) return;

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Consultar Avaliador");
        alert.setHeaderText("Dados do Avaliador");
        alert.setContentText(
                "Nome: " + selecionado.getNome() + "\n" +
                        "Email: " + selecionado.getEmail() + "\n" +
                        "Celular: " + selecionado.getCelular() + "\n" +
                        "Data de Inscrição: " + selecionado.getDataDeInscricao()
        );
        alert.showAndWait();
    }

    private void atualizarAvaliador() {
        Avaliador selecionado = getAvaliadorSelecionado();
        if (selecionado == null) return;

        Avaliador original = new Avaliador(
                selecionado.getEmail(),
                selecionado.getNome(),
                selecionado.getCelular(),
                selecionado.getDataDeInscricao()
        );

        Avaliador atualizado = mostrarFormulario("Atualizar Avaliador", selecionado);
        if (atualizado != null) {
            try {
                service.atualizar(original, atualizado);
                int index = avaliadores.indexOf(selecionado);
                avaliadores.set(index, atualizado);
                mostrarAlerta("Sucesso", "Avaliador atualizado com sucesso!");
            } catch (IOException | ClassNotFoundException e) {
                mostrarAlerta("Erro", "Erro ao atualizar: " + e.getMessage());
            }
        }
    }

    private void excluirAvaliador() {
        Avaliador selecionado = getAvaliadorSelecionado();
        if (selecionado == null) return;

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Exclusão");
        confirmacao.setContentText("Deseja realmente excluir o avaliador \"" + selecionado.getNome() + "\"?");

        if (confirmacao.showAndWait().get() == ButtonType.OK) {
            try {
                service.excluir(selecionado.getEmail());
                avaliadores.remove(selecionado);
                mostrarAlerta("Sucesso", "Avaliador excluído com sucesso!");
            } catch (IOException | ClassNotFoundException e) {
                mostrarAlerta("Erro", "Erro ao excluir: " + e.getMessage());
            }
        }
    }

    private Avaliador mostrarFormulario(String titulo, Avaliador avaliador) {
        Stage formStage = new Stage();
        formStage.setTitle(titulo);
        formStage.initModality(Modality.APPLICATION_MODAL);
        formStage.initOwner(stage);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setStyle("-fx-padding: 20;");

        TextField txtNome = new TextField();
        TextField txtEmail = new TextField();
        TextField txtCelular = new TextField();
        DatePicker dpDataInscricao = new DatePicker();

        if (avaliador != null) {
            txtNome.setText(avaliador.getNome());
            txtEmail.setText(avaliador.getEmail());
            txtCelular.setText(avaliador.getCelular());
            dpDataInscricao.setValue(avaliador.getDataDeInscricao());
        }

        grid.addRow(0, new Label("Nome:"), txtNome);
        grid.addRow(1, new Label("Email:"), txtEmail);
        grid.addRow(2, new Label("Celular:"), txtCelular);
        grid.addRow(3, new Label("Data de Inscrição:"), dpDataInscricao);

        Button btnSalvar = new Button("Salvar");
        Button btnCancelar = new Button("Cancelar");

        grid.setStyle("-fx-padding: 20; -fx-background-color: #fff0f5;");

        btnSalvar.setStyle("-fx-background-color: #ff69b4; -fx-text-fill: white;");
        btnCancelar.setStyle("-fx-background-color: #db7093; -fx-text-fill: white;");

        final Avaliador[] resultado = {null};

        btnSalvar.setOnAction(e -> {
            String nome = txtNome.getText().trim();
            String email = txtEmail.getText().trim();
            String celular = txtCelular.getText().trim();
            LocalDate data = dpDataInscricao.getValue();

            if (nome.isEmpty() || email.isEmpty() || celular.isEmpty() || data == null) {
                mostrarAlerta("Erro", "Todos os campos são obrigatórios.");
                return;
            }

            if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
                mostrarAlerta("Erro", "Email inválido.");
                return;
            }

            if (!celular.matches("^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$")) {
                mostrarAlerta("Erro", "Celular inválido.");
                return;
            }

            resultado[0] = new Avaliador(email, nome, celular, data);
            formStage.close();
        });

        btnCancelar.setOnAction(e -> formStage.close());

        HBox botoes = new HBox(10, btnSalvar, btnCancelar);
        botoes.setAlignment(Pos.CENTER);

        VBox conteudo = new VBox(15, grid, botoes);
        conteudo.setAlignment(Pos.CENTER);
        conteudo.setStyle("-fx-padding: 20;");

        BorderPane root = new BorderPane();
        root.setCenter(conteudo);

        formStage.setScene(new Scene(root, 500, 300));
        formStage.showAndWait();

        return resultado[0];
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }


    private Avaliador getAvaliadorSelecionado() {
        Avaliador selecionado = listView.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta("Aviso", "Selecione um avaliador.");
        }
        return selecionado;
    }
}
