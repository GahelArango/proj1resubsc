package edu.guilford;

public class Tile {
    private char letter;
    private int pointValue;

    public Tile(char letter, int value) {
        this.letter = letter;
        this.pointValue = value;
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public int getPointValue() {
        return pointValue;
    }

    public void setPointValue(int pointValue) {
        this.pointValue = pointValue;
    }

    public String toString() {
        return "Tile: " + this.letter + " " + this.pointValue;
    }
}