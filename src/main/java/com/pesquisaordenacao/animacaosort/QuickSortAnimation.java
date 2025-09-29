package com.pesquisaordenacao.animacaosort;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class QuickSortAnimation extends Application {

    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;
    private static final int VARS_MAX_COLS = 8;
    private static final int DELAY = 1500;

    // Usando Manrope como fonte principal
    Font manropeFont = Font.font("Manrope", 18);
    Font codeFont = Font.font("Manrope", 18);

    private BorderPane root;

    private VBox topBottomContainer;
    private HBox containerTop;
    private VBox containerVetores;
    private HBox bottomBottomContainer;
    private GridPane gridVariaveis;
    private VBox bottomContainer;
    private Button initQuickInterno;
    private Button voltarTelaInicial;
    private Button refazerQuickBtn;
    private VBox vectorLabelsContainer;
    private HBox buttonsContainer;
    private VBox containerCodigo;
    private VBox containerCodigoLinhas;
    private ScrollPane scrollPaneCodigo;

    private HashMap<String, VBox> variaveisMap = new HashMap<>();
    private HashMap<String, HBox> vectorContainerMap = new HashMap<>();
    private HashMap<String, HBox> vectorMap = new HashMap<>();
    private HashMap<String, Label> labelsMap = new HashMap<>();
    private HashMap<Integer, StackPane> linhasCodigoMap = new HashMap<>();
    private int[] vet;
    private int tl;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Quick Sort");
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

    private void configRandomValores() {
        tl = 8; // Fixed size as per user's quickSortComPivo class
        vet = new int[tl];
        Random rand = new Random();
        HashSet<Integer> valores = new HashSet<>();
        while (valores.size() < tl) {
            int num = rand.nextInt(100) + 1; // Values between 1 and 100
            if (valores.add(num))
                vet[valores.size() - 1] = num;
        }
    }

    private void quickSort() {
        configRandomValores();
        for (int i = 0; i < tl; i++) {
            atualizaVetorPorPos("vet", i, vet[i]);
        }
        bottomContainer.getChildren().remove(initQuickInterno);
        initQuickInterno = null;

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(() -> {
                    adicionarLinhaCodigo("public void quickRecursao() {", 0);
                    adicionarLinhaCodigo("    quickSP(0, TL-1);", 1);
                    adicionarLinhaCodigo("}", 2);
                    adicionarLinhaCodigo("", 3);
                    adicionarLinhaCodigo("public void quickSP(int inicio, int fim) {", 4);
                    adicionarLinhaCodigo("    int i = inicio, j = fim, aux;", 5);
                    adicionarLinhaCodigo("    while(i<j) {", 6);
                    adicionarLinhaCodigo("        while(i<j && vet[i] <= vet[j])", 7);
                    adicionarLinhaCodigo("            i++;", 8);
                    adicionarLinhaCodigo("", 9);
                    adicionarLinhaCodigo("        aux = vet[i];", 10);
                    adicionarLinhaCodigo("        vet[i] = vet[j];", 11);
                    adicionarLinhaCodigo("        vet[j] = aux;", 12);
                    adicionarLinhaCodigo("", 13);
                    adicionarLinhaCodigo("        while(i < j && vet[j] >= vet[i])", 14);
                    adicionarLinhaCodigo("            j--;", 15);
                    adicionarLinhaCodigo("", 16);
                    adicionarLinhaCodigo("        aux = vet[i];", 17);
                    adicionarLinhaCodigo("        vet[i] = vet[j];", 18);
                    adicionarLinhaCodigo("        vet[j] = aux;", 19);
                    adicionarLinhaCodigo("    }", 20);
                    adicionarLinhaCodigo("    if(inicio < i-1)", 21);
                    adicionarLinhaCodigo("        quickSP(inicio,i-1);", 22);
                    adicionarLinhaCodigo("    if(j+1<fim)", 23);
                    adicionarLinhaCodigo("        quickSP(j+1,fim);", 24);
                    adicionarLinhaCodigo("}", 25);

                    destacarLinha(0);
                });
                Thread.sleep(DELAY);

                Platform.runLater(() -> {
                    destacarLinha(1);
                });
                Thread.sleep(DELAY);

                quickRecursao();

                Platform.runLater(() -> {
                    limparTodosDestaques();
                    configurarBotoesFinal();
                    deletarVariavel("inicio");
                    deletarVariavel("fim");
                    deletarVariavel("i");
                    deletarVariavel("j");
                    deletarVariavel("aux");
                });
                Thread.sleep(1);
                return null;
            }
        };
        new Thread(task).start();
    }

    public void quickRecursao() throws InterruptedException {
        quickSP(0, tl - 1);
    }

    public void quickSP(int inicio, int fim) throws InterruptedException {
        final int finalInicio = inicio;
        final int finalFim = fim;
        Platform.runLater(() -> {
            destacarLinha(4);
            criaAtualizaVariavel("inicio", finalInicio);
            criaAtualizaVariavel("fim", finalFim);
        });
        Thread.sleep(DELAY);

        int i = inicio, j = fim;
        final int[] finalI = {i};
        final int[] finalJ = {j};
        Platform.runLater(() -> {
            destacarLinha(5);
            criaAtualizaVariavel("i", finalI[0]);
            criaAtualizaVariavel("j", finalJ[0]);
        });
        Thread.sleep(DELAY);

        while (finalI[0] < finalJ[0]) {
            Platform.runLater(() -> {
                destacarLinha(6);
            });
            Thread.sleep(DELAY);

            // Primeiro while: while(i<j && vet[i] <= vet[j]) i++;
            while (finalI[0] < finalJ[0] && vet[finalI[0]] <= vet[finalJ[0]]) {
                Platform.runLater(() -> {
                    destacarLinha(7);
                    animacaoAuxUsoDaPosicaoDoVetor("vet", finalI[0]);
                    animacaoAuxUsoDaPosicaoDoVetor("vet", finalJ[0]);
                });
                Thread.sleep(DELAY);
                finalI[0]++;
                Platform.runLater(() -> {
                    destacarLinha(8);
                    criaAtualizaVariavel("i", finalI[0]);
                });
                Thread.sleep(DELAY);
            }

            if (finalI[0] < finalJ[0]) {
                // Primeira troca
                int aux = vet[finalI[0]];
                final int finalAux1 = aux;
                final int swapI1 = finalI[0];
                final int swapJ1 = finalJ[0];
                Platform.runLater(() -> {
                    destacarLinha(10);
                    criaAtualizaVariavel("aux", finalAux1);
                });
                Thread.sleep(DELAY);

                // Realizar a troca no array primeiro
                vet[finalI[0]] = vet[finalJ[0]];
                vet[finalJ[0]] = finalAux1;

                Platform.runLater(() -> {
                    destacarLinha(11);
                    animarTrocaPosVet("vet", swapI1, swapJ1);
                });
                Thread.sleep(DELAY + 500);

                Platform.runLater(() -> {
                    destacarLinha(12);
                });
                Thread.sleep(DELAY);

                // Segundo while: while(i < j && vet[j] >= vet[i]) j--;
                while (finalI[0] < finalJ[0] && vet[finalJ[0]] >= vet[finalI[0]]) {
                    Platform.runLater(() -> {
                        destacarLinha(14);
                        animacaoAuxUsoDaPosicaoDoVetor("vet", finalI[0]);
                        animacaoAuxUsoDaPosicaoDoVetor("vet", finalJ[0]);
                    });
                    Thread.sleep(DELAY);
                    finalJ[0]--;
                    Platform.runLater(() -> {
                        destacarLinha(15);
                        criaAtualizaVariavel("j", finalJ[0]);
                    });
                    Thread.sleep(DELAY);
                }

                if (finalI[0] < finalJ[0]) {
                    // Segunda troca
                    aux = vet[finalI[0]];
                    final int finalAux2 = aux;
                    final int swapI2 = finalI[0];
                    final int swapJ2 = finalJ[0];
                    Platform.runLater(() -> {
                        destacarLinha(17);
                        criaAtualizaVariavel("aux", finalAux2);
                    });
                    Thread.sleep(DELAY);

                    // Realizar a troca no array primeiro
                    vet[finalI[0]] = vet[finalJ[0]];
                    vet[finalJ[0]] = finalAux2;

                    Platform.runLater(() -> {
                        destacarLinha(18);
                        animarTrocaPosVet("vet", swapI2, swapJ2);
                    });
                    Thread.sleep(DELAY + 500);

                    Platform.runLater(() -> {
                        destacarLinha(19);
                        deletarVariavel("aux");
                    });
                    Thread.sleep(DELAY);
                }
            }
        }
        Thread.sleep(DELAY);

        // Chamadas recursivas
        if (finalInicio < finalI[0] - 1) {
            Platform.runLater(() -> destacarLinha(21));
            Thread.sleep(DELAY);
            Platform.runLater(() -> destacarLinha(22));
            Thread.sleep(DELAY);
            quickSP(finalInicio, finalI[0] - 1);
            Thread.sleep(DELAY);
        }

        if (finalJ[0] + 1 < finalFim) {
            Platform.runLater(() -> destacarLinha(23));
            Thread.sleep(DELAY);
            Platform.runLater(() -> destacarLinha(24));
            Thread.sleep(DELAY);
            quickSP(finalJ[0] + 1, finalFim);
            Thread.sleep(DELAY);
        }
    }

    private void animarTrocaPosVet(String vectorName, int pos1, int pos2) {
        HBox boxesContainer = vectorMap.get(vectorName);
        if (boxesContainer != null && pos1 >= 0 && pos1 < boxesContainer.getChildren().size()
                && pos2 >= 0 && pos2 < boxesContainer.getChildren().size() && pos1 != pos2) {

            StackPane box1 = (StackPane) boxesContainer.getChildren().get(pos1);
            StackPane box2 = (StackPane) boxesContainer.getChildren().get(pos2);

            // Salvar os valores antes da animação
            Label label1 = (Label) box1.getChildren().get(0);
            Label label2 = (Label) box2.getChildren().get(0);
            String valor1 = label1.getText();
            String valor2 = label2.getText();

            // Forçar layout antes de calcular posições
            boxesContainer.applyCss();
            boxesContainer.layout();

            double distanciaX = box2.getBoundsInParent().getMinX() - box1.getBoundsInParent().getMinX();

            // Evitar divisão por zero e casos extremos
            if (Math.abs(distanciaX) < 1) {
                return;
            }

            // Criar as animações com durações mais balanceadas
            TranslateTransition subirBox1 = new TranslateTransition(Duration.millis(700), box1);
            subirBox1.setByY(-60);

            TranslateTransition moverHorizontalBox1 = new TranslateTransition(Duration.millis(1000), box1);
            moverHorizontalBox1.setByX(distanciaX);

            TranslateTransition descerBox1 = new TranslateTransition(Duration.millis(700), box1);
            descerBox1.setByY(60);

            TranslateTransition descerBox2 = new TranslateTransition(Duration.millis(700), box2);
            descerBox2.setByY(60);

            TranslateTransition moverHorizontalBox2 = new TranslateTransition(Duration.millis(1000), box2);
            moverHorizontalBox2.setByX(-distanciaX);

            TranslateTransition subirBox2 = new TranslateTransition(Duration.millis(700), box2);
            subirBox2.setByY(-60);

            SequentialTransition seqBox1 = new SequentialTransition(subirBox1, moverHorizontalBox1, descerBox1);
            SequentialTransition seqBox2 = new SequentialTransition(descerBox2, moverHorizontalBox2, subirBox2);
            ParallelTransition animacaoParalela = new ParallelTransition(seqBox1, seqBox2);

            animacaoParalela.setOnFinished(e -> {
                try {
                    // Reset das transformações
                    box1.setTranslateX(0);
                    box1.setTranslateY(0);
                    box2.setTranslateX(0);
                    box2.setTranslateY(0);

                    // Trocar apenas os valores dos labels, mantendo as posições dos elementos
                    label1.setText(valor2);
                    label2.setText(valor1);
                } catch (Exception ex) {
                    System.err.println("Erro na animação de troca: " + ex.getMessage());
                    ex.printStackTrace();
                }
            });

            animacaoParalela.play();
        }
    }

    private void configurarBotoesFinal() {
        buttonsContainer = new HBox(20);
        voltarTelaInicial = new Button("Voltar");
        voltarTelaInicial.setFont(Font.font("Manrope", 16));
        voltarTelaInicial.setStyle("-fx-background-color: #444444; -fx-border-width: 1px; -fx-border-color: white; -fx-border-style: solid; -fx-text-fill: white;");
        voltarTelaInicial.setOnAction(e -> voltarParaTelaInicial());
        buttonsContainer.getChildren().add(voltarTelaInicial);
        refazerQuickBtn = new Button("Refazer");
        refazerQuickBtn.setFont(Font.font("Manrope", 16));
        refazerQuickBtn.setStyle("-fx-background-color: #444444; -fx-border-width: 1px; -fx-border-color: white; -fx-border-style: solid; -fx-text-fill: white;");
        refazerQuickBtn.setOnAction(e -> refazerQuick());
        buttonsContainer.getChildren().add(refazerQuickBtn);
        buttonsContainer.setAlignment(Pos.CENTER);
        bottomContainer.getChildren().add(buttonsContainer);
    }

    private void refazerQuick() {
        for (int i = 0; i < 26; i++) { // Ajustado para 26 linhas (0-25)
            removerLinhaCodigo(i);
        }
        removerVetorDaTela("vet");
        bottomContainer.getChildren().remove(buttonsContainer);
        criarVetorNaTela("vet", vet);
        quickSort();
    }

    private void voltarParaTelaInicial() {
        vectorMap.clear();
        vectorContainerMap.clear();
        variaveisMap.clear();
        labelsMap.clear();
        vet = null;
        topBottomContainer = null;
        containerTop = null;
        containerVetores = null;
        bottomBottomContainer = null;
        gridVariaveis = null;
        bottomContainer = null;
        initQuickInterno = null;
        voltarTelaInicial = null;
        refazerQuickBtn = null;
        vectorLabelsContainer = null;
        buttonsContainer = null;
        configurarLayoutInicial();
    }

    private void configurarVetores() {
        vectorLabelsContainer = new VBox();
        vectorLabelsContainer.prefWidthProperty().bind(bottomBottomContainer.widthProperty().multiply(0.20));
        containerVetores = new VBox();
        containerVetores.prefWidthProperty().bind(bottomBottomContainer.widthProperty().multiply(0.79));
        bottomBottomContainer.getChildren().addAll(vectorLabelsContainer, containerVetores);
        vectorMap.clear();
        vectorContainerMap.clear();
        criarVetorNaTela("vet", vet);
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
        Button initQuick = new Button("Quick Sort");
        initQuick.setFont(Font.font("Manrope", 16));
        initQuick.setOnAction(e -> configurarQuick());
        initQuick.setMinWidth(150);
        initQuick.setStyle(
                "-fx-background-color: #444444; -fx-border-width: 1px; -fx-border-color: white; -fx-border-style: solid; -fx-border-radius: 0; -fx-text-fill: white;");
        StackPane centerPane = new StackPane(container);
        centerPane.setAlignment(Pos.CENTER);
        container.getChildren().addAll(initQuick);
        VBox namesContainer = new VBox(10);
        Label feito = new Label("Feito por:");
        Label g = new Label("Allan Maldonado");
        Label v = new Label("Daniel Andreassi");
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

    private void removerVetorDaTela(String nomeDoVetor) {
        HBox container = vectorMap.get(nomeDoVetor);
        if (container != null) {
            containerVetores.getChildren().remove(container);
            vectorMap.remove(nomeDoVetor);
            vectorContainerMap.remove(nomeDoVetor);
        }
        Label label = labelsMap.get(nomeDoVetor);
        if (label != null) {
            vectorLabelsContainer.getChildren().remove(label);
            labelsMap.remove(nomeDoVetor);
        }
    }

    private void deletarVariavel(String nome) {
        VBox container = variaveisMap.get(nome);
        if (container != null) {
            gridVariaveis.getChildren().remove(container);
            variaveisMap.remove(nome);
        }
    }

    private void criaAtualizaVariavel(String nome, int valor) {
        String styleBase = "-fx-border-radius: 0; -fx-border-color: white; -fx-border-width: 1px; -fx-min-width: 50px; -fx-background-color: #333333;";
        if (variaveisMap.containsKey(nome)) {
            VBox container = variaveisMap.get(nome);
            Label valorLabel = (Label) container.getChildren().get(1);
            valorLabel.setText(String.valueOf(valor));
            String originalStyle = container.getStyle();
            container.setStyle(originalStyle + " -fx-background-color: #2ECA23;");
            FadeTransition ft = new FadeTransition(Duration.millis(800), container);
            ft.setFromValue(1.0);
            ft.setToValue(0.3);
            ft.setCycleCount(2);
            ft.setAutoReverse(true);
            ft.setOnFinished(e -> container.setStyle(styleBase));
            ft.play();
        } else {
            VBox container = new VBox();
            container.setAlignment(Pos.CENTER);
            container.setSpacing(5);
            container.setPadding(new Insets(5));
            container.setStyle(styleBase);
            Label nomeLabel = new Label(nome);
            nomeLabel.setFont(Font.font("Manrope", 18));
            nomeLabel.setAlignment(Pos.CENTER);
            nomeLabel.setStyle("-fx-border-color: white; -fx-border-width: 0 0 1 0; -fx-text-fill: white;");
            Label valorLabel = new Label(String.valueOf(valor));
            valorLabel.setFont(Font.font("Manrope", 18));
            valorLabel.setAlignment(Pos.CENTER);
            valorLabel.setStyle("-fx-text-fill: white;");
            container.getChildren().addAll(nomeLabel, valorLabel);
            int index = variaveisMap.size();
            int col = index % VARS_MAX_COLS;
            int row = index / VARS_MAX_COLS;
            gridVariaveis.add(container, col, row);
            variaveisMap.put(nome, container);
            container.setStyle(styleBase + " -fx-background-color: #2ECA23;");
            FadeTransition ft = new FadeTransition(Duration.millis(800), container);
            ft.setFromValue(1.0);
            ft.setToValue(0.3);
            ft.setCycleCount(2);
            ft.setAutoReverse(true);
            ft.setOnFinished(e -> container.setStyle(styleBase));
            ft.play();
        }
    }

    private void criaAtualizaVariavel(String nome, String valor) {
        String styleBase = "-fx-border-radius: 0; -fx-border-color: white; -fx-border-width: 1px; -fx-min-width: 50px; -fx-background-color: #333333;";
        if (variaveisMap.containsKey(nome)) {
            VBox container = variaveisMap.get(nome);
            Label valorLabel = (Label) container.getChildren().get(1);
            valorLabel.setText(valor);
            String originalStyle = container.getStyle();
            container.setStyle(originalStyle + " -fx-background-color: #555555;");
            FadeTransition ft = new FadeTransition(Duration.millis(800), container);
            ft.setFromValue(1.0);
            ft.setToValue(0.3);
            ft.setCycleCount(2);
            ft.setAutoReverse(true);
            ft.setOnFinished(e -> container.setStyle(styleBase));
            ft.play();
        } else {
            VBox container = new VBox();
            container.setAlignment(Pos.CENTER);
            container.setSpacing(5);
            container.setPadding(new Insets(5));
            container.setStyle(styleBase);
            Label nomeLabel = new Label(nome);
            nomeLabel.setFont(Font.font("Manrope", 18));
            nomeLabel.setAlignment(Pos.CENTER);
            nomeLabel.setStyle("-fx-border-color: white; -fx-border-width: 0 0 1 0; -fx-text-fill: white;");
            Label valorLabel = new Label(valor);
            valorLabel.setFont(Font.font("Manrope", 18));
            valorLabel.setAlignment(Pos.CENTER);
            valorLabel.setStyle("-fx-text-fill: white;");
            container.getChildren().addAll(nomeLabel, valorLabel);
            int index = variaveisMap.size();
            int col = index % VARS_MAX_COLS;
            int row = index / VARS_MAX_COLS;
            gridVariaveis.add(container, col, row);
            variaveisMap.put(nome, container);
            container.setStyle(styleBase + " -fx-background-color: #555555;");
            FadeTransition ft = new FadeTransition(Duration.millis(800), container);
            ft.setFromValue(1.0);
            ft.setToValue(0.3);
            ft.setCycleCount(2);
            ft.setAutoReverse(true);
            ft.setOnFinished(e -> container.setStyle(styleBase));
            ft.play();
        }
    }

    private void criarVetorNaTela(String vectorName, int[] vetor) {
        try {
            Label label = new Label(vectorName);
            label.setFont(Font.font("Manrope", 21));
            label.setStyle("-fx-text-fill: white;");
            label.prefHeightProperty().bind(vectorLabelsContainer.heightProperty().multiply(0.32));
            labelsMap.put(vectorName, label);
            vectorLabelsContainer.getChildren().add(label);
            HBox boxesContainer = new HBox();
            boxesContainer.setSpacing(10);
            boxesContainer.setAlignment(Pos.CENTER);
            SequentialTransition sequentialAppear = new SequentialTransition();
            for (int i = 0; i < vetor.length; i++) {
                StackPane box = new StackPane();
                box.prefWidthProperty().bind(boxesContainer.widthProperty().divide(vetor.length));
                box.prefHeightProperty().bind(boxesContainer.heightProperty());
                box.minWidthProperty().bind(boxesContainer.widthProperty().divide(vetor.length).multiply(0.5));
                box.minHeightProperty().bind(boxesContainer.heightProperty().multiply(0.5));
                box.setOpacity(0);
                box.setStyle(
                        "-fx-border-color: white; -fx-border-width: 1px; -fx-border-radius: 0; -fx-background-radius: 0;");

                box.setBackground(
                        new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
                Label valueLabel = new Label(String.valueOf(vetor[i]));
                valueLabel.setFont(Font.font("Manrope", 14));
                valueLabel.setStyle("-fx-text-fill: white;");
                box.getChildren().add(valueLabel);
                boxesContainer.getChildren().add(box);
                FadeTransition fadeIn = new FadeTransition(Duration.millis(180), box);
                fadeIn.setFromValue(0);
                fadeIn.setToValue(1.0);
                sequentialAppear.getChildren().add(fadeIn);
            }
            containerVetores.getChildren().add(boxesContainer);
            boxesContainer.prefHeightProperty().bind(containerVetores.heightProperty().multiply(0.32));
            sequentialAppear.play();
            sequentialAppear.setOnFinished(e -> {
                PauseTransition pause = new PauseTransition(Duration.millis(100));
                pause.setOnFinished(event -> {
                    ParallelTransition parallelFade = new ParallelTransition();
                    boxesContainer.getChildren().forEach(node -> {
                        StackPane box = (StackPane) node;
                        FadeTransition fadeToDark = new FadeTransition(Duration.millis(800), box);
                        fadeToDark.setFromValue(1.0);
                        fadeToDark.setToValue(0.7);
                        fadeToDark.setCycleCount(1);
                        fadeToDark.setAutoReverse(false);
                        fadeToDark.setOnFinished(evt -> {
                            box.setBackground(
                                    new Background(
                                            new BackgroundFill(Color.web("#222222"), CornerRadii.EMPTY, Insets.EMPTY)));
                            box.setOpacity(1.0);
                        });
                        parallelFade.getChildren().add(fadeToDark);
                    });
                    parallelFade.play();
                });
                pause.play();
            });
            vectorMap.put(vectorName, boxesContainer);
            vectorContainerMap.put(vectorName, boxesContainer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void atualizaVetorPorPos(String vectorName, int pos, int newValue) {
        HBox boxesContainer = vectorMap.get(vectorName);
        if (boxesContainer != null && pos >= 0 && pos < boxesContainer.getChildren().size()) {
            StackPane box = (StackPane) boxesContainer.getChildren().get(pos);
            Label valueLabel = (Label) box.getChildren().get(0);
            valueLabel.setText(String.valueOf(newValue));
            box.setStyle(
                    "-fx-border-color: white; -fx-border-width: 1px; -fx-border-radius: 0; -fx-background-radius: 0; -fx-background-color: #2ECA23;");
            FadeTransition ft = new FadeTransition(Duration.millis(800), box);
            ft.setFromValue(1.0);
            ft.setToValue(0.3);
            ft.setCycleCount(2);
            ft.setAutoReverse(true);
            ft.setOnFinished(e -> box.setStyle(
                    "-fx-border-color: white; -fx-border-width: 1px; -fx-border-radius: 0; -fx-background-radius: 0;"));
            ft.play();
        }
    }

    private void animacaoAuxUsoDaPosicaoDoVetor(String vectorName, int pos) {
        HBox boxesContainer = vectorMap.get(vectorName);
        if (boxesContainer != null && pos >= 0 && pos < boxesContainer.getChildren().size()) {
            StackPane box = (StackPane) boxesContainer.getChildren().get(pos);
            box.setStyle(
                    "-fx-border-color: white; -fx-border-width: 1px; -fx-border-radius: 0; -fx-background-radius: 0; -fx-background-color: deepskyblue;");
            FadeTransition ft = new FadeTransition(Duration.millis(800), box);
            ft.setFromValue(1.0);
            ft.setToValue(0.3);
            ft.setCycleCount(2);
            ft.setAutoReverse(true);
            ft.setOnFinished(e -> box.setStyle(
                    "-fx-border-color: white; -fx-border-width: 1px; -fx-border-radius: 0; -fx-background-radius: 0;"));
            ft.play();
        }
    }

    private void configurarQuick() {
        cleanRoot();
        vet = new int[8]; // TL = 8 as per user's class
        tl = 8;
        configurarLayout();
        configurarCodigo();
        configurarVetores();
        configurarStartBtn();
    }

    public void configurarStartBtn() {
        initQuickInterno = new Button("Iniciar");
        initQuickInterno.setFont(Font.font("Manrope", 16));
        initQuickInterno.setStyle(
                "-fx-background-color: #444444; -fx-border-width: 1px; -fx-border-color: white; -fx-border-style: solid; -fx-border-radius: 0; -fx-text-fill: white;");
        initQuickInterno.setOnAction(e -> quickSort());
        bottomContainer.getChildren().add(initQuickInterno);
    }

    private void configurarLayout() {
        topBottomContainer = new VBox();
        topBottomContainer.setPadding(new Insets(10));
        double spacing = 20;
        topBottomContainer.setSpacing(spacing);
        containerTop = new HBox();
        containerTop.setSpacing(10);
        VBox boxLeft = new VBox();
        boxLeft.setAlignment(Pos.TOP_CENTER);
        boxLeft.setPadding(new Insets(10));
        boxLeft.setSpacing(10);
        boxLeft.setStyle(
                "-fx-border-color: white; -fx-border-width: 1px; -fx-border-radius: 0; -fx-background-radius: 0; -fx-background-color: #222222;");
        Label variaveisLabel = new Label("Variáveis");
        variaveisLabel.setFont(Font.font("Manrope", 21));
        variaveisLabel.setStyle("-fx-text-fill: white;");
        gridVariaveis = new GridPane();
        gridVariaveis.setHgap(10);
        gridVariaveis.setVgap(8);
        gridVariaveis.setAlignment(Pos.TOP_LEFT);
        boxLeft.getChildren().addAll(variaveisLabel, gridVariaveis);
        containerCodigo = new VBox();
        containerCodigo.setAlignment(Pos.TOP_CENTER);
        containerCodigo.setPadding(new Insets(10));
        containerCodigo.setStyle(
                "-fx-border-color: white; -fx-border-width: 1px; -fx-border-radius: 0; -fx-background-radius: 0; -fx-background-color: #222222;");
        HBox.setHgrow(boxLeft, Priority.ALWAYS);
        HBox.setHgrow(containerCodigo, Priority.ALWAYS);
        boxLeft.prefWidthProperty().bind(containerTop.widthProperty().multiply(0.5));
        containerCodigo.prefWidthProperty().bind(containerTop.widthProperty().multiply(0.5));
        containerTop.getChildren().addAll(boxLeft, containerCodigo);
        bottomContainer = new VBox(10);
        bottomContainer.setAlignment(Pos.TOP_CENTER);
        bottomContainer.setStyle(
                "-fx-border-color: white; -fx-border-width: 1px; -fx-border-radius: 0; -fx-background-radius: 0; -fx-background-color: #222222;");
        bottomContainer.setPadding(new Insets(10));
        bottomBottomContainer = new HBox();
        bottomBottomContainer.setSpacing(10);
        Label labelVetores = new Label("Vetores");
        labelVetores.setFont(Font.font("Manrope", 21));
        labelVetores.setStyle("-fx-text-fill: white;");
        bottomContainer.getChildren().addAll(labelVetores, bottomBottomContainer);
        topBottomContainer.getChildren().addAll(containerTop, bottomContainer);
        containerTop.prefHeightProperty()
                .bind(topBottomContainer.heightProperty().subtract(spacing).multiply(0.4));
        bottomContainer.prefHeightProperty()
                .bind(topBottomContainer.heightProperty().subtract(spacing).multiply(0.55));
        bottomBottomContainer.prefHeightProperty().bind(bottomContainer.heightProperty().multiply(0.90));
        root.setCenter(topBottomContainer);
    }

    private void configurarCodigo() {
        Label label = new Label("Código");
        label.setFont(Font.font("Manrope", 21));
        label.setStyle("-fx-text-fill: white;");

        containerCodigoLinhas = new VBox();
        containerCodigoLinhas.setPadding(new Insets(10));

        // Adiciona scroll para o código
        scrollPaneCodigo = new ScrollPane(containerCodigoLinhas);
        scrollPaneCodigo.setFitToWidth(true);
        scrollPaneCodigo.setFitToHeight(true);
        scrollPaneCodigo.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPaneCodigo.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPaneCodigo.setStyle("-fx-background: #222222; -fx-background-color: #222222;");

        containerCodigo.getChildren().addAll(label, scrollPaneCodigo);
    }

    private void adicionarLinhaCodigo(String linha, int num) {
        StackPane box = new StackPane();
        box.setAlignment(Pos.TOP_LEFT);
        box.setMaxHeight(20);
        box.setPrefHeight(20);
        Label valueLabel = new Label(linha);
        valueLabel.setFont(Font.font("Manrope", 14));
        valueLabel.setStyle("-fx-text-fill: white;");
        box.getChildren().add(valueLabel);
        linhasCodigoMap.put(num, box);
        containerCodigoLinhas.getChildren().add(box);
    }

    private void limparTodosDestaques() {
        for (StackPane box : linhasCodigoMap.values()) {
            if (box != null) {
                box.setStyle("-fx-background-color: #222222;");
            }
        }
    }

    private void destacarLinha(int numLinha) {
        // Primeiro remove todos os destaques
        limparTodosDestaques();

        // Depois destaca apenas a linha atual
        StackPane box = linhasCodigoMap.get(numLinha);
        if (box != null) {
            box.setStyle("-fx-background-color: #2ECA23;");

            // Auto scroll para a linha destacada
            scrollParaLinha(box);
        }
    }

    private void scrollParaLinha(StackPane linhaBox) {
        if (scrollPaneCodigo != null && linhaBox != null) {
            Platform.runLater(() -> {
                // Calcula a posição da linha no container
                double linhaHeight = linhaBox.getHeight() > 0 ? linhaBox.getHeight() : 20;
                double containerHeight = containerCodigoLinhas.getHeight();
                double scrollHeight = scrollPaneCodigo.getViewportBounds().getHeight();

                // Encontra o índice da linha
                int index = containerCodigoLinhas.getChildren().indexOf(linhaBox);

                if (index >= 0 && containerHeight > scrollHeight) {
                    // Calcula a posição ideal do scroll (centraliza a linha na viewport)
                    double linhaY = index * linhaHeight;
                    double scrollPosition = (linhaY - scrollHeight / 2) / (containerHeight - scrollHeight);

                    // Garante que o scroll fique entre 0 e 1
                    scrollPosition = Math.max(0, Math.min(1, scrollPosition));

                    // Aplica o scroll suavemente
                    scrollPaneCodigo.setVvalue(scrollPosition);
                }
            });
        }
    }


    private void removerLinhaCodigo(int num) {
        StackPane box = linhasCodigoMap.get(num);
        if (box != null) {
            containerCodigoLinhas.getChildren().remove(box);
            linhasCodigoMap.remove(num);
        }
    }
}
