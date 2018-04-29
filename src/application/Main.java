package application;
    

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

    
public class Main extends Application {
    
	// Match[][] gameRounds = BracketProcessor.getData
    Match[][] gameRounds = {{new Match(new Team("8"), new Team("1"), 0, 0, null)}};
    
    BorderPane[] rounds = new BorderPane[gameRounds.length];
    
    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setTitle("Tournament Bracket");
            primaryStage.getIcons().add(new Image("file:color-UWcrest-print.png"));
            
            for (int i = 0; i < gameRounds.length; i++) {
                rounds[i] = new BorderPane();
                if (i !=0) {
                    rounds[i-1].setCenter(rounds[i]);
                }
            }
            
            for (int i = 0; i < gameRounds.length - 1; i++) {
                VBox roundLeft = new VBox();
                roundLeft.setPadding(new Insets(50*i, 50, 0, 50));
                VBox roundRight = new VBox();
                roundRight.setPadding(new Insets(50*i, 50, 0, 50));
                
                // for the first half of the games
                for (int j = 0; j < gameRounds[i].length / 2; j++) {
                	// add match
                    roundLeft.getChildren().addAll(gameRounds[i][j].getContainer());
                }
                // for the second half of the games
                for (int j = gameRounds[i].length / 2; j < gameRounds[i].length; j++) {
                    // add match
                	roundRight.getChildren().add(gameRounds[i][j].getContainer());
                }
                rounds[i].setLeft(roundLeft);
                rounds[i].setRight(roundRight);
            }
            
            // add last match
            rounds[rounds.length - 1].setCenter(gameRounds[gameRounds.length][0].getContainer());
          
            Scene scene = new Scene(rounds[0],1366,900);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            
            // instructions
            VBox instructionBox = new VBox();
            instructionBox.setPadding(new Insets(20,10,50,20));
            Label instruction = new Label("Enter scores for the matchup and click submit to lock in scores. "
                    + "Once all matches in a round are entered, winning teams will advance and new scores can be entered.");
            instruction.setFont(new Font("Roboto", 25));
            instruction.setWrapText(true);
            instructionBox.getChildren().add(instruction);
            rounds [0].setTop(instructionBox);
            
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BracketProcessor bracketData = new BracketProcessor("teams.txt");
    	launch(args);
    }
}
    
//    public VBox makeGames(String team1, String team2) {
//        
//        VBox vBox = new VBox();
//        vBox.setPadding(new Insets(0,0,50,0));
//        
//        HBox teamField1 = new HBox();
//        Label teamName1 = new Label(team1 + "  ");
//        TextField inputTeam1 = new TextField();
//        inputTeam1.setPromptText("Enter team scores");
//        inputTeam1.setOnAction( e -> System.out.println("works"));
//        teamField1.getChildren().addAll(teamName1, inputTeam1);
//        
//        HBox submitBox = new HBox();
//        submitBox.setPadding(new Insets(0,0,0,50));
//        Button button = new Button("Submit Game Score");
//        button.setOnAction( e -> System.out.println("works"));
//        submitBox.getChildren().addAll(button);
//        
//        HBox teamField2 = new HBox();
//        Label teamName2 = new Label(team2 + "  ");
//        TextField inputTeam2 = new TextField();
//        inputTeam2.setPromptText("Enter team scores");
//        inputTeam2.setOnAction( e -> System.out.println("works"));
//        teamField2.getChildren().addAll(teamName2, inputTeam2);
//        
//        vBox.getChildren().addAll(teamField1, submitBox, teamField2);
//        
//        return vBox;
//    }
    

