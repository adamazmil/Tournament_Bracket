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
            
            String[] teamsAsString = teamStream.toArray(String[]::new);
            Team[] teamList = new Team[teamsAsString.length];
            
            //copys stream to array 1:1 (no seeding)
            for (int j = 0; j < teamList.length; j++) {
                    teamList[j] = new Team(teamsAsString[j]);
            }
            matchRounds = new Team[(int)(Math.log(teamList.length)/Math.log(2))+1][];
            //System.out.println((int)(Math.log(teamList.length)/Math.log(2)));
            for(int i = 0; i < matchRounds.length; i++) {
                if (i == 0) {
                    matchRounds[i] = teamList;
                }
                else {
                    matchRounds[i] = new Team[teamList.length/(int)(Math.pow(2, i))];
                }
            }
            this.numberOfTeams = teamList.length;
            seed();
            
            
            for (int i = 0; i < matchRounds.length; i++) {
                for(int j = 0; j < matchRounds[i].length; j++) {
                    if (matchRounds[i][j] == null) {
                        System.out.print("null");
                    } else {
                        System.out.print(matchRounds[i][j].getName());
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
        Team[] temporary = new Team[numberOfTeams];
        int teamIndex = numberOfTeams-1;
        for(int i=0; i < numberOfTeams-1 ; i++ ) {
            System.out.println("test");
            temporary[i]= matchRounds[0][i];
            temporary[++i] = matchRounds[0][teamIndex--];
        }
        matchRounds[0]=temporary;
        return matchRounds[0];
    }
        
    public void advanceRound(Team team1, Team team2, int round, int gameIndex) {
    	Team winner = (team1.getScore() > team2.getScore()) ? team1 : team2;
    	int winnerPosition = gameIndex / 2;
    	if (gameIndex % 2 == 0) {
    		matchRounds[round + 1][winnerPosition].setTeam1(winner);
    	} else {
    		matchRounds[round + 1][winnerPosition].setTeam2(winner);
    	}
    }
    
    @Override
    public Team[] getData(int index) {
        
        return matchRounds[index];
    }
    
    

}
