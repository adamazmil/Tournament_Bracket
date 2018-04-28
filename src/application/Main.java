package application;
<<<<<<< HEAD
    

import javafx.application.Application;
=======
import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
>>>>>>> 97ba90ec9a4a3d9a3407b601f1b5582d377984e2
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

<<<<<<< HEAD
    
public class Main extends Application {
    
    //static int TEAMNUM = 16;
    String[][] teamRounds = {{"TEAM 1","TEAM 8","TEAM 3","TEAM 6","TEAM 4","TEAM 5","TEAM 2"," TEAM 7"}, 
                            {"TBD","TBD","TBD","TBD"}, 
                            {"TBD","TBD"}};
    
    //int[] teams = {1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 6, 7, 8};
    
    
    //int currentRound;
    
    BorderPane[] rounds = new BorderPane[teamRounds.length];
    
=======
public class Main extends Application {
	
	BracketProcessor bracketData = new BracketProcessor("teams.txt");
    //Team[][] teamRounds = bracketData.getData();
	Team[][] teamRounds = { { new Team("1"), new Team("8"), new Team("3"), new Team("6"), 
								new Team("4"), new Team("5"), new Team("2"), new Team("7") },
            				{ null, null, null, null}, 
            				{ null, null} };

    BorderPane[] rounds = new BorderPane[teamRounds.length];

>>>>>>> 97ba90ec9a4a3d9a3407b601f1b5582d377984e2
    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setTitle("Tournament Bracket");
            
            for (int i = 0; i < teamRounds.length; i++) {
                rounds[i] = new BorderPane();
<<<<<<< HEAD
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
=======
                if (i != 0) {
                    rounds[i - 1].setCenter(rounds[i]);
                }
            }

            for (int i = 0; i < teamRounds.length - 1; i++) {
                VBox roundLeft = new VBox();
                roundLeft.setPadding(new Insets(50 * i, 50, 0, 50));
                VBox roundRight = new VBox();
                roundRight.setPadding(new Insets(50 * i, 50, 0, 50));
                for (int j = 0; j < teamRounds[i].length / 2; j += 2) {
                	roundLeft.getChildren().add(makeGames(teamRounds[i][j], teamRounds[i][j + 1], j, j));
                }
                for (int j = teamRounds[i].length / 2; j < teamRounds[i].length; j += 2) {
                    roundRight.getChildren().add(makeGames(teamRounds[i][j], teamRounds[i][j + 1], i, j));
>>>>>>> 97ba90ec9a4a3d9a3407b601f1b5582d377984e2
                }
                rounds[i].setLeft(roundLeft);
                rounds[i].setRight(roundRight);
            }
<<<<<<< HEAD
            
            VBox lastRound = new VBox();
            lastRound.setPadding(new Insets(50,0,0,20));
            lastRound.getChildren().add(makeGames(teamRounds[teamRounds.length - 1][0],
                    teamRounds[teamRounds.length - 1][1]));
            rounds[rounds.length - 1].setCenter(lastRound);
          
            Scene scene = new Scene(rounds[0],1366,900);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            
            VBox instructionBox = new VBox();
            instructionBox.setPadding(new Insets(20,10,50,20));
=======

            VBox lastRound = new VBox();
            lastRound.setPadding(new Insets(50, 0, 0, 20));
            lastRound.getChildren()
                    .add(makeGames(teamRounds[teamRounds.length - 1][0], 
                    		teamRounds[teamRounds.length - 1][1], teamRounds.length - 1, 0));
            rounds[rounds.length - 1].setCenter(lastRound);

            Scene scene = new Scene(rounds[0], 1366, 900);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            VBox instructionBox = new VBox();
            instructionBox.setPadding(new Insets(20, 10, 50, 20));
>>>>>>> 97ba90ec9a4a3d9a3407b601f1b5582d377984e2
            Label instruction = new Label("Enter scores for the matchup and click submit to lock in scores. "
                    + "Once all matches in a round are entered, winning teams will advance and new scores can be entered.");
            instruction.setFont(new Font("Arial", 25));
            instruction.setWrapText(true);
            instructionBox.getChildren().add(instruction);
<<<<<<< HEAD
            rounds [0].setTop(instructionBox);
            
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
=======
            rounds[0].setTop(instructionBox);

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
>>>>>>> 97ba90ec9a4a3d9a3407b601f1b5582d377984e2
            e.printStackTrace();
        }
    }
    
<<<<<<< HEAD
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
        launch(args);
=======
    private VBox makeGames(Team team1, Team team2, int round, int gameIndex) {

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(0, 0, 50, 0));

        HBox teamField1 = new HBox();
        String name1 = (team1 == null) ? "TBD" : team1.getName(); 
        Label teamName1 = new Label(name1 + "  ");
        TextField inputTeam1 = new TextField();
        inputTeam1.setPromptText("Enter team scores");
        inputTeam1.setOnAction(e -> team1.setScore(Integer.parseInt(inputTeam1.getText())));
//        inputTeam1.setOnAction(e -> System.out.println("works"));
        teamField1.getChildren().addAll(teamName1, inputTeam1);

        HBox teamField2 = new HBox();
        String name2 = (team2 == null) ? "TBD" : team2.getName(); 
        Label teamName2 = new Label(name2 + "  ");
        TextField inputTeam2 = new TextField();
        inputTeam2.setPromptText("Enter team scores");
        inputTeam2.setOnAction(e -> team2.setScore(Integer.parseInt(inputTeam2.getText())));
//        inputTeam2.setOnAction(e -> System.out.println("works"));
        teamField2.getChildren().addAll(teamName2, inputTeam2);
        
        HBox submitBox = new HBox();
        submitBox.setPadding(new Insets(0, 0, 0, 50));
        Button button = new Button("Submit Game Score");
        

        BooleanBinding booleanBind = inputTeam1.textProperty().isEmpty()
                						.or(inputTeam2.textProperty().isEmpty());
        button.disableProperty().bind(booleanBind);
  
        button.setOnAction(e -> { bracketData.advanceRound(team1, team2, round, gameIndex);
        						  button.setDisable(true);
        						});
//        button.setOnAction(e -> System.out.println("works"));
        submitBox.getChildren().addAll(button);

        vBox.getChildren().addAll(teamField1, submitBox, teamField2);

        return vBox;
    }

    public static void main(String[] args) {
    	launch(args);
>>>>>>> 97ba90ec9a4a3d9a3407b601f1b5582d377984e2
    }
}
