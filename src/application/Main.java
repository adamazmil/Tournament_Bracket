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
    
    //static int TEAMNUM = 16;
    String[][] teamRounds = {{"TEAM 1","TEAM 8","TEAM 3","TEAM 6","TEAM 4","TEAM 5","TEAM 2"," TEAM 7"}, 
                            {"TBD","TBD","TBD","TBD"}, 
                            {"TBD","TBD"}};
    
    //int[] teams = {1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 6, 7, 8};
    
    
    //int currentRound;
    
    BorderPane[] rounds = new BorderPane[teamRounds.length];
    
    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setTitle("Tournament Bracket");
            primaryStage.getIcons().add(new Image("file:color-UWcrest-print.png"));
            
            for (int i = 0; i < teamRounds.length; i++) {
                rounds[i] = new BorderPane();
                if (i !=0) {
                    rounds[i-1].setCenter(rounds[i]);
                }
            }
            
            for (int i = 0; i < teamRounds.length - 1; i++) {
                VBox roundLeft = new VBox();
                roundLeft.setPadding(new Insets(50*i, 50, 0, 50));
                VBox roundRight = new VBox();
                roundRight.setPadding(new Insets(50*i, 50, 0, 50));
                for (int j = 0; j < teamRounds[i].length / 2; j += 2) {
                    roundLeft.getChildren().add(makeGames(teamRounds[i][j], teamRounds[i][j + 1]));
                }
                for (int j = teamRounds[i].length / 2; j < teamRounds[i].length; j += 2) {
                    roundRight.getChildren().add(makeGames(teamRounds[i][j], teamRounds[i][j + 1]));
                }
                rounds[i].setLeft(roundLeft);
                rounds[i].setRight(roundRight);
            }
            
            VBox lastRound = new VBox();
            lastRound.setPadding(new Insets(50,0,0,20));
            lastRound.getChildren().add(makeGames(teamRounds[teamRounds.length - 1][0],
                    teamRounds[teamRounds.length - 1][1]));
            rounds[rounds.length - 1].setCenter(lastRound);
          
            Scene scene = new Scene(rounds[0],1366,900);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            
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
    
    public VBox makeGames(String team1, String team2) {
        
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(0,0,50,0));
        
        HBox teamField1 = new HBox();
        Label teamName1 = new Label(team1 + "  ");
        TextField inputTeam1 = new TextField();
        inputTeam1.setPromptText("Enter team scores");
        inputTeam1.setOnAction( e -> System.out.println("works"));
        teamField1.getChildren().addAll(teamName1, inputTeam1);
        
        HBox submitBox = new HBox();
        submitBox.setPadding(new Insets(0,0,0,50));
        Button button = new Button("Submit Game Score");
        button.setOnAction( e -> System.out.println("works"));
        submitBox.getChildren().addAll(button);
        
        HBox teamField2 = new HBox();
        Label teamName2 = new Label(team2 + "  ");
        TextField inputTeam2 = new TextField();
        inputTeam2.setPromptText("Enter team scores");
        inputTeam2.setOnAction( e -> System.out.println("works"));
        teamField2.getChildren().addAll(teamName2, inputTeam2);
        
        vBox.getChildren().addAll(teamField1, submitBox, teamField2);
        
        return vBox;
    }
    
    public static void main(String[] args) {
        BracketProcessor bracketData = new BracketProcessor("teams.txt");
    	launch(args);
    }
}
