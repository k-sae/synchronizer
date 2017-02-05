package Synchronizer;

import Synchronizer.AppConnections.Server.MainServerConnection;
import Synchronizer.AppConnections.Server.ServerMetaData;
import Synchronizer.AppConnections.Server.ServersFinder;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        MainServerConnection mainServerConnection = new MainServerConnection();
        mainServerConnection.startServer();
        new ServersFinder() {
            @Override
            public void onFinish(ArrayList<ServerMetaData> serversMetaData) {

            }
        }.start();
    }
    @Override
    public void stop() throws Exception {
        super.stop();
        Platform.exit();
        //this will close all active threads with error code of 0
        System.exit(0);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
