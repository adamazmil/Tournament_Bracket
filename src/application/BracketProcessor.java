package application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class BracketProcessor implements BracketProcessorADT {
    private Team[] teamList;
    private int numberOfTeams;
    private Team[][] teamRounds;
   

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
            
            
            teamList = new Team[(int)teamStream.count()];
            String[] teamsAsString = (String[])teamStream.toArray();
            
            //copys stream to array 1:1 (no seeding)
            for(int i = 0; i < teamList.length; i++) {
                teamList[i] = new Team(teamsAsString[i]);
            }
            
            this.numberOfTeams = teamList.length;
            seed();
        } catch(IOException e) {
            e.printStackTrace();
        }
        
    }
    
    public Team[] seed() {
        return null;
    }

    public int advanceRound(Team team1, Team team2, int round, int gameIndex) {
    	Team winner = (team1.getScore() > team2.getScore()) ? team1 : team2;
    	int winnerPosition = gameIndex / 2;
    	teamRounds[round + 1][winnerPosition] = winner;
    	return winnerPosition;
    }

    public Team[] getData() {
        
        return null;
    }

}
