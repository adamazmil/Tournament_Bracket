package application;

import javafx.scene.layout.HBox;

public interface BracketProcessorADT {
    public Team[] seed();

    public boolean advanceRound(Team team1, Team team2, int round, int gameIndex); 
    
    public Team[][] getData();
    
}
