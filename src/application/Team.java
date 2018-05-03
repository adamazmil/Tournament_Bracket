package application;

import javafx.scene.control.Label;

/*
 * Team class that stores score and a label containing the team name
 */
public class Team {
    private Label name;
    private int score;

    public Team(String name) {
        this.name = new Label(name);
        this.score = -1;
    }
    
    /*
     * gets label associated with team
     * 
     * @return  label connecting to team
     */
    public Label getNameLabel() {
    	return name;
    }
    
    /*
     * sets the text of name label
     * 
     * @param   name    name to change label to
     */
    public void setNameLabel(String name) {
        this.name.setText(name);;
    }
    
    /*
     * getter for string representation of name
     * 
     * @return name as a string
     */
    public String getNameString() {
    	return name.getText();
    }

    /*
     * setter for score of team
     * 
     * @param   score   new score of team
     */
    public void setScore(int score) {
        this.score = score;
    }
    
    /*
     * getter for score
     * 
     * @return  int score of team
     */
    public int getScore() {
        return score;
    }

}
