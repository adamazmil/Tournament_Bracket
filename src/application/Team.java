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

import javafx.scene.control.Label;

/**
 * Team class that stores score and a label containing the team name
 */
public class Team {
    private Label name;
    private int score;

    /*
     * Constructor for team, initializes Label with given name and score to -1 (not set)
     * 
     * @param   name    name of team
     * 
     */
    public Team(String name) {
        this.name = new Label(name);
        this.score = -1;
    }
    
    /**
     * gets label associated with team
     * 
     * @return  label connecting to team
     */
    public Label getNameLabel() {
    	return name;
    }
    
    /**
     * sets the text of name label
     * 
     * @param   name    name to change label to
     */
    public void setNameLabel(String name) {
        this.name.setText(name);;
    }
    
    /**
     * getter for string representation of name
     * 
     * @return name as a string
     */
    public String getNameString() {
    	return name.getText();
    }

    /**
     * setter for score of team
     * 
     * @param   score   new score of team
     */
    public void setScore(int score) {
        this.score = score;
    }
    
    /**
     * getter for score
     * 
     * @return  int score of team
     */
    public int getScore() {
        return score;
    }

}
