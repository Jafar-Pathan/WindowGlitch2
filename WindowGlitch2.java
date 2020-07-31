
/**

 * simulates window glitch 
 
 * @author Jafar Pathan (https://github.com/Jafar-Pathan)
 * @version 2.0
 * @copyright Feel free to change it and use it anywhere. Just provide a link for github.
 * <b>!! WARNING :- DON'T OVER DO IT, BECAUSE IT CREATES NEW STAGE OBJECT EACH TIME WHEN PRIMARY STAGE IS DRAGGED. DRAGGING IT FOR TOO LONG WILL PUT IMPACT ON MEMORY. BE CAUTION.
 * 					TO CLOSE APPLICATION. CLOSE IT FROM WINDOWS TASKBAR OR CLOSE IT FROM CLOSE BUTTON PROVIDED IN CONTROL WINDOW. !!</b>
 * 
 * Classes      :- WindowGlitch2 which extends Application 
 * 
 * Methods used :- public void handleMousePressed(MouseEvenet e) - to get and store the mouse click point 
 * 				   public void handleMouseDragged(MouseEvenet e) - to move the stage to the location where towarads mouse is dragged
 * 				   public void createNew(double x, double y, int w, int h) - to create new stage with specified parameters 
 * 				   public void createNewWithColor(double x, double y, int w, int h) - to create new stage with random scene color
 * 				   public void handleColorMousePresse(MouseEvent e) - to get and store the mouse click point from colored stage
 * 				   public void handleColorMouseDragged(MouseEvent e) - to move to colored stage to the location where towards mouse is dragged
 * 				   public Color randomColor() - returns random Color object from given list
 * 
 * 

*/


import javafx.application.Application;
import javafx.application.Platform;

import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.Modality;
import javafx.stage.Screen;

import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import javafx.geometry.Rectangle2D;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class WindowGlitch2 extends Application{
		
	private Stage glitchStage; //used to store reference for current stage
	private double dragOffsetX; // used to store x point where mouse is dragged
	private double dragOffsetY; // used to stroe y point where mouse is dragged
	private Button createButton, closeButton, createNewWithColors;
	private static TextField heightTextField, widthTextField;
	
	public static void main(String args[]){
		Application.launch(args);
	}

	@Override
	public void start(Stage controlStage){
		createButton = new Button("Create New");
		closeButton = new Button("Close");
		createNewWithColors = new Button("Create new with colors");
		heightTextField = new TextField();
		widthTextField = new TextField();
		widthTextField.setText("300");
		heightTextField.setText("200");

		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds(); // get the screenbounds to display stage in center later
		createNew((screenBounds.getWidth() - 300)/2, (screenBounds.getHeight() - 200)/2,300, 200); // create a stage at the center of display

		createNewWithColors.setOnAction(e -> createNewWithColor((screenBounds.getWidth() - 300)/2, (screenBounds.getHeight() - 200)/2, Integer.parseInt(widthTextField.getText().toString()), Integer.parseInt(heightTextField.getText().toString())));
		closeButton.setOnAction(e -> Platform.exit());
		createButton.setOnAction(e -> createNew((screenBounds.getWidth() - 300)/2, (screenBounds.getHeight() - 200)/2,Integer.parseInt(widthTextField.getText().toString()),Integer.parseInt(heightTextField.getText().toString())));
		
		VBox root = new VBox();
		root.getChildren().addAll(widthTextField, heightTextField, createButton, createNewWithColors, closeButton);
		Scene controlScene = new Scene(root, 300, 200);
		controlStage.setScene(controlScene);
		controlStage.setX(0.0);
		controlStage.setY(0.0);// set the position of control stage to the top roght corner
		controlStage.show();
		
	}

	public void handleMousePressed(MouseEvent e){
		this.dragOffsetX = e.getScreenX() - glitchStage.getX();
		this.dragOffsetY = e.getScreenY() - glitchStage.getY(); // store the x and y mouse click points		
	}

	public void handleMouseDragged(MouseEvent e){
		glitchStage.setX(e.getScreenX() - this.dragOffsetX);
		glitchStage.setY(e.getScreenY() - this.dragOffsetY); // move the current stage towards mouse dragged point
		
		createNew(glitchStage.getX(), glitchStage.getY(), Integer.parseInt(widthTextField.getText().toString()), Integer.parseInt(heightTextField.getText().toString())); // create new stage at mouse dragged points
	}

	public void createNew(double x, double y,int w, int h){	
		Scene glitchScene = new Scene(new Group(new Label("Drag")), w, h);
		glitchScene.setOnMousePressed(e -> handleMousePressed(e));
		glitchScene.setOnMouseDragged(e -> handleMouseDragged(e)); // create a scene for new stage and set mouse click and drag listeners for it
		
		this.glitchStage = new Stage();
		glitchStage.setScene(glitchScene);
		glitchStage.setX(x);
		glitchStage.setY(y);
		glitchStage.setTitle("Window Glitch");
		glitchStage.show();
	}

	public void createNewWithColor(double x, double y, int w, int h){  // same working principle as createNew() method just it has color and implemented for handlig colored stage separately
		Scene glitchScene = new Scene(new Group(new Label("Drag")), w, h);
		glitchScene.setOnMousePressed(e -> handleColorMousePressed(e));
		glitchScene.setOnMouseDragged(e -> handleColorMouseDragged(e));
		glitchScene.setFill(randomColor()); //  fill background of scene with random Color object
			
		this.glitchStage = new Stage();	
		glitchStage.setX(x);
		glitchStage.setY(y);
		glitchStage.setScene(glitchScene);
		glitchStage.setTitle("Window Glitch");
		glitchStage.show();
	}

  	public void handleColorMousePressed(MouseEvent e){
		this.dragOffsetX = e.getScreenX() - glitchStage.getX();
		this.dragOffsetY = e.getScreenY() - glitchStage.getY();
	}

	public void handleColorMouseDragged(MouseEvent e){
		glitchStage.setX(e.getScreenX() - this.dragOffsetX);
		glitchStage.setY(e.getScreenY() - this.dragOffsetY);
		
		createNewWithColor(glitchStage.getX(), glitchStage.getY(), Integer.parseInt(widthTextField.getText().toString()), Integer.parseInt(heightTextField.getText().toString()));
	}

	public Color randomColor(){
		List<Color> colorlist = new ArrayList<>();
		colorlist.add(Color.BLACK);
		colorlist.add(Color.WHITE);
		colorlist.add(Color.RED);
		colorlist.add(Color.BLUE);
		colorlist.add(Color.GREEN);
		colorlist.add(Color.PURPLE);
		colorlist.add(Color.CYAN);
		colorlist.add(Color.ORANGE);
		Random random = new Random();
		return colorlist.get(random.nextInt(colorlist.size())); // return random Color object
	}

}