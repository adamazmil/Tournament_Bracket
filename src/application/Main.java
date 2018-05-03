///////////////////////////////////////////////////////////////////////////////
// 
// Title:            A-TEAM Program 5
// Files:            Main.java, Team.java, BracketProcessor.java,
//						BracketProcessorADT.java
// Semester:         CS400 Spring 2018
//
// Authors:           John Chen, Justin Tan, Jun Lin, Adam Azmil
// Lecturer's Name:   Deb Deppler
// Group Number:      24
//
///////////////////////////////////////////////////////////////////////////////

package application;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

// constructs the user interface of the Tournament Bracket
public class Main extends Application {
	
	BracketProcessor bracketData = new BracketProcessor("teams.txt");
	
	Team[] firstRound = bracketData.getData(0);

    BorderPane[] rounds; 
    
    @Override
    // displays the GUI
    public void start(Stage primaryStage) {
        try {
            primaryStage.setTitle("Tournament Bracket");
            primaryStage.getIcons().add(new Image("file:color-UWcrest-print.png"));
            
            String oneTeam = "";
	        if (firstRound == null) { // if there are no teams 
            	Label message = new Label("NO TEAMS ENTERED - 0 WINNERS");
            	message.setId("titleMessage");
	        	rounds = new BorderPane[1];
	        	rounds[0] = new BorderPane();
	        	rounds[0].setCenter(message);
	        	
            } else if (firstRound.length == 1) { // if there is only one team
            	oneTeam = firstRound[0].getNameString();
            	Label message = new Label("Team " + oneTeam + " wins by default");
            	message.setId("titleMessage");
            	rounds = new BorderPane[1];
                rounds[0] = new BorderPane();
                rounds[0].setCenter(message);
            
            } else { // if there are two or more teams
            	// create a BorderPane for each round 
            	rounds = new BorderPane[(int)(Math.log(firstRound.length)/Math.log(2))];
	            for (int i = 0; i < rounds.length; i++) {
	            	rounds[i] = new BorderPane();
	            	if (i != 0) {
	            		rounds[i - 1].setCenter(rounds[i]);
	            	}
	            }
	        
	            // creates the games. Games allow user to input team scores and advance the winner 
	            for (int i = 0; i < rounds.length - 1; i++) {
	            	VBox roundLeft = new VBox(); // stores the games on the left
	            	roundLeft.setAlignment(Pos.CENTER);
	            	roundLeft.setPadding(new Insets(0, 0, 0, 50));
	            	VBox roundRight = new VBox(); // stores the games on the right
	            	roundRight.setAlignment(Pos.CENTER);
	            	roundRight.setPadding(new Insets(0, 50, 0, 0));
	            	Team[] tempRound = bracketData.getData(i);
	            	// for the first half of the teams in round i
	            	for (int j = 0; j < tempRound.length / 2; j += 2) {
	            		roundLeft.getChildren().add(makeGames(tempRound[j], tempRound[j + 1], i, j));
	            	}
	            	//for the second half of the teams in round i
	            	for (int j = tempRound.length / 2; j < tempRound.length; j += 2) {
	            		roundRight.getChildren().add(makeGames(tempRound[j], tempRound[j + 1], i, j));
	            	}
	            	rounds[i].setLeft(roundLeft);
	            	rounds[i].setRight(roundRight);
	            }
	        	
		        // creates the last round game
		        VBox lastRound = new VBox();
		        lastRound.setAlignment(Pos.CENTER);
		        lastRound.setPadding(new Insets(0, 960-(200*(rounds.length)), 0, 960-(200*(rounds.length))));
		        lastRound.getChildren()
		        		.add(makeGames(bracketData.getData(rounds.length-1)[0], 
		        				bracketData.getData(rounds.length-1)[1], rounds.length - 1, 0));
		        rounds[rounds.length - 1].setCenter(lastRound);
		            
		        // creates empty leader board
		        VBox leaderBoardBox = new VBox();
		        leaderBoardBox.setId("leaderBoard");
		        leaderBoardBox.getChildren().addAll(new Label("LEADER BOARD:"),
		        		new Label("1. " + oneTeam), new Label("2."), new Label("3."));
		        rounds[rounds.length - 1].setBottom(leaderBoardBox);
				leaderBoardBox.setAlignment(Pos.BOTTOM_CENTER);
				rounds[0].setBottom(leaderBoardBox);
				
				// prints the instructions
				VBox instructionBox = new VBox();
				instructionBox.setPadding(new Insets(20, 10, 50, 20));
				Label instruction = new Label("Enter scores for the matchup and click "
						+ "submit to lock in scores. Once all matches in a round are entered,"
						+ " winning teams will advance and new scores can be entered.");
				instruction.setId("instruction");
				instruction.setWrapText(true);
				instructionBox.getChildren().add(instruction);
				rounds[0].setTop(instructionBox);
			}

	        Group root = new Group();
	        Scene scene = new Scene(root, 1920, 1080);
	        primaryStage.setScene(scene);

	        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            
	        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
	        
            ScrollPane s1 = new ScrollPane();
            s1.setPannable(true);
            s1.setPrefSize(visualBounds.getWidth(), visualBounds.getHeight());
            s1.setContent(rounds[0]);
            
            root.getChildren().add(s1);

            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * constructs games that user can enter team scores in and advances the winner  
     * 
     * @param   team1   first team of match
     * @param   team2   second team of match
     * @param   round   round number of match (3 rounds in a 4 person tournament, 
     * 			4 teams in first round, 2 teams in second round, 1 in final round(champion))
     * @param   gameIndex   game number within round
     * 
     */
    private VBox makeGames(Team team1, Team team2, int round, int gameIndex) {
        VBox vBox = new VBox(); // stores all elements of the game
        vBox.setPadding(new Insets(0, 0, 50, 0));

        HBox teamField1 = new HBox();
        TextField inputTeam1 = new TextField(); // where user enters team 1 score
        inputTeam1.setPromptText("Enter team scores");
        // sets team's score when score is entered in the text field
        inputTeam1.textProperty().addListener((obs, o, n) -> {
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
        TextField inputTeam2 = new TextField(); // where user enters team 2 score
        inputTeam2.setPromptText("Enter team scores");
        // sets team score when score is entered in the text field
        inputTeam2.textProperty().addListener((obs, o, n) -> {
            try {
                team2.setScore(Integer.parseInt(n));
            } catch (NumberFormatException err) {
                Platform.runLater(() -> { 
                    if (n.length()>o.length()) {
                        Platform.runLater(() -> { 
                            inputTeam2.setText(o);
                        }); 
                    }
                }); 
            }  
        });
        teamField2.getChildren().addAll(team2.getNameLabel(), inputTeam2);
        
        HBox submitBox = new HBox(); // the submit button
        submitBox.setPadding(new Insets(0, 0, 0, 50));
        
        Button button;
        if (round == rounds.length-1) { // if last round
            button = new Button("Finish");
        } else { // if not last round
            button = new Button("Submit Game Score");
        }
        
        // when button is clicked, advance the winner if there are valid scores in team 1 
        // and team 2
        button.setOnAction(e -> { 
        	// if both teams are valid in the game
        	if (!inputTeam1.getText().trim().isEmpty() && !inputTeam2.getText().trim().isEmpty() 
        			&& !team1.getNameString().equals("TBD") 
        			&& !team2.getNameString().equals("TBD")) {
                // update the winner to the next round
        		bracketData.advanceRound(team1, team2, round, gameIndex);
                if (round == rounds.length-1) { // if it is the last round
                	// update the leader board
                	VBox leaderBoardBox = processWinners();
                	leaderBoardBox.setId("leaderBoard");
                	rounds[0].setBottom(leaderBoardBox);
                	leaderBoardBox.setAlignment(Pos.BOTTOM_CENTER);
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
    
    /**
     * gets the top 3 teams and shows them in a leader board  
     *
     * @return  VBox  the leader board
     * 
     */
    private VBox processWinners() {
        List<Team> leaderBoard = bracketData.getLeaderBoard();
    	
        String first = bracketData.getData(rounds.length)[0].getNameString();
        String second = leaderBoard.get(leaderBoard.size() - 1).getNameString();
        String third = "";
        if (leaderBoard.size() > 2) {
            third = (leaderBoard.get(0).getScore() >= leaderBoard.get(1).getScore())
            	? leaderBoard.get(0).getNameString() : leaderBoard.get(1).getNameString();
        } 
        
        // contructs leader board
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(0, 0, 130, 0));
        Label title = new Label("LEADER BOARD:");
        Label firstPlace = new Label("1. " + first);
        Label secondPlace = new Label("2. " + second);
        Label thirdPlace = new Label("3. " + third);
        vBox.getChildren().addAll(title, firstPlace, secondPlace, thirdPlace);
        
        return vBox;
    }

    public static void main(String[] args) {
    	launch(args); // launches GUI
    }
}
