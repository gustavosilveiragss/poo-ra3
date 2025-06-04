package br.com.puc.poo;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModalController {
    private Stage stage;
    private Stage stageOwner;
    private Scene cena;
    private String titulo;

    public ModalController(Stage stageOwner, String titulo){
        this.stageOwner = stageOwner;
        this.titulo = titulo;
    }

    public void mostrar(){
        criarUI();
        stage.showAndWait();
    }

    private void criarUI(){
        this.stage = new Stage();
        stage.setTitle(titulo);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(this.stageOwner);

        Label labelMensagem = new Label("Esta é a " + titulo);
        labelMensagem.setFont(new Font("Arial",26));

        Label labelText = new Label("Ela permanece sobre a outra até ser fechada");
        labelText.setFont(new Font("Arial",18));

        Button btnFechar = new Button("Fechar Janela");
        btnFechar.setOnAction(event -> stage.close());

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        layout.getChildren().addAll(labelMensagem, labelText, btnFechar);

        this.cena = new Scene(layout, 500, 200);
        this.stage.setScene(this.cena);
    }
}