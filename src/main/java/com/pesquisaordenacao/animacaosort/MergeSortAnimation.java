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

public class MergeSortAnimation extends Application {

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
    private Button initMergeInterno;
    private Button voltarTelaInicial;
    private Button refazerMergeBtn;
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
        primaryStage.setTitle("Merge Sort");
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
        tl = 8; // Fixed size as per user's mergeSort class
        vet = new int[tl];
        Random rand = new Random();
        HashSet<Integer> valores = new HashSet<>();
        while (valores.size() < tl) {
            int num = rand.nextInt(100) + 1; // Values between 1 and 100
            if (valores.add(num))
                vet[valores.size() - 1] = num;
        }
    }

    private void mergeSort() {
        configRandomValores();
        for (int i = 0; i < tl; i++) {
            atualizaVetorPorPos("vet", i, vet[i]);
        }
        bottomContainer.getChildren().remove(initMergeInterno);
        initMergeInterno = null;

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(() -> {
                    adicionarLinhaCodigo("public void MergeSort() {", 0);
                    adicionarLinhaCodigo("    int aux[] = new int[TL];", 1);
                    adicionarLinhaCodigo("    merge(0,TL-1,aux);", 2);
                    adicionarLinhaCodigo("}", 3);
                    adicionarLinhaCodigo("", 4);
                    adicionarLinhaCodigo("public void merge(int esquerda, int direita, int aux[]) {", 5);
                    adicionarLinhaCodigo("    if(esquerda < direita) {", 6);
                    adicionarLinhaCodigo("        int meio = (esquerda + direita) / 2;", 7);
                    adicionarLinhaCodigo("        merge(esquerda, meio, aux);", 8);
                    adicionarLinhaCodigo("        merge(meio + 1, direita, aux);", 9);
                    adicionarLinhaCodigo("        fusao(esquerda, meio, meio+1,direita,aux);", 10);
                    adicionarLinhaCodigo("    }", 11);
                    adicionarLinhaCodigo("}", 12);
                    adicionarLinhaCodigo("", 13);
                    adicionarLinhaCodigo("public void fusao(int inicio1, int fim1, int inicio2, int fim2, int aux[]) {", 14);
                    adicionarLinhaCodigo("    int i = inicio1, j = inicio2, k = 0;", 15);
                    adicionarLinhaCodigo("    while(i <= fim1 && j <= fim2) {", 16); // Corrected condition
                    adicionarLinhaCodigo("        if(vetor[i] < vetor[j])", 17);
                    adicionarLinhaCodigo("            aux[k++] = vetor[i++];", 18);
                    adicionarLinhaCodigo("        else", 19);
                    adicionarLinhaCodigo("            aux[k++] = vetor[j++];", 20);
                    adicionarLinhaCodigo("    }", 21);
                    adicionarLinhaCodigo("    while(i <= fim1)", 22); // Corrected condition
                    adicionarLinhaCodigo("        aux[k++] = vetor[i++];", 23);
                    adicionarLinhaCodigo("    while(j <= fim2)", 24); // Corrected condition
                    adicionarLinhaCodigo("        aux[k++] = vetor[j++];", 25);
                    adicionarLinhaCodigo("", 26);
                    adicionarLinhaCodigo("    for(i = 0; i < k; i++) {", 27);
                    adicionarLinhaCodigo("        vetor[inicio1+i] = aux[i];", 28);
                    adicionarLinhaCodigo("    }", 29);
                    adicionarLinhaCodigo("}", 30);

                    destacarLinha(0);
                });
                Thread.sleep(DELAY);

                int[] aux = new int[tl];
                Platform.runLater(() -> {
                    destacarLinha(1);
                    criarVetorNaTela("aux", aux);
                });
                Thread.sleep(DELAY);

                Platform.runLater(() -> {
                    destacarLinha(2);
                });
                Thread.sleep(DELAY);

                merge(0, tl - 1, aux);

                Platform.runLater(() -> {
                    limparTodosDestaques();
                    configurarBotoesFinal();
                    removerVetorDaTela("aux");
                    deletarVariavel("esquerda");
                    deletarVariavel("direita");
                    deletarVariavel("meio");
                    deletarVariavel("inicio1");
                    deletarVariavel("fim1");
                    deletarVariavel("inicio2");
                    deletarVariavel("fim2");
                    deletarVariavel("i");
                    deletarVariavel("j");
                    deletarVariavel("k");
                });
                Thread.sleep(1);
                return null;
            }
        };
        new Thread(task).start();
    }

    public void merge(int esquerda, int direita, int aux[]) throws InterruptedException {
        final int finalEsquerda = esquerda;
        final int finalDireita = direita;
        Platform.runLater(() -> {
            destacarLinha(5);
            criaAtualizaVariavel("esquerda", finalEsquerda);
            criaAtualizaVariavel("direita", finalDireita);
        });
        Thread.sleep(DELAY);

        Platform.runLater(() -> {
            destacarLinha(6);
        });
        Thread.sleep(DELAY);

        if (esquerda < direita) {
            Thread.sleep(DELAY);

            int meio = (esquerda + direita) / 2;
            final int finalMeio = meio;
            Platform.runLater(() -> {
                destacarLinha(7);
                criaAtualizaVariavel("meio", finalMeio);
            });
            Thread.sleep(DELAY);

            Platform.runLater(() -> {
                destacarLinha(8);
            });
            Thread.sleep(DELAY);
            merge(esquerda, meio, aux);
            Thread.sleep(DELAY);


            Platform.runLater(() -> destacarLinha(9));
            Thread.sleep(DELAY);
            merge(meio + 1, direita, aux);
            Thread.sleep(DELAY);


            Platform.runLater(() -> destacarLinha(10));
            Thread.sleep(DELAY);
            fusao(esquerda, meio, meio + 1, direita, aux);
            Thread.sleep(DELAY);

        } else {
            Thread.sleep(DELAY);
        }
    }

    public void fusao(int inicio1, int fim1, int inicio2, int fim2, int aux[]) throws InterruptedException {
        final int finalInicio1 = inicio1;
        final int finalFim1 = fim1;
        final int finalInicio2 = inicio2;
        final int finalFim2 = fim2;
        Platform.runLater(() -> {
            destacarLinha(14);
            criaAtualizaVariavel("inicio1", finalInicio1);
            criaAtualizaVariavel("fim1", finalFim1);
            criaAtualizaVariavel("inicio2", finalInicio2);
            criaAtualizaVariavel("fim2", finalFim2);
        });
        Thread.sleep(DELAY);

        int i = inicio1, j = inicio2, k = 0;
        final int[] finalI = {i};
        final int[] finalJ = {j};
        final int[] finalK = {k};
        Platform.runLater(() -> {
            destacarLinha(15);
            criaAtualizaVariavel("i", finalI[0]);
            criaAtualizaVariavel("j", finalJ[0]);
            criaAtualizaVariavel("k", finalK[0]);
        });
        Thread.sleep(DELAY);

        while (finalI[0] <= finalFim1 && finalJ[0] <= finalFim2) { // Corrected condition
            final int currentI = finalI[0];
            final int currentJ = finalJ[0];
            Platform.runLater(() -> {
                destacarLinha(16);
                animacaoAuxUsoDaPosicaoDoVetor("vet", currentI);
                animacaoAuxUsoDaPosicaoDoVetor("vet", currentJ);
            });
            Thread.sleep(DELAY);

            if (vet[finalI[0]] < vet[finalJ[0]]) {
                Platform.runLater(() -> {
                    destacarLinha(17);
                });
                Thread.sleep(DELAY);

                aux[finalK[0]] = vet[finalI[0]];
                final int auxK = finalK[0];
                final int auxVal = aux[finalK[0]];
                Platform.runLater(() -> {
                    destacarLinha(18);
                    atualizaVetorPorPos("aux", auxK, auxVal);
                    animacaoAuxUsoDaPosicaoDoVetor("aux", auxK);
                });
                Thread.sleep(DELAY);
                finalI[0]++;
                finalK[0]++;
                Platform.runLater(() -> {
                    criaAtualizaVariavel("i", finalI[0]);
                    criaAtualizaVariavel("k", finalK[0]);
                });
                Thread.sleep(DELAY);
            } else {
                Platform.runLater(() -> {
                    destacarLinha(19);
                });
                Thread.sleep(DELAY);

                aux[finalK[0]] = vet[finalJ[0]];
                final int auxK = finalK[0];
                final int auxVal = aux[finalK[0]];
                Platform.runLater(() -> {
                    destacarLinha(20);
                    atualizaVetorPorPos("aux", auxK, auxVal);
                    animacaoAuxUsoDaPosicaoDoVetor("aux", auxK);
                });
                Thread.sleep(DELAY);
                finalJ[0]++;
                finalK[0]++;
                Platform.runLater(() -> {
                    criaAtualizaVariavel("j", finalJ[0]);
                    criaAtualizaVariavel("k", finalK[0]);
                });
                Thread.sleep(DELAY);
            }
        }

        while (finalI[0] <= finalFim1) { // Corrected condition
            final int currentI = finalI[0];
            Platform.runLater(() -> {
                destacarLinha(22);
                animacaoAuxUsoDaPosicaoDoVetor("vet", currentI);
            });
            Thread.sleep(DELAY);

            aux[finalK[0]] = vet[finalI[0]];
            final int auxK = finalK[0];
            final int auxVal = aux[finalK[0]];
            Platform.runLater(() -> {
                destacarLinha(23);
                atualizaVetorPorPos("aux", auxK, auxVal);
                animacaoAuxUsoDaPosicaoDoVetor("aux", auxK);
            });
            Thread.sleep(DELAY);
            finalI[0]++;
            finalK[0]++;
            Platform.runLater(() -> {
                criaAtualizaVariavel("i", finalI[0]);
                criaAtualizaVariavel("k", finalK[0]);
            });
            Thread.sleep(DELAY);
        }

        while (finalJ[0] <= finalFim2) { // Corrected condition
            final int currentJ = finalJ[0];
            Platform.runLater(() -> {
                destacarLinha(24);
                animacaoAuxUsoDaPosicaoDoVetor("vet", currentJ);
            });
            Thread.sleep(DELAY);

            aux[finalK[0]] = vet[finalJ[0]];
            final int auxK = finalK[0];
            final int auxVal = aux[finalK[0]];
            Platform.runLater(() -> {
                destacarLinha(25);
                atualizaVetorPorPos("aux", auxK, auxVal);
                animacaoAuxUsoDaPosicaoDoVetor("aux", auxK);
            });
            Thread.sleep(DELAY);
            finalJ[0]++;
            finalK[0]++;
            Platform.runLater(() -> {
                criaAtualizaVariavel("j", finalJ[0]);
                criaAtualizaVariavel("k", finalK[0]);
            });
            Thread.sleep(DELAY);
        }

        final int finalKValue = finalK[0];
        Platform.runLater(() -> {
            destacarLinha(27);
            criaAtualizaVariavel("i", 0);
        });
        Thread.sleep(DELAY);

        for (int x = 0; x < finalKValue; x++) {
            final int currentX = x;
            final int currentAuxVal = aux[currentX];
            Platform.runLater(() -> {
                destacarLinha(28);
                animacaoAuxUsoDaPosicaoDoVetor("aux", currentX);
                atualizaVetorPorPos("vet", finalInicio1 + currentX, currentAuxVal);
                animacaoAuxUsoDaPosicaoDoVetor("vet", finalInicio1 + currentX);
            });
            Thread.sleep(DELAY);
            vet[finalInicio1 + x] = aux[x];
            Platform.runLater(() -> {
                destacarLinha(27);
                criaAtualizaVariavel("i", currentX + 1);
            });
            Thread.sleep(DELAY);
        }
        Platform.runLater(() -> {
            limparTodosDestaques();
            deletarVariavel("i");
            deletarVariavel("j");
            deletarVariavel("k");
            deletarVariavel("inicio1");
            deletarVariavel("fim1");
            deletarVariavel("inicio2");
            deletarVariavel("fim2");
        });
        Thread.sleep(DELAY);
    }

    private void configurarBotoesFinal() {
        buttonsContainer = new HBox(20);
        voltarTelaInicial = new Button("Voltar");
        voltarTelaInicial.setFont(Font.font("Manrope", 16));
        voltarTelaInicial.setStyle("-fx-background-color: #444444; -fx-border-width: 1px; -fx-border-color: white; -fx-border-style: solid; -fx-text-fill: white;");
        voltarTelaInicial.setOnAction(e -> voltarParaTelaInicial());
        buttonsContainer.getChildren().add(voltarTelaInicial);
        refazerMergeBtn = new Button("Refazer");
        refazerMergeBtn.setFont(Font.font("Manrope", 16));
        refazerMergeBtn.setStyle("-fx-background-color: #444444; -fx-border-width: 1px; -fx-border-color: white; -fx-border-style: solid; -fx-text-fill: white;");
        refazerMergeBtn.setOnAction(e -> refazerMerge());
        buttonsContainer.getChildren().add(refazerMergeBtn);
        buttonsContainer.setAlignment(Pos.CENTER);
        bottomContainer.getChildren().add(buttonsContainer);
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
        initMergeInterno = null;
        voltarTelaInicial = null;
        refazerMergeBtn = null;
        vectorLabelsContainer = null;
        buttonsContainer = null;
        configurarLayoutInicial();
    }

    public void refazerMerge() {
        for (int i = 0; i < 31; i++) { // Adjust line count as needed
            removerLinhaCodigo(i);
        }
        removerVetorDaTela("vet");
        removerVetorDaTela("aux");
        bottomContainer.getChildren().remove(buttonsContainer);
        criarVetorNaTela("vet", vet);
        mergeSort();
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
        Button initMerge = new Button("Merge Sort");
        initMerge.setFont(Font.font("Manrope", 16));
        initMerge.setOnAction(e -> configurarMerge());
        initMerge.setMinWidth(150);
        initMerge.setStyle(
                "-fx-background-color: #444444; -fx-border-width: 1px; -fx-border-color: white; -fx-border-style: solid; -fx-border-radius: 0; -fx-text-fill: white;");
        StackPane centerPane = new StackPane(container);
        centerPane.setAlignment(Pos.CENTER);
        container.getChildren().addAll(initMerge);
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

            // Ajusta altura de todos os vetores dinamicamente
            int numVetores = containerVetores.getChildren().size();
            double heightRatio = Math.max(0.15, 0.45 / Math.max(1, numVetores));

            // Aplica a nova altura a todos os vetores existentes
            for (int i = 0; i < containerVetores.getChildren().size(); i++) {
                HBox existingContainer = (HBox) containerVetores.getChildren().get(i);
                existingContainer.prefHeightProperty().unbind();
                existingContainer.prefHeightProperty().bind(containerVetores.heightProperty().multiply(heightRatio));
            }

            // Ajusta altura de todos os labels também
            for (int i = 0; i < vectorLabelsContainer.getChildren().size(); i++) {
                Label existingLabel = (Label) vectorLabelsContainer.getChildren().get(i);
                existingLabel.prefHeightProperty().unbind();
                existingLabel.prefHeightProperty().bind(vectorLabelsContainer.heightProperty().multiply(heightRatio));
            }
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

    private void configurarMerge() {
        cleanRoot();
        vet = new int[8]; // TL = 8 as per user's class
        tl = 8;
        configurarLayout();
        configurarCodigo();
        configurarVetores();
        configurarStartBtn();
    }

    public void configurarStartBtn() {
        initMergeInterno = new Button("Iniciar");
        initMergeInterno.setFont(Font.font("Manrope", 16));
        initMergeInterno.setStyle(
                "-fx-background-color: #444444; -fx-border-width: 1px; -fx-border-color: white; -fx-border-style: solid; -fx-border-radius: 0; -fx-text-fill: white;");
        initMergeInterno.setOnAction(e -> mergeSort());
        bottomContainer.getChildren().add(initMergeInterno);
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

    private void animarTrocaPosVet(String vectorName, int pos1, int pos2) {
        HBox boxesContainer = vectorMap.get(vectorName);
        if (boxesContainer != null && pos1 >= 0 && pos1 < boxesContainer.getChildren().size()
                && pos2 >= 0 && pos2 < boxesContainer.getChildren().size()) {

            StackPane box1 = (StackPane) boxesContainer.getChildren().get(pos1);
            StackPane box2 = (StackPane) boxesContainer.getChildren().get(pos2);
            double distanciaX = box2.getBoundsInParent().getMinX() - box1.getBoundsInParent().getMinX();
            TranslateTransition subirBox1 = new TranslateTransition(Duration.millis(600), box1);
            subirBox1.setByY(-60);

            TranslateTransition moverHorizontalBox1 = new TranslateTransition(Duration.millis(800), box1);
            moverHorizontalBox1.setByX(distanciaX);

            TranslateTransition descerBox1 = new TranslateTransition(Duration.millis(600), box1);
            descerBox1.setByY(60);

            TranslateTransition descerBox2 = new TranslateTransition(Duration.millis(600), box2);
            descerBox2.setByY(60);

            TranslateTransition moverHorizontalBox2 = new TranslateTransition(Duration.millis(800), box2);
            moverHorizontalBox2.setByX(-distanciaX);

            TranslateTransition subirBox2 = new TranslateTransition(Duration.millis(600), box2);
            subirBox2.setByY(-60);

            SequentialTransition seqBox1 = new SequentialTransition(subirBox1, moverHorizontalBox1, descerBox1);
            SequentialTransition seqBox2 = new SequentialTransition(descerBox2, moverHorizontalBox2, subirBox2);
            ParallelTransition animacaoParalela = new ParallelTransition(seqBox1, seqBox2);
            animacaoParalela.setOnFinished(e -> {
                box1.setTranslateX(0);
                box1.setTranslateY(0);
                box2.setTranslateX(0);
                box2.setTranslateY(0);
                boxesContainer.getChildren().remove(box1);
                boxesContainer.getChildren().remove(box2);
                boxesContainer.getChildren().add(pos2, box1);
                boxesContainer.getChildren().add(pos1, box2);
            });
            animacaoParalela.play();
        }
    }
}
