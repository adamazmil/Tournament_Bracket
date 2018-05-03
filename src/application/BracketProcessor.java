///////////////////////////////////////////////////////////////////////////////
// 
// Title:            A-TEAM Program 5
// Files:            Main.java, Team.java, bracketProcessor.java,
//						bracketProcessorADT.java
// Semester:         CS400 Spring 2018
//
// Authors:           John Chen, Justin Tan, Jun Lin, Adam Azmil
// Lecturer's Name:   Deb Deppler
// Group Number:      24
//
///////////////////////////////////////////////////////////////////////////////

package application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class BracketProcessor implements BracketProcessorADT {
    private int numberOfTeams;
    //2d array holding all rounds of the tournament, 
    //last round is only champion, first round is all teams, eliminates half of the teams per round
    private Team[][] teamRounds; 

    //list of teams leaderboard, constructed in reverse order
    private List<Team> leaderBoard = new ArrayList<Team>();

    /**
     * Constructor for bracketprocessor, takes a filepath as a string and constructs the 2d array of rounds based on the size of the file
     * teams in file assumed to be in positional order
     * 
     */
    public BracketProcessor(String filepath) {
        try {
            // creates stream from inputted filepath and filters to lower case and removes
            // null and empty strings, 
            Stream<String> teamStream = Files.lines(Paths.get(filepath))
                    // remove any null or empty Strings from the Stream
                    .filter(x -> x != null && !x.equals(""))
                    // make all of the Strings in the Stream lowercase
                    .map(String::toLowerCase);


            String[] teamsAsString = teamStream.toArray(String[]::new);
            Team[] teamList = new Team[teamsAsString.length];
            if (teamsAsString.length == 0) {
                teamRounds = null;
                return;
            }
            if (teamsAsString.length == 1) {
                teamRounds = new Team[1][1];
                teamRounds[0][0] = new Team(teamsAsString[0]);
                return;
            }
            // copys stream to array 1:1 (no seeding)
            for (int j = 0; j < teamList.length; j++) {
                teamList[j] = new Team(teamsAsString[j]);
            }

            //calculates row length of round 2d array
            teamRounds = new Team[(int) (Math.log(teamList.length) / Math.log(2)) + 1][];

            //fills first round with given teams, and all other rounds with TBD teams
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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Seeds first round inside 2d array implementation of bracketprocessor (1st v last, 2nd v 2nd to last, etc in alternating order)
     * assuming inputted array is already in positional order
     * 
     * @return  array of seeded teams 
     */
    public Team[] seed() {
        //seed variables
        ArrayList<Team> list = new ArrayList<Team>(Arrays.asList(teamRounds[0]));
        //starting seed, only first 2 indices
        Team[] base = {teamRounds[0][0], teamRounds[0][1]};

        
        //iterates log base 2 of size then minus 1 (ex: size 8, iterates 2 times) 
        //for each iteration, makes a new array of 2 times previous length, and fills in every other index with the contents of old array
        //old: {1, 2} --> new: {1, _, 2, _}
        //then, fills in empty spaces with corresponding team taken from ordered team list (first corresponds to last, second to last corresponds to second, etc.
        for (int i = 0; i < (Math.log(list.size())/Math.log(2)) - 1; i++) {
            ArrayList<Team> iter = new ArrayList<Team>( list.subList(0, (int)Math.pow(2, i+2)));
            Team[] newlist = new Team[base.length*2];
            for (int j = 0; j < base.length; j++) {
                //fill every other space with contents of previous (base) array
                newlist[j*2] = base[j];
                //fill in empty space with corresponding entry from full list
                newlist[(j*2)+1] = iter.get((iter.size() - 1) - iter.indexOf(base[j]));
            }
            //make base the new previous array
            base = newlist;
        }

        return base;
    }

    /**
     * moves winning team to next corresponding round and adds finishers to leaderboard based on round position
     * 
     * @param   team1   first team of match
     * @param   team2   second team of match
     * @param   round   round number of match (3 rounds in a 4 person tournament, 4 teams in first round, 2 teams in second round, 1 in final round(champion))
     * @param   gameIndex   game number within round
     * 
     */
    public void advanceRound(Team team1, Team team2, int round, int gameIndex) {
        Team winner = (team1.getScore() > team2.getScore()) ? team1 : team2;

        //calculates 3rd place winner in semifinals
        if (round >= teamRounds.length - 3) {
            Team loser = (team1.getNameString().equals(winner.getNameString())) ? team2 : team1;
            leaderBoard.add(loser);
        }

        //advances team with higher score
        int winnerPosition = gameIndex / 2;
        teamRounds[round + 1][winnerPosition].setNameLabel(winner.getNameString());
    }

    /**
     * gets array of teams of a given round, returns null if no teams present
     * 
     * @param   index   round number to get
     * 
     * @return  array of teams corresponding to round number
     */
    public Team[] getData(int index) {
        if (teamRounds == null) {
            return null;
        }
        return teamRounds[index];
    }

    /**
     * getter for leaderboard list
     * 
     * @return  List of teams of leaderboard in reverse order
     */
    public List<Team> getLeaderBoard() {
        return leaderBoard;
    }
}

