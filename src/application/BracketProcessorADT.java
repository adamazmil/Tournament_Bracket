package application;

import javafx.scene.layout.HBox;

public interface BracketProcessorADT {
    public Team[] seed();

    public int advanceRound(Team team1, Team team2, int round, int gameIndex); 
    
    public Team[][] getData();
    
    public HBox[][] getTeamLabels();
}
