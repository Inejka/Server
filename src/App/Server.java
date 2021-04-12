package App;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Server.ServerThread;


public class Server extends Application {

    ServerThread serverThread;
    TabPane tabPane;

    public void beg() {
        launch();
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        serverThread = new ServerThread(this::createTab);
        primaryStage.setOnCloseRequest(e -> serverThread.stop());
        VBox root = new VBox();
        Scene scene = new Scene(root, 700, 500);
        buttonsInit(root);
        paneInit(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Tab createTab(String name) {
        Tab toReturn = new Tab(name);
        tabPane.getTabs().add(toReturn);
        TextArea textArea = new TextArea("Socket started");
        toReturn.setContent(textArea);
        textArea.setEditable(false);
        return toReturn;
    }

    private void paneInit(VBox root) {
        tabPane = new TabPane();
        root.getChildren().add(tabPane);
        tabPane.prefWidthProperty().bind(root.widthProperty());
        tabPane.prefHeightProperty().bind(root.heightProperty());
        var a = createTab("127.0.0.1");
    }

    private void buttonsInit(VBox root) {
        Button start = new Button("start");
        start.setOnAction(e -> serverThread.enable());
        Button stop = new Button("stop");
        stop.setOnAction(e -> serverThread.disable());
        start.prefWidthProperty().bind(root.widthProperty());
        stop.prefWidthProperty().bind(root.widthProperty());
        root.getChildren().addAll(start, stop);
    }
}
