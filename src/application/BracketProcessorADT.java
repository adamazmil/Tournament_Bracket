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

import javafx.scene.layout.HBox;

public interface BracketProcessorADT {
    /**
     * Seeds first round of tournament bracket
     * 
     * @return  array of seeded teams 
     */
    public Team[] seed();
    
    /**
     * moves winning team to next corresponding round and adds finishers to leaderboard based on round position
     * 
     * @param   team1   first team of match
     * @param   team2   second team of match
     * @param   round   round number of match (3 rounds in a 4 person tournament, 4 teams in first round, 2 teams in second round, 1 in final round(champion))
     * @param   gameIndex   game number within round
     * 
     */
    public void advanceRound(Team team1, Team team2, int round, int gameIndex); 
    
    /**
     * gets array of teams of a given round
     * 
     * @param   index   round number to get
     * 
     * @return  array of teams corresponding to round number
     */
    public Team[] getData(int index);
    
}
