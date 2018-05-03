package application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;


public class BracketProcessor implements BracketProcessorADT {
    private int numberOfTeams;
    private Team[][] teamRounds;
    private List<Team> leaderBoard = new ArrayList<Team>();

    /**
     * Constructor for this class. Initializes instances variables to set the
     * starting state of the object
     */
    public BracketProcessor(String filepath) {
        try {
            // creates stream from inputted filepath and filters to lower case and removes
            // null/empty strings\
            Stream<String> teamStream = Files.lines(Paths.get(filepath))
                    // remove any null or empty Strings from the Stream
                    .filter(x -> x != null && !x.equals(""))
                    // make all of the Strings in the Stream uppercase
                    .map(String::toLowerCase);
            String[] teamsAsString = teamStream.toArray(String[]::new);
            Team[] teamList = new Team[teamsAsString.length];
            // copys stream to array 1:1 (no seeding)
            for (int j = 0; j < teamList.length; j++) {
                teamList[j] = new Team(teamsAsString[j]);
            }
            
            teamRounds = new Team[(int) (Math.log(teamList.length) / Math.log(2)) + 1][];
            // System.out.println((int)(Math.log(teamList.length)/Math.log(2)));
            for (int i = 0; i < teamRounds.length; i++) {
                if (i == 0) {
                    teamRounds[i] = teamList;
                }
                else {
                    teamRounds[i] = new Team[teamList.length / (int) (Math.pow(2, i))];
                    for(int j = 0; j < teamRounds[i].length; j++) {
                        teamRounds[i][j] = new Team("TBD");
                    }
                }
            }
            this.numberOfTeams = teamList.length;
          
            teamRounds[0] = seed();
            for (int i = 0; i < teamRounds.length; i++) {
                for (int j = 0; j < teamRounds[i].length; j++) {
                    if (teamRounds[i][j] == null) {
                        System.out.print("null");
                    } else {
                        System.out.print(teamRounds[i][j].getNameString()+",");
                    }
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Team[] seed() {
      //seed variables
        Team[] teamList = teamRounds[0];
        int midPoint1 = numberOfTeams/2-1;
        int midPoint2 = numberOfTeams/2;
        int startPoint = 0;
        int endPoint = numberOfTeams-1;
        int roundItterator=0;
        
        Team [] teamSeed = new Team[numberOfTeams];
        if(numberOfTeams>=8) {
            for(int i=0; i<numberOfTeams/8;i++) {
                
                teamSeed[roundItterator++]= teamList[startPoint+i];
                teamSeed[roundItterator++]= teamList[endPoint-i];
                teamSeed[roundItterator++]= teamList[midPoint1-i];
                teamSeed[roundItterator++]= teamList[midPoint2+i];
                teamSeed[roundItterator++]= teamList[numberOfTeams/4-1-i];
                teamSeed[roundItterator++]= teamList[(numberOfTeams/4)*3+i];
                teamSeed[roundItterator++]= teamList[numberOfTeams/4+i];
                teamSeed[roundItterator++]= teamList[(numberOfTeams/4)*3-1-i];

            }
        }
        else if(numberOfTeams==4){
            for(int i=0; i<numberOfTeams/4;i++) {
                
                teamSeed[roundItterator++]= teamList[startPoint+i];
                teamSeed[roundItterator++]= teamList[endPoint-i];
                teamSeed[roundItterator++]= teamList[midPoint1-i];
                teamSeed[roundItterator++]= teamList[midPoint2+i];
                

            }
        } else {
            return teamList;
        }
        
        return teamSeed;
    }

    public boolean advanceRound(Team team1, Team team2, int round, int gameIndex) {
        Team winner = (team1.getScore() > team2.getScore()) ? team1 : team2;
        if (round >= teamRounds.length - 3) {
            Team loser = (team1.getNameString().equals(winner.getNameString())) ? team2 : team1;
            leaderBoard.add(loser);
        }
        int winnerPosition = gameIndex / 2;
        teamRounds[round + 1][winnerPosition].setNameLabel(winner.getNameString());
        return true;
    }

    public Team[] getData(int index) {
        if (teamRounds == null) {
            return null;
        }
        return teamRounds[index];
    }

    public List<Team> getLeaderBoard() {
        return leaderBoard;
    }
}

