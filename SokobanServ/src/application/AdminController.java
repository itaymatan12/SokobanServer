package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import adminModel.Task;
import adminModel.client;
import entities.User;
import entities.UserRecord;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import server.ViewModel;

/**
 * The AdminController is very similar to the MainWindowController in the client
 * this class is responsible on the Administrator window that from this window we can see all that going in the server
 * and controls it like we can to disconnect clients and change the maximum clients capacity and more
 *
 */

public class AdminController  implements View , Observer ,Initializable {

	private ViewModel vm ;
	private ObservableList<client> data;
	private client myUser;
	int flag =0;
	private Stage stageTasksWindow;

	@FXML
	private Label numofclients;

	@FXML
	private TableColumn<client, String> ip;
	
	@FXML
	private TableColumn<client,Integer> id;

	@FXML
	private TableView<client> table;
	
	@FXML
	TasksWindowController Tasks;
	
	public AdminController() {
		
		table = new TableView<client>();
		ip = new TableColumn<client, String>();	
		id = new TableColumn<client, Integer>();	
		Tasks = new TasksWindowController();
		stageTasksWindow = null;
		
		try
		{
			
			FXMLLoader fxmlLoader5 = new FXMLLoader(getClass().getResource("Tasks.fxml"));
			fxmlLoader5.setController(Tasks);
			Parent root2 = (Parent) fxmlLoader5.load();
			stageTasksWindow = new Stage();
			stageTasksWindow.initStyle(StageStyle.UTILITY);
			stageTasksWindow.setResizable(false);
			stageTasksWindow.setScene(new Scene(root2));
			
		} 
		
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public AdminController(ViewModel vm) 
	{
		this.vm=vm;
		table = new TableView<client>();
		ip = new TableColumn<client, String>();	
		id = new TableColumn<client, Integer>();
		stageTasksWindow = null;
		
		try
		{
			
			FXMLLoader fxmlLoader5 = new FXMLLoader(getClass().getResource("Tasks.fxml"));
			fxmlLoader5.setController(Tasks);
			Parent root2 = (Parent) fxmlLoader5.load();
			stageTasksWindow = new Stage();
			stageTasksWindow.initStyle(StageStyle.UTILITY);
			stageTasksWindow.setResizable(false);
			stageTasksWindow.setScene(new Scene(root2));
			
		} 
		
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	
	@Override
	public void displayMessage(String string) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setcontrols(String up, String down, String left, String right) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPlayersList(List<User> p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLevelScores(List<UserRecord> p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setUserScores(List<UserRecord> p) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable o, Object arg) 
	{
		if(o instanceof ViewModel)
		{
			if(((ViewModel) o).getFlag() == 1)
			{
				setclientList((List<client>)arg);
			}
			
			else if(((ViewModel) o).getFlag() == 2)
			{
				setTaskstasks((List<Task>)arg);
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		
		ip.setCellValueFactory(new PropertyValueFactory<client,String>("ip"));
		id.setCellValueFactory(new PropertyValueFactory<client,Integer>("id"));
		// OnMouseClick - when use click on row
		table.setOnMousePressed(new EventHandler<javafx.scene.input.MouseEvent>()
		{
			@Override
			public void handle(javafx.scene.input.MouseEvent event)
			{
				myUser = table.getSelectionModel().getSelectedItem();

				if(myUser != null)
				{	

						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Disconnect user");
						alert.setHeaderText(null);
						alert.setContentText("Do you want to disconnect " +myUser.getIp() + "?");

						Optional<ButtonType> result = alert.showAndWait();
						
						if (result.get() == ButtonType.OK)
						{
							vm.disconnectSpecificClient(myUser.getIp());
						}
				}
			}
		});
		
	}
	
	public void setclientList(List<client> clients)
	{
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
				System.out.println(clients);
				table.setEditable(true);
				data = FXCollections.observableList(clients);
				
				table.setItems(data);
			//	table.getColumns().clear();
			//	table.getColumns().add(ip);
				//table.getColumns().add(id);
		    }
		});
		

	}
	
	public void setTaskstasks(List<Task> tasks)
	{
	//	System.out.println(tasks.toString());
		
		Tasks.setTasksList(tasks);
	}

	public void startserver()
	{
		if(flag!=0)
		{
			vm.startserver();
			flag=0;
		}
			
	}
	
	public void stopserver()
	{
		if(flag!=1)
		{
			vm.stopserver();
			flag=1;
		}
	}
	
	public void changeServerCapcity()
	{
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Change Client Capcities");
		dialog.setHeaderText(null);
		dialog.setContentText("Enter the new capcity number:");
		dialog.initStyle(StageStyle.UTILITY);

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent())
		{	
			numofclients.setText(result.toString() + " - max clients");
			vm.changeclientcapacity(Integer.parseInt(result.get()));
		}
		
	}
	
	public void showTasks()
	{

		if(stageTasksWindow != null)
			stageTasksWindow.show();
	}


	public ViewModel getVm() {
		return vm;
	}

	public void setVm(ViewModel vm) {
		this.vm = vm;
	}

	
}