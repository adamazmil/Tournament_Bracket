package application;

import javafx.scene.control.Label;

public class Team {
    private Label name;
    private int score;

    public Team(String name) {
        this.name = new Label(name);
    }

    public Label getNameLabel() {
    	return name;
    }
    
    public void setNameLabel(String name) {
        this.name.setText(name);;
    }
    
    public String getNameString() {
    	return name.getText();
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

}
