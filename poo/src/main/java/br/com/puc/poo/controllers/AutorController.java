package br.com.puc.poo.controllers;

import br.com.puc.poo.entidades.Autor;
import br.com.puc.poo.services.AutorService;
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

public class AutorController {
    private Stage stage;
    private final Stage stageOwner;
    private final AutorService service;
    private final ObservableList<Autor> autores;
    private ListView<Autor> listView;

    public AutorController(Stage stageOwner) {
        this.stageOwner = stageOwner;
        this.service = new AutorService();
        this.autores = FXCollections.observableArrayList();
        carregarAutores();
    }

    public void mostrar() {
        criarUI();
        stage.showAndWait();
    }

    private void criarUI() {
        stage = new Stage();
        stage.setTitle("Gerenciamento de Autores");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(stageOwner);

        listView = new ListView<>(autores);
        listView.setPrefHeight(300);

        Button btnInserir = new Button("Inserir");
        btnInserir.setOnAction(e -> inserirAutor());

        Button btnConsultar = new Button("Consultar");
        btnConsultar.setOnAction(e -> consultarAutor());

        Button btnAtualizar = new Button("Atualizar");
        btnAtualizar.setOnAction(e -> atualizarAutor());

        Button btnExcluir = new Button("Excluir");
        btnExcluir.setOnAction(e -> excluirAutor());

        Button btnFechar = new Button("Fechar");
        btnFechar.setOnAction(e -> stage.close());

        HBox botoes = new HBox(10, btnInserir, btnConsultar, btnAtualizar, btnExcluir, btnFechar);
        botoes.setAlignment(Pos.CENTER);

        btnInserir.setStyle("-fx-background-color: #ffb6c1; -fx-text-fill: white;");
        btnConsultar.setStyle("-fx-background-color: #ff69b4; -fx-text-fill: white;");
        btnAtualizar.setStyle("-fx-background-color: #ff1493; -fx-text-fill: white;");
        btnExcluir.setStyle("-fx-background-color: #c71585; -fx-text-fill: white;");
        btnFechar.setStyle("-fx-background-color: #db7093; -fx-text-fill: white;");

        VBox layout = new VBox(15, new Label("Bem-vindo a página de autores! Aqui voce pode gerenciar todos os " +
                "autores:"),
                listView, botoes);
        layout.setStyle("-fx-padding: 20;");

        stage.setScene(new Scene(layout, 600, 500));
    }

    private void carregarAutores() {
        try {
            List<Autor> lista = service.listar();
            autores.setAll(lista);
        } catch (IOException | ClassNotFoundException e) {
            mostrarAlerta("Erro", "Erro ao carregar autores: " + e.getMessage());
        }
    }

    private void inserirAutor() {
        Autor novo = mostrarFormulario("Inserir Autor", null);
        if (novo != null) {
            try {
                service.inserir(novo);
                autores.add(novo);
                mostrarAlerta("Sucesso", "Autor inserido com sucesso!");
            }
            catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                mostrarAlerta("Erro", "Erro ao inserir: " + e.getMessage());
            }
        }
    }

    private void consultarAutor() {
        Autor selecionado = getAutorSelecionado();
        if (selecionado == null) return;

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Consultar Autor");
        alert.setHeaderText("Dados do Autor");
        alert.setContentText(
                "Nome: " + selecionado.getNome() + "\n" +
                        "Email: " + selecionado.getEmail() + "\n" +
                        "Data de nascimento: " + selecionado.getDataDeNascimento() + "\n" +
                        "Formação: " + selecionado.getFormacao()
        );
        alert.showAndWait();
    }

    private void atualizarAutor() {
        Autor selecionado = getAutorSelecionado();
        if (selecionado == null) return;

        Autor atualizado = mostrarFormulario("Atualizar Autor", selecionado);
        if (atualizado != null) {
            try {
                service.atualizar(atualizado);
                int index = autores.indexOf(selecionado);
                autores.set(index, atualizado);
                mostrarAlerta("Sucesso", "Autor atualizado com sucesso!");
            } catch (IOException | ClassNotFoundException e) {
                mostrarAlerta("Erro", "Erro ao atualizar: " + e.getMessage());
            }
        }
    }

    private void excluirAutor() {
        Autor selecionado = getAutorSelecionado();
        if (selecionado == null) return;

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Exclusão");
        confirmacao.setContentText("Deseja realmente excluir o autor \"" + selecionado.getNome() + "\"?");

        if (confirmacao.showAndWait().get() == ButtonType.OK) {
            try {
                service.excluir(selecionado.getEmail());
                autores.remove(selecionado);
                mostrarAlerta("Sucesso", "Autor excluído com sucesso!");
            } catch (IOException | ClassNotFoundException e) {
                mostrarAlerta("Erro", "Erro ao excluir: " + e.getMessage());
            }
        }
    }

    private Autor mostrarFormulario(String titulo, Autor autor) {
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
        TextField txtFormacao = new TextField();
        DatePicker dpDataNascimento = new DatePicker();
//        TextField txtNacionalidade = new TextField();

        if (autor != null) {
            txtNome.setText(autor.getNome());
            txtEmail.setText(autor.getEmail());
            txtFormacao.setText(autor.getFormacao());
            dpDataNascimento.setValue(autor.getDataDeNascimento());
//            txtNacionalidade.setText(autor.getNacionalidade());
        }

        grid.addRow(0, new Label("Nome:"), txtNome);
        grid.addRow(1, new Label("Email:"), txtEmail);
        grid.addRow(2, new Label("Formação:"), txtFormacao);
        grid.addRow(3, new Label("Data de Nascimento:"), dpDataNascimento);

        Button btnSalvar = new Button("Salvar");
        Button btnCancelar = new Button("Cancelar");

        grid.setStyle("-fx-padding: 20; -fx-background-color: #fff0f5;");

        btnSalvar.setStyle("-fx-background-color: #ff69b4; -fx-text-fill: white;");
        btnCancelar.setStyle("-fx-background-color: #db7093; -fx-text-fill: white;");

        final Autor[] resultado = {null};

        btnSalvar.setOnAction(e -> {
            String nome = txtNome.getText().trim();
            String email = txtEmail.getText().trim();
            String formacao = txtFormacao.getText().trim();
            LocalDate datadenascimento = dpDataNascimento.getValue();

            if (nome.isEmpty() || email.isEmpty() || formacao.isEmpty() || datadenascimento == null)
            {
                mostrarAlerta("Erro", "Todos os campos são obrigatórios.");
                return;
            }

            if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
                mostrarAlerta("Erro", "Email inválido.");
                return;
            }
            resultado[0] = new Autor(nome, datadenascimento, formacao, email);
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


    private Autor getAutorSelecionado() {
        Autor selecionado = listView.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta("Aviso", "Selecione um autor.");
        }
        return selecionado;
    }
}
