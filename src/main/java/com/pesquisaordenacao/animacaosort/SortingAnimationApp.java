package com.pesquisaordenacao.animacaosort;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SortingAnimationApp extends Application {

    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;

    // Usando Manrope como fonte principal
    Font manropeFont = Font.font("Manrope", 18);
    Font codeFont = Font.font("Manrope", 18);

    private BorderPane root;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sorting Animations");
        configurarLayoutInicial();
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void cleanRoot() {
        if (root != null) {
            root.setTop(null);
            root.setBottom(null);
            root.setLeft(null);
            root.setRight(null);
            root.setCenter(null);
        }
    }

    private void configurarLayoutInicial() {
        cleanRoot();
        System.out.println("CONFIGURANDO LAYOUT PRINCIPAL");
        if (root == null) {
            root = new BorderPane();
            root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        }
        HBox container = new HBox(20);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(20));
        Button initMerge = new Button("Merge Sort");
        initMerge.setFont(Font.font("Manrope", 16));
        initMerge.setOnAction(e -> {
            Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            new MergeSortAnimation().start(stage);
        });
        initMerge.setMinWidth(150);
        initMerge.setStyle(
                "-fx-background-color: #444444; -fx-border-width: 1px; -fx-border-color: white; -fx-border-style: solid; -fx-border-radius: 0; -fx-text-fill: white;");
        Button initQuick = new Button("Quick Sort");
        initQuick.setFont(Font.font("Manrope", 16));
        initQuick.setOnAction(e -> {
            Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            new QuickSortAnimation().start(stage);
        });
        initQuick.setMinWidth(150);
        initQuick.setStyle(
                "-fx-background-color: #444444; -fx-border-width: 1px; -fx-border-color: white; -fx-border-style: solid; -fx-border-radius: 0; -fx-text-fill: white;");
        StackPane centerPane = new StackPane(container);
        centerPane.setAlignment(Pos.CENTER);
        container.getChildren().addAll(initMerge, initQuick);
        VBox namesContainer = new VBox(10);
        Label feito = new Label("Feito por:");
        Label g = new Label("Allan Maldonado");
        Label v = new Label("Daniel Andreassi Lopes");
        feito.setStyle("-fx-text-fill: white;");
        feito.setFont(Font.font("Manrope", 18));
        feito.setAlignment(Pos.CENTER);
        g.setStyle("-fx-text-fill: white;");
        g.setFont(Font.font("Manrope", 18));
        g.setAlignment(Pos.CENTER);
        v.setStyle("-fx-text-fill: white;");
        v.setFont(Font.font("Manrope", 18));
        v.setAlignment(Pos.CENTER);
        namesContainer.getChildren().addAll(feito, g, v);
        root.setCenter(centerPane);
        root.setBottom(namesContainer);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
