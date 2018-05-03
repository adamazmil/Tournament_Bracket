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
    public Team[] seed();

    public void advanceRound(Team team1, Team team2, int round, int gameIndex); 
    
    public Team[] getData(int index);
    
}
