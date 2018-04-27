package application;

public class Team {
    private String name;
    private int score;
    private boolean win;

    public Team() {
        win = false;
    }
    
    public Team(String name) {
        this.name = name;
        win = false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setWin() {
        win = true;
    }

}
