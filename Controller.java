//Rishabh Kapoor 
//Mina Barsoum
//Sesh Venugopal
//Software Methodology

package songLib;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Controller {
	
	  @FXML AnchorPane root;
	  @FXML Button add;
	  @FXML Button edit;
	  @FXML Button delete;
	  @FXML Button submit;
	  @FXML TextArea addEditDetailArea;
	  @FXML Label addEditDetailLabel;
	  @FXML TextField nameTextField;
	  @FXML TextField artistTextField;
	  @FXML TextField albumTextField;
	  @FXML TextField yearTextField;
	  @FXML Label songArtistListLabel;
	  @FXML Label songDetailLabel;
	  @FXML Label nameLabel;
	  @FXML Label artistLabel;
	  @FXML Label albumLabel;
	  @FXML Label yearLabel;
	  @FXML Label userLabel;
	  @FXML ListView<String> list = new ListView<String>();
	  private ObservableList<Song> obsList=FXCollections.observableArrayList();
	 
	  
	  public void start(Stage mainStage)
	  {
		   userLabel.setText("Enter song info below to add, or click on a song above to edit or delete!");
		   ArrayList <Song> songs = new ArrayList<Song>();
		   BufferedReader br;
		   boolean noFile=false;
		   Path filePath = Paths.get("src/saved.txt");
		   try 
		   {
				if (!new File("src/saved.txt").exists())
				{
				   obsList=FXCollections.observableArrayList();
				   noFile=true;
				}
				else
			   {
			   br = Files.newBufferedReader(filePath);
			   String text = br.readLine();
				
			   while (text != null) 
			   { 
				   String name = text;
				   String artist = br.readLine();
				   String album = br.readLine();
				   String year = br.readLine();  
				   Song s = new Song(name, artist, album, Integer.parseInt(year));
				   songs.add(s);   
				   text = br.readLine();
			   } 
			   }
				
		   } 
		   catch (IOException e) 
		   {
			   e.printStackTrace();
		   }
		   if(!noFile)
		   {
		   Collections.sort(songs, new SongComparator());
		   obsList=FXCollections.observableArrayList(songs);
		   }
		   for(Song s: obsList)
		   {
			   list.getItems().add(s.getName()+"-- by "+s.getArtist());
		   }
		   
		   if(!noFile)
		   {
			   if(!obsList.isEmpty())
			   {
			   list.getSelectionModel().select(0);
			   Song temp=obsList.get(0);
			   nameLabel.setText(temp.getName());
				artistLabel.setText(temp.getArtist());
				albumLabel.setText(temp.getAlbum());
				yearLabel.setText(Integer.toString(temp.getYear()));
			   }
			   
		   }
		  
		 mainStage.setOnCloseRequest(event->
		  {
		BufferedWriter writer;
	  	try {
	  			writer = new BufferedWriter(new FileWriter("src/saved.txt"));
				for(Song s: obsList)
		    	  {
		    		  writer.write(s.getName()+"\n");
		    		  writer.write(s.getArtist()+"\n");
		    		  writer.write(s.getAlbum()+"\n");
		    		  writer.write(s.getYear()+"\n"); 
		    	  }
		    	 writer.close(); 
	  		} 		
	  	catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
			}
		  });
	  }
		
	  public void handleList(MouseEvent e) {
		  int i=list.getSelectionModel().getSelectedIndex();
		  Song s= obsList.get(i);
		  nameLabel.setText(s.getName());
		  artistLabel.setText(s.getArtist());
		  albumLabel.setText(s.getAlbum());
		  yearLabel.setText(Integer.toString(s.getYear()));
		  }

		public void add(ActionEvent e) {
			
			Song temp;
			int i = list.getSelectionModel().getSelectedIndex();
			
			String error=investigate(nameTextField.getText(), artistTextField.getText(),
					   albumTextField.getText(), yearTextField.getText());
			if(error!=null)
			{
				   Alert alert = new Alert(AlertType.INFORMATION);
				   alert.setTitle("Error");
				   alert.setHeaderText("Error");
				   alert.setContentText(error);
				   alert.showAndWait();
				   return;
			}
			else
			{
			Song s =new Song(nameTextField.getText(), artistTextField.getText(), albumTextField.getText(), Integer.parseInt(yearTextField.getText()));
			temp=s;
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Add Item");
			alert.setHeaderText("Add Item");
			alert.setContentText("Are you sure you want to add " + s.getName() + "?" +"\n All songs added will be saved between sessions!");
			Optional<ButtonType> result = alert.showAndWait();
			if(result.isPresent())
			{
			obsList.add(s);
			list.getItems().add(s.getName()+" -- by "+s.getArtist());
		    FXCollections.sort(obsList, new SongComparator());
			if (obsList.size() == 1) {
				   list.getSelectionModel().select(0);
			   }
			   else
			   {
				   list.getItems().clear();
				   i = 0;
				   for(Song song: obsList)
				   {
					   System.out.println(obsList.get(i).getName());
					   list.getItems().add(song.getName()+" -- by "+song.getArtist());
					   if(song == s)
					   {
						  list.getSelectionModel().select(i);
					   }
					   i++;
				   }
			   }
			}
			//Song s = list.getSelectionModel().getSelectedItem();
			
			nameTextField.setText("");
			artistTextField.setText("");
			albumTextField.setText("");
			yearTextField.setText("");
			
			nameLabel.setText(temp.getName());
			artistLabel.setText(temp.getArtist());
			albumLabel.setText(temp.getAlbum());
			yearLabel.setText(Integer.toString(temp.getYear()));
			}
	 	}
		
		
		public void edit(ActionEvent e) {
			
			if (obsList.isEmpty()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				   alert.setTitle("Error");
				   alert.setHeaderText("Error");
				   alert.setContentText("There are no songs in the library!");
				   alert.showAndWait();
				   return;
			   }
			
			userLabel.setText("Edit information below then click submit!");
			
			int i=list.getSelectionModel().getSelectedIndex();
			Song temp=obsList.get(i);
			
			nameTextField.setText(temp.getName());
			artistTextField.setText(temp.getArtist());
			albumTextField.setText(temp.getAlbum());
			yearTextField.setText(Integer.toString(temp.getYear()));	
			
			edit.setDisable(true);
			submit.setDisable(false);
		}
		
		public void handleSubmit(ActionEvent e)
		{
			int i=list.getSelectionModel().getSelectedIndex();
			Song temp=obsList.get(i);
			String error = investigate(nameTextField.getText(), artistTextField.getText(),
					   albumTextField.getText(), yearTextField.getText());
			if(error!=null && !(error.equals("This song already exists!") && temp.getName().equals(nameTextField.getText()) && temp.getArtist().equals(artistTextField.getText())))
			{
			   Alert alert = new Alert(AlertType.INFORMATION);
			   alert.setTitle("Error");
			   alert.setHeaderText("Error");
			   alert.setContentText(error);
			   alert.showAndWait();
			   return;
			}
			else
			{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Edit Item");
			alert.setHeaderText("Edit Item");
			alert.setContentText("Are you sure you want to edit " + obsList.get(i).getName() + "?");
			Optional<ButtonType> result = alert.showAndWait();
			if(result.isPresent())
			{
			obsList.get(i).setName(nameTextField.getText().trim());
			obsList.get(i).setArtist(artistTextField.getText().trim());
			obsList.get(i).setAlbum(albumTextField.getText().trim());
			obsList.get(i).setYear(Integer.parseInt(yearTextField.getText().trim()));
			
			FXCollections.sort(obsList, new SongComparator());
			if (obsList.size() == 1) {
				   list.getSelectionModel().select(0);
			   }
			   else
			   {
				   list.getItems().clear();
				   i = 0;
				   for(Song song: obsList)
				   {
					   System.out.println(obsList.get(i).getName());
					   list.getItems().add(song.getName()+" -- by "+song.getArtist());
					   if(song == temp)
					   {
						  list.getSelectionModel().select(i);
					   }
					   i++;
				   }
			   }
			//Song s = list.getSelectionModel().getSelectedItem();
			
			nameLabel.setText(temp.getName());
			artistLabel.setText(temp.getArtist());
			albumLabel.setText(temp.getAlbum());
			yearLabel.setText(Integer.toString(temp.getYear()));
			}
			}
			nameTextField.setText("");
			artistTextField.setText("");
			albumTextField.setText("");
			yearTextField.setText("");
			userLabel.setText("Enter song info below to add, or click on a song above to edit or delete!");
			edit.setDisable(false);
			submit.setDisable(true);
		}
		
		public void delete(ActionEvent e) {
			
			if (obsList.isEmpty()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				   alert.setTitle("Error");
				   alert.setHeaderText("Error");
				   alert.setContentText("There are no songs in the library!");
				   alert.showAndWait();
				   return;
			   }

			int i = list.getSelectionModel().getSelectedIndex();
			Song song = obsList.get(i);
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Delete Item");
			alert.setHeaderText("Delete Item");
			alert.setContentText("Are you sure you want to delete " + song.getName() + "?" +"\n This song will not be saved for future sessions!");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.isPresent()) {
				   obsList.remove(song);
				   list.getItems().remove(i);
				   if (obsList.isEmpty()) {
					   nameLabel.setText("");
					   artistLabel.setText("");
					   albumLabel.setText("");
					   yearLabel.setText("");
					   return;
				   }
				   else if(i == obsList.size())
				   {
					  i--;
					   list.getSelectionModel().select(i);
				   }
			}
			Song s = obsList.get(i);
			nameLabel.setText(s.getName());
			artistLabel.setText(s.getArtist());
			albumLabel.setText(s.getAlbum());
			yearLabel.setText(Integer.toString(s.getYear()));
			
		}
		
		
		public String investigate(String name, String artist, String album, String year) 
		{
			   if (name.trim().isEmpty())
			   {
				   return "Name not specified!";
			   }
			   else if (artist.trim().isEmpty())
			   {
				   return "Artist not specified!";
			   }
			   else if (!year.trim().isEmpty()) 
			   {
				  if (!year.trim().matches("[0-9]+"))
				  {
					   return "Year must contain numbers only";
				  }
			      else if (year.trim().length() != 4)
			      {
					   return "Year must be 4 digits";
			      }
			   }
			   for (Song song: obsList) 
				   { 
					   if (song.getName().toLowerCase().equals(name.trim().toLowerCase()) &&
							   song.getArtist().toLowerCase().equals(artist.trim().toLowerCase()))
					   {
						  return "This song already exists!";
					   }   
				   }
			   return null;
		   }
	}