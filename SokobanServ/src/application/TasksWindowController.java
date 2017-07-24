package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import adminModel.Task;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.Initializable;

/**
 * Class that controls the tasks window that show us all the tasks that running now and the tasks that already finished
 */


public class TasksWindowController implements Initializable {
	
	private ObservableList<Task> data;
	
	private Task myTask;
	 

	@FXML
	private TableColumn<Task, String> name;
	
	@FXML
	private TableColumn<Task, String> state;

	@FXML
	private TableView<Task> table;
	
	public TasksWindowController() 
	{
		table = new TableView<Task>();
		name = new TableColumn<Task, String>();	
		state = new TableColumn<Task, String>();	
		data = FXCollections.observableArrayList();
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		name.setCellValueFactory(new PropertyValueFactory<Task,String>("name"));
		state.setCellValueFactory(new PropertyValueFactory<Task,String>("state"));
		// OnMouseClick - when use click on row
		table.setOnMousePressed(new EventHandler<javafx.scene.input.MouseEvent>()
		{
			@Override
			public void handle(javafx.scene.input.MouseEvent event)
			{
				myTask = table.getSelectionModel().getSelectedItem();

				if(myTask != null)
				{	

						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Task");
						alert.setHeaderText(null);
						alert.setContentText("This is task  " +myTask.getName() + "!");
						alert.showAndWait();
						
				}
			}
		});
		
		
	}

	
	public void setTasksList(List<Task> tasks)
	{

		Platform.runLater(new Runnable() {
		    @Override
		    public void run() 
		    {
		    	try
		    	{
					table.setEditable(true);

					
					for (Task t :tasks)
					{
						System.out.println(t.getName());
						System.out.println(t.getState());
						//data.add(t);
					}
					
					//data = FXCollections.observableArrayList(new Task("fdfd","Dddd"));
					data =FXCollections.observableList(tasks);
					table.setItems(data);
					//data.clear();
					
		    	}
		    	
		    	catch(Exception e)
		    	{
		    		
		    	}

				
		    }
		});
		

	}
}
