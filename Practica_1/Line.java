//package Practica_1;

import java.util.*;

public class Line {

    public static final char ESC = (char) 27;
    public static final String ESC_LEFT = ESC + "[D";
    public static final String ESC_RIGHT = ESC + "[C";
    public static final String ESC_END = ESC + "[F";
    public static final String ESC_HOME = ESC + "[H";
    public static final String ESC_INSERT = ESC + "[2~";
    public static final String ESC_DEL = ESC + "[3~";
    public static final String ESC_BACKSPACE = ESC + "[8~";

    private List<Character> buffer;
    private int column;
    private boolean insert;

    public Line() {
        this.column = 0;
        this.insert = false;
        this.buffer = new ArrayList<>();
    }

    public void add_character(char character) {

        if (this.column == this.buffer.size()) {
            this.buffer.add(character);
            this.column++;
        } else {
            if (this.insert) {
                this.buffer.set(this.column, character);
            } else {
                this.buffer.add(this.column, character);
            }
            this.column++;
            int moves = 0; // Update terminal
            for (int i = 0; i + this.column < this.buffer.size(); i++) {
                System.out.print(buffer.get(i + this.column));
                moves++;
            }
            for (int i = 0; i < moves; i++) { // Put the cursor to the original position
                System.out.print(ESC_LEFT);
            }
        }
    }

    public void left() {
        if (this.column > 0) {
            this.column--;
            System.out.print(ESC_LEFT);
        }
    }

    public void right() {
        if (this.column < this.buffer.size()) {
            this.column++;
            System.out.print(ESC_RIGHT);
        }
    }

    public void delete() {
        if (this.column < this.buffer.size()) {
            this.buffer.remove(this.column);
            int positions = 0;
            for (int i = 0; i + this.column < this.buffer.size(); i++) {
                System.out.print(this.buffer.get(i + this.column));
                positions++;
            }
            System.out.print(" ");
            positions++;
            for (int i = 0; i < positions; i++) {
                System.out.print(ESC_LEFT);
            }
        }
    }

    public void backspace() {
        if (this.column > 0) {
            this.column--;
            this.buffer.remove(this.column);
            System.out.print(ESC_LEFT);
            int positions = 0;
            for (int i = 0; i + this.column < this.buffer.size(); i++) {
                System.out.print(this.buffer.get(i + this.column));
                positions++;
            }
            System.out.print(" ");
            positions++;
            for (int i = 0; i < positions; i++) {
                System.out.print(ESC_LEFT);
            }
        }
    }

    public void insert() {
        this.insert ^= true;
    }

    public void home() {
        while (this.column > 0) {
            this.left();
        }
    }

    public void end() {
        while (this.column < this.buffer.size()) {
            this.right();
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(this.buffer.size());
        for (Character character : this.buffer) {
            sb.append(character);
        }
        return sb.toString();
    }

}
