module com.pesquisaordenacao.animacaosort {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.pesquisaordenacao.animacaosort to javafx.fxml, javafx.graphics;
    exports com.pesquisaordenacao.animacaosort;
    provides javafx.application.Application with com.pesquisaordenacao.animacaosort.SortingAnimationApp,
                                                com.pesquisaordenacao.animacaosort.MergeSortAnimation,
                                                com.pesquisaordenacao.animacaosort.QuickSortAnimation;
    }