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
    
    private Label label1;
    private Label label2;
    
    private TextField text1;
    private TextField text2;
    
    public Match(Team team1, Team team2, int round, int gameIndex, BracketProcessor bracket) {
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
        label1 = new Label(name1 + "  ");
        text1 = new TextField();
        text1.setPromptText("Enter team scores");
        text1.setOnAction(e -> team1.setScore(Integer.parseInt(text1.getText())));
        teamField1.getChildren().addAll(label1, text1);
        
        //makes second hbox with second team name and score field
        HBox teamField2 = new HBox();
        String name2 = (team2 == null) ? "TBD" : team2.getName(); 
        label2 = new Label(name1 + "  ");
        text2 = new TextField();
        text2.setPromptText("Enter team scores");
        text2.setOnAction(e -> team1.setScore(Integer.parseInt(text2.getText())));
        teamField1.getChildren().addAll(label2, text2);
        
        
        BooleanBinding booleanBind = text1.textProperty().isEmpty()
                                        .or(text2.textProperty().isEmpty());
        button.disableProperty().bind(booleanBind);
  
        
        button.setOnAction(e -> { bracket.advanceRound(team1, team2, round, gameIndex);
                                  button.setDisable(true);
                                });
        
        container.getChildren().addAll(teamField1, submitBox, teamField2);
    }
    
    public void setLabel1() {
    	
    }
    
    public void setLabel2() {
    
    }
    
    public VBox getContainer() {
        return container;
    }
    
}
