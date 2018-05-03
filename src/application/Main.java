package application;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
                roundLeft.setAlignment(Pos.CENTER);
                roundLeft.setPadding(new Insets(0, 0, 0, 50));
                VBox roundRight = new VBox();
                roundRight.setAlignment(Pos.CENTER);
                roundRight.setPadding(new Insets(0, 0, 0, 50));
                Team[] tempround = bracketData.getData(i);
                for (int j = 0; j < tempround.length / 2; j += 2) {
                	roundLeft.getChildren().add(makeGames(tempround[j], tempround[j + 1], i, j));
                }
                for (int j = tempround.length / 2; j < tempround.length; j += 2) {
                    roundRight.getChildren().add(makeGames(tempround[j], tempround[j + 1], i, j));
                }
                rounds[i].setLeft(roundLeft);
                rounds[i].setRight(roundRight);
            }

            VBox lastRound = new VBox();
            lastRound.setAlignment(Pos.CENTER);
            lastRound.setPadding(new Insets(0, 0, 0, 540-(75*(rounds.length))));
            lastRound.getChildren()
                    .add(makeGames(bracketData.getData(rounds.length-1)[0], 
                            bracketData.getData(rounds.length-1)[1], rounds.length - 1, 0));
            rounds[rounds.length - 1].setCenter(lastRound);
            
//            // testing advanceRound.
//            teamRounds[1][0].setNameLabel("HELLO");

            Scene scene = new Scene(rounds[0], 1920, 1080);
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
        inputTeam1.textProperty().addListener((obs, o, n) -> 
        {
            try {
                team1.setScore(Integer.parseInt(n));
            } catch (NumberFormatException err) {
                System.out.println(n.trim());
                if (n.length()>o.length()) {
                    Platform.runLater(() -> { 
                        inputTeam1.setText(o);
                    }); 
                }
                
            }
            
        });
        teamField1.getChildren().addAll(team1.getNameLabel(), inputTeam1);
        
        HBox teamField2 = new HBox();
        TextField inputTeam2 = new TextField();
        inputTeam2.setPromptText("Enter team scores");
        inputTeam2.textProperty().addListener((obs, o, n) -> 
        {
            try {
                team2.setScore(Integer.parseInt(n));
            } catch (NumberFormatException err) {
                Platform.runLater(() -> { 
                    if (n.length()>o.length()) {
                        Platform.runLater(() -> { 
                            inputTeam1.setText(o);
                        }); 
                    }
                }); 
            }
            
        });
        teamField2.getChildren().addAll(team2.getNameLabel(), inputTeam2);
        
        HBox submitBox = new HBox();
        submitBox.setPadding(new Insets(0, 0, 0, 50));
        
        Button button;
        if (round == rounds.length-1) {
            button = new Button("Finish");
        } else {
            button = new Button("Submit Game Score");
        }
        

//        BooleanBinding booleanBind = inputTeam1.textProperty().isEmpty()
//                						.or(inputTeam2.textProperty().isEmpty());
//        button.disableProperty().bind(booleanBind);
  
        if (round == rounds.length-1) {
            button.setOnAction(e -> { 
                if (!(inputTeam1.getText().trim().isEmpty() || inputTeam2.getText().trim().isEmpty()) 
                        && !team1.getNameString().equals("TBD") && !team2.getNameString().equals("TBD")) {
                    
                    button.setDisable(true);
                }
            });
        } else {
            button.setOnAction(e -> { 
                if (!(inputTeam1.getText().trim().isEmpty() || inputTeam2.getText().trim().isEmpty()) 
                        && !team1.getNameString().equals("TBD") && !team2.getNameString().equals("TBD")) {
                    bracketData.advanceRound(team1, team2, round, gameIndex);
                    button.setDisable(true);
                }
            });
        }
            
        submitBox.getChildren().addAll(button);

        vBox.getChildren().addAll(teamField1, submitBox, teamField2);

        return vBox;
    }
    
    private void processWinners(BracketProcessor processor) {
        
    }

    public static void main(String[] args) {
    	launch(args);
    }
}
