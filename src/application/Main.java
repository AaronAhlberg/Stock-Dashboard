package application;
	
import java.io.IOException;

import Services.PoolingJsonRequestService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	private Stage primaryStage;
	private BorderPane mainLayout;

	@Override
	public void start(Stage primaryStage) throws IOException {
			
		
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("TESTER");
			showMainView();	
		
	}
	
	private void showMainView() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("Home.fxml"));
		mainLayout = loader.load();
		Scene scene = new Scene(mainLayout);
		scene.getStylesheets().addAll(getClass().getResource("application.css").toExternalForm(),
				getClass().getResource("financialsTableCss.css").toExternalForm());
		primaryStage.setScene(scene);
	
	
		primaryStage.show();
	
		}
	
	public static void main(String[] args) {
		 
		launch(args);
	}
}
