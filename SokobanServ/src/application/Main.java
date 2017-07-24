package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import model.MyModel;
import server.MyServer;
import server.RegularClientHandler;
import server.ViewModel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			    FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
			  // loader.setController(this);
			    
				BorderPane root = (BorderPane)loader.load();
				AdminController view = loader.getController();
				Scene scene = new Scene(root,500,500);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				//closing from the X button
				primaryStage.setOnCloseRequest(e ->
				{
					e.consume();
					//Alert alert = new Alert(AlertType.CONFIRMATION);
					//alert.setTitle("Confirmation Dialog");
					//alert.setHeaderText(null);
					//alert.setContentText("Are you sure you want to exit?");

					//Optional<ButtonType> result = alert.showAndWait();
					//if (result.get() == ButtonType.OK)
						view.exit();
				});
				
				//binding the view, controller and model
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						init(view);
						
					}
				}).start();
				
			
				primaryStage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		public void init(AdminController view)
		{
			
			
			MyModel model = new MyModel();
			ViewModel viewmodel = new ViewModel(model);
			
			model.addObserver(viewmodel);
			
			viewmodel.addObserver(view);
			
			view.setVm(viewmodel);
			
			
			RegularClientHandler r = new RegularClientHandler();
			MyServer s = new MyServer(2679, r);
			
			viewmodel.setS(s);
			r.addObserver(viewmodel);
			s.addObserver(viewmodel);
			
			
			try {
				s.runServer();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	/*
	 * FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
			BorderPane root = (BorderPane)loader.load();
			AdminController view = loader.getController();
			Scene scene = new Scene(root,500,500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			//closing from the X button
			primaryStage.setOnCloseRequest(e ->
			{
				e.consume();
				//Alert alert = new Alert(AlertType.CONFIRMATION);
				//alert.setTitle("Confirmation Dialog");
				//alert.setHeaderText(null);
				//alert.setContentText("Are you sure you want to exit?");

				//Optional<ButtonType> result = alert.showAndWait();
				//if (result.get() == ButtonType.OK)
					view.exit();
			});
			
			//binding the view, controller and model
			init(view);
		
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void init(AdminController view)
	{
		MyModel model = new MyModel();
		ViewModel controller = new ViewModel(model);
		
		model.addObserver(controller);
		controller.addObserver(view);
	}
	*/
	 
}
