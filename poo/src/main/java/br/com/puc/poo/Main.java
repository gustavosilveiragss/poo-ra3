package br.com.puc.poo;

import br.com.puc.poo.controllers.AvaliadorController;
import br.com.puc.poo.controllers.AvaliacaoController;
import br.com.puc.poo.controllers.MinicursoController;
import br.com.puc.poo.controllers.AutorController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sistema de Gerenciamento Acadêmico");

        Button btnAutor = new Button("Gerenciar Autores");
        btnAutor.setPrefWidth(200);
        btnAutor.setOnAction(e -> new AutorController(primaryStage).mostrar());

        Button btnMinicurso = new Button("Gerenciar Minicursos");
        btnMinicurso.setPrefWidth(200);
        btnMinicurso.setOnAction(e -> new MinicursoController(primaryStage).mostrar());

        Button btnAvaliador = new Button("Gerenciar Avaliadores");
        btnAvaliador.setPrefWidth(200);
        btnAvaliador.setOnAction(e -> new AvaliadorController(primaryStage).mostrar());

        Button btnAvaliacao = new Button("Gerenciar Avaliações");
        btnAvaliacao.setPrefWidth(200);
        btnAvaliacao.setOnAction(e -> new AvaliacaoController(primaryStage).mostrar());

        VBox layout = new VBox(15);
        layout.setStyle("-fx-padding: 30; -fx-alignment: center;");
        layout.getChildren().addAll(btnAutor, btnMinicurso, btnAvaliador, btnAvaliacao);

        Scene cenaPrincipal = new Scene(layout, 500, 400);
        primaryStage.setScene(cenaPrincipal);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
