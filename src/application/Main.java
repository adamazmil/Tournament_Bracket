package application;
import java.util.List;

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
import javafx.scene.text.Font;

public class Main extends Application {
	
	BracketProcessor bracketData = new BracketProcessor("teams.txt");
	
	Team[] firstRound = bracketData.getData(0);

    BorderPane[] rounds = new BorderPane[(int)(Math.log(firstRound.length)/Math.log(2))];

    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setTitle("Tournament Bracket");
            
            for (int i = 0; i < rounds.length; i++) {
                rounds[i] = new BorderPane();
                if (i != 0) {
                    rounds[i - 1].setCenter(rounds[i]);
                }
            }

            for (int i = 0; i < rounds.length - 1; i++) {
                VBox roundLeft = new VBox();
                roundLeft.setPadding(new Insets(50 * i, 50, 0, 50));
                VBox roundRight = new VBox();
                roundRight.setPadding(new Insets(50 * i, 50, 0, 50));
                Team[] tempRound = bracketData.getData(i);
                for (int j = 0; j < tempRound.length / 2; j += 2) {
                	roundLeft.getChildren().add(makeGames(tempRound[j], tempRound[j + 1], i, j));
                }
                for (int j = tempRound.length / 2; j < tempRound.length; j += 2) {
                    roundRight.getChildren().add(makeGames(tempRound[j], tempRound[j + 1], i, j));
                }
                rounds[i].setLeft(roundLeft);
                rounds[i].setRight(roundRight);
            }

            VBox lastRound = new VBox();
            lastRound.setPadding(new Insets(50, 0, 0, 20));
            lastRound.getChildren()
                    .add(makeGames(bracketData.getData(rounds.length-1)[0], 
                            bracketData.getData(rounds.length-1)[1], rounds.length - 1, 0));
            rounds[rounds.length - 1].setCenter(lastRound);

            Scene scene = new Scene(rounds[0], 1366, 900);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            VBox instructionBox = new VBox();
            instructionBox.setPadding(new Insets(20, 10, 50, 20));
            Label instruction = new Label("Enter scores for the matchup and click submit to lock in scores. "
                    + "Once all matches in a round are entered, winning teams will advance and new scores can be entered.");
            instruction.setFont(new Font("Arial", 25));
            instruction.setWrapText(true);
            instructionBox.getChildren().add(instruction);
            rounds[0].setTop(instructionBox);
            
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private VBox makeGames(Team team1, Team team2, int round, int gameIndex) {
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(0, 0, 50, 0));

        HBox teamField1 = new HBox();
        TextField inputTeam1 = new TextField();
        inputTeam1.setPromptText("Enter team scores");
        inputTeam1.setOnAction(e -> team1.setScore(Integer.parseInt(inputTeam1.getText())));
        teamField1.getChildren().addAll(team1.getNameLabel(), inputTeam1);
        
        HBox teamField2 = new HBox();
        TextField inputTeam2 = new TextField();
        inputTeam2.setPromptText("Enter team scores");
        inputTeam2.setOnAction(e -> team2.setScore(Integer.parseInt(inputTeam2.getText())));
        teamField2.getChildren().addAll(team2.getNameLabel(), inputTeam2);
        
        HBox submitBox = new HBox();
        submitBox.setPadding(new Insets(0, 0, 0, 50));
        
        Button button;
        if (round == rounds.length-1) {
            button = new Button("Finish");
        } else {
            button = new Button("Submit Game Score");
        }
        
        button.setOnAction(e -> { 
        	if (!inputTeam1.getText().trim().isEmpty() && !inputTeam2.getText().trim().isEmpty() 
        			&& !team1.getNameString().equals("TBD") && !team2.getNameString().equals("TBD")) {
                bracketData.advanceRound(team1, team2, round, gameIndex);
                if (round == rounds.length-1) {
                	vBox.getChildren().add(processWinners());
                	//rounds[rounds.length - 1].setBottom(processWinners());
                }
                button.setDisable(true);
                inputTeam1.setDisable(true);
                inputTeam2.setDisable(true);
        	}
        });
        
        submitBox.getChildren().addAll(button);
        vBox.getChildren().addAll(teamField1, submitBox, teamField2);
        return vBox;
    }
    
    private VBox processWinners() {
        List<Team> leaderBoard = bracketData.getLeaderBoard();
    	
        Team first = bracketData.getData(rounds.length - 1)[0];
        Team second = leaderBoard.get(2);
        Team third = (leaderBoard.get(0).getScore() >= leaderBoard.get(1).getScore())
        		? leaderBoard.get(0) : leaderBoard.get(1);
        		
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(50, 50, 50, 50));
        Label title = new Label("LEADER BOARD:");
        Label firstPlace = new Label("1. " + first.getNameString());
        Label secondPlace = new Label("2. " + second.getNameString());
        Label thirdPlace = new Label("3. " + third.getNameString());
        vBox.getChildren().addAll(title, firstPlace, secondPlace, thirdPlace);
        
        return vBox;
        
        
        
        
        //        Team winner2 = (bracketData.getData(rounds.length - 2)[0].getNameString().equals(winner1.getNameString())) 
//        		? bracketData.getData(rounds.length - 2)[1] : bracketData.getData(rounds.length - 2)[0];
//        Team winner3 = new Team("temp");
//        winner3.setScore(-1);
//        for (int i = 0; i < bracketData.getData(rounds.length - 3).length; i++) {
//        	if (!bracketData.getData(rounds.length - 3)[i].getNameString().equals(winner1) 
//        			&&  !bracketData.getData(rounds.length - 3)[i].getNameString().equals(winner2))  {
//        		winner3 = (bracketData.getData(rounds.length - 3)[i].getScore() > )
//        	}
//        }
    }

    public static void main(String[] args) {
    	launch(args);
    }
}
