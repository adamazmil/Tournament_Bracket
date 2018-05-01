package application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class BracketProcessor implements BracketProcessorADT {
    private int numberOfTeams;
    private Match[][] matchRounds;

    /**
     * Constructor for this class. Initializes instances variables to set the starting state of the object
     */
    public BracketProcessor(String filepath) {
        try {
            
            //creates stream from inputted filepath and filters to lower case and removes null/empty strings
            Stream<String> teamStream = Files.lines(Paths.get(filepath))
                // remove any null or empty Strings from the Stream
                .filter(x -> x != null && !x.equals(""))
                // make all of the Strings in the Stream uppercase
                .map(String::toLowerCase);
            
            String[] teamList = teamStream.toArray(String[]::new);
            
            
            matchRounds = new Match[(int)((Math.log(teamList.length/2)/Math.log(2))+1)][];
            //System.out.println((int)(Math.log(teamList.length)/Math.log(2)));
            for(int i = 0; i < matchRounds.length; i++) {
                if (i == 0) {
                    matchRounds[0] = new Match[teamList.length/2];
                    for (int j = 0; j < teamList.length; j+=2) {
                        matchRounds[0][j/2] = new Match(new Team(teamList[j]),new Team(teamList[j+1]), 0, j/2, this);
                    }
                }
                else {
                    matchRounds[i] = new Match[matchRounds[0].length/(int)(Math.pow(2, i))];
                    for(int j = 0; j < matchRounds[i].length; j++) {
                        matchRounds[i][j] = new Match(null, null, i, j, this);
                    }
                }
            }
            this.numberOfTeams = teamList.length;
            //seed();
            
            
            for (int i = 0; i < matchRounds.length; i++) {
                for(int j = 0; j < matchRounds[i].length; j++) {
                    if (matchRounds[i][j] == null) {
                        System.out.print("null");
                    } else {
                        System.out.print(matchRounds[i][j]);
                    }
                }
                System.out.println();
            }
            
        } catch(IOException e) {
            e.printStackTrace();
        }
        
    }
    
    @Override

    public Team[] seed() {
//        Team[] temporary = new Team[numberOfTeams];
//        int teamIndex = numberOfTeams-1;
//        for(int i=0; i < numberOfTeams-1 ; i++ ) {
//            System.out.println("test");
//            temporary[i]= matchRounds[0][i];
//            temporary[++i] = matchRounds[0][teamIndex--];
//        }
//        matchRounds[0]=temporary;
//        return matchRounds[0];
        return null;
    }
        
    public void advanceRound(Team team1, Team team2, int round, int gameIndex) {
        Team winner;
        System.out.println("team1 score: " + team1.getScore() + " team2 score: " + team2.getScore());
        if (team1.getScore() > team2.getScore()) {
            winner = team1;
        }
        else {
            winner = team2;
        }
    	//Team winner = (team1.getScore() > team2.getScore()) ? team2 : team1;
    	int winnerPosition = gameIndex / 2;

    	if (gameIndex % 2 == 0) {
    		matchRounds[round + 1][winnerPosition].setTeam1(winner);
    	} else {
    		matchRounds[round + 1][winnerPosition].setTeam2(winner);
    	}
    }
    
    @Override
    public Match[] getData(int index) {  
        return matchRounds[index];
    }
    
    public int getRounds() {
        return matchRounds.length;
    }
    
    

}
