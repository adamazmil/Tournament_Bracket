package application;
import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Match {
    private VBox container;
    private Button button;
    private TextField text1;
    private TextField text2;
    
    Team team1;
    Team team2;
    
    private Label team1Label;
    private Label team2Label;
    
    public Match(Team team1, Team team2, int round, int gameIndex, BracketProcessor bracket) {
        
    	this.team1 = team1;
        this.team2 = team2;
    	
    	
    	//creates vbox as outer container for match, with padding
        container = new VBox();
        container.setPadding(new Insets(0, 0, 50, 0));
        
        //hold button in hbox for padding
        HBox submitBox = new HBox();
        submitBox.setPadding(new Insets(0, 0, 0, 50));
        button = new Button("Submit Game Score");
        submitBox.getChildren().addAll(button);
        
        //makes first hbox with first team name and score field
        HBox teamField1 = new HBox();
        String name1 = (team1 == null) ? "TBD" : team1.getName(); 
        team1Label = new Label(name1 + "  ");
        text1 = new TextField();
        text1.setPromptText("Enter team scores");
        text1.setOnAction(e -> team1.setScore(Integer.parseInt(text1.getText())));
        teamField1.getChildren().addAll(team1Label, text1);
        
        //makes second hbox with second team name and score field
        HBox teamField2 = new HBox();
        String name2 = (team2 == null) ? "TBD" : team2.getName(); 
        team2Label = new Label(name1 + "  ");
        text2 = new TextField();
        text2.setPromptText("Enter team scores");
        text2.setOnAction(e -> team1.setScore(Integer.parseInt(text2.getText())));
        teamField1.getChildren().addAll(team2Label, text2);
        
        
        BooleanBinding booleanBind = text1.textProperty().isEmpty()
                                        .or(text2.textProperty().isEmpty());
        button.disableProperty().bind(booleanBind);
  
        
        button.setOnAction(e -> { bracket.advanceRound(team1, team2, round, gameIndex);
                                  button.setDisable(true);
                                });
        
        container.getChildren().addAll(teamField1, submitBox, teamField2);
    }
    
    public void setTeam1(Team team1) {
    	this.team1 = team1;
    	this.team1Label = new Label(team1.getName());
    }
    
    public void setTeam2(Team team2) {
    	this.team2 = team2;
    	this.team2Label = new Label(team2.getName());
    }
    
    public VBox getContainer() {
        return container;
    }
    
}
