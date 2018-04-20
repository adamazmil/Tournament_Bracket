package application;
	

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

    
public class Main extends Application {
    
    static int TEAMNUM = 8;
	int[] teams = {1, 2, 3, 4, 5, 6, 7, 8};
	int lengthOfRoundArray = 3;
	int currentRound;
	BorderPane[] rounds = new BorderPane[lengthOfRoundArray];
    
    @Override
	public void start(Stage primaryStage) {
		try {
		    primaryStage.setTitle("Tournament Bracket");
            
		    
		    for (int i = 0; i < lengthOfRoundArray; i++) {
		        rounds[i] = new BorderPane();
		        if (i !=0) {
		            rounds[i-1].setCenter(rounds[i]);
		        }
		    }
		    //BorderPane root = new BorderPane();
		    
		    for (int i = 0; i < lengthOfRoundArray; i++) {
		        VBox roundLeft = new VBox();
//		        roundLeft.setPadding(new Insets(50*i, 50, 0, 0));
	            VBox roundRight = new VBox();
//	            roundRight.setPadding(new Insets(50*i, 0, 0, 50));
		        for (int j = 0; j < (int)Math.pow(2, 1-i); j++) {
	                roundLeft.getChildren().add(makeGames(teams[i], teams[i + 1]));
	                roundRight.getChildren().add(makeGames(teams[i], teams[i + 1]));
	            }
	            rounds[i].setLeft(roundLeft);
	            rounds[i].setRight(roundRight);
		    }
		    
		    rounds[rounds.length - 1].setCenter(makeGames(1,2));
            
//            VBox round1Left = new VBox();
//            VBox round1Right = new VBox();
//            for (int i = 0; i < teams.length/2; i += 2) {
//                round1Left.getChildren().add(makeGames(root, teams[i], teams[i + 1]));
//            }
//            for (int i = teams.length/2; i < teams.length; i += 2) {
//                round1Right.getChildren().add(makeGames(root, teams[i], teams[i + 1]));
//            }
//            
//            root.setLeft(round1Left);
//            root.setRight(round1Right);
            
			Scene scene = new Scene(rounds[0],1366,900);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			rounds [0].setTop(new Label("Enter scores for matchup and click submit to lock in scores. "
			                + "Once all matches in a round are entered, winning teams will advance and new scores can be entered."));
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
    
    public VBox makeGames(int team1, int team2) {
        
        VBox vBox = new VBox();
        vBox.setPadding(new Insets (0,0,50,0));
        
        HBox teamField1 = new HBox();
        Label teamName1 = new Label("team " + team1);
        TextField inputTeam1 = new TextField();
        inputTeam1.setPromptText("Enter team scores");
        inputTeam1.setOnAction( e -> System.out.println("works"));
        teamField1.getChildren().addAll(teamName1, inputTeam1);
        
        Button button = new Button("Submit Game Score");
        button.setOnAction( e -> System.out.println("works"));
        
        HBox teamField2 = new HBox();
        Label teamName2 = new Label("team " + team2);
        TextField inputTeam2 = new TextField();
        inputTeam2.setPromptText("Enter team scores");
        inputTeam2.setOnAction( e -> System.out.println("works"));
        teamField2.getChildren().addAll(teamName2, inputTeam2);
        
        vBox.getChildren().addAll(teamField1, button, teamField2);
        
        return vBox;
    }
	
	public static void main(String[] args) {
		launch(args);
	}
}
