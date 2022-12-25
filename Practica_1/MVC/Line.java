package Practica_1.MVC;

import java.util.*;

public class Line extends Observable { // Model

    private List<Character> buffer;
    private int current_column;
    private boolean insert_type;
    private final Console view;

    public Line() {
        current_column = 0;
        insert_type = false;
        buffer = new ArrayList<>();
        view = new Console();
        this.addObserver(view);
    }

    public List<Character> get_buffer() {
        return buffer;
    }

    public int get_current_column() {
        return current_column;
    }

    public void set_current_column(int column) {
        this.current_column = column;
    }

    public void add_character(char character) {

        if (insert_type) {
            buffer.set(current_column, character);
        } else {
            buffer.add(current_column, character);
            current_column++;
        }
        this.setChanged();
        this.notifyObservers(character);
    }

    public void left() {

        if (current_column > 0) {
            current_column--;
            this.setChanged();
            this.notifyObservers(Constants.STRING_LEFT);
        }
    }

    public void right() {

        if (current_column < buffer.size()) {
            current_column++;
            this.setChanged();
            this.notifyObservers(Constants.STRING_RIGHT);
        }
    }

    public void home() {

        while (current_column > 0) {
            this.left();
        }
    }

    public void end() {

        while (current_column < buffer.size()) {
            this.right();
        }
    }

    public void insert() {

        this.insert_type ^= true;
    }

    public void delete() {

        if (current_column < buffer.size()) {
            buffer.remove(current_column);
            this.setChanged();
            this.notifyObservers(Constants.STRING_DELETE);
        }
    }

    public void backspace() {

        if (current_column > 0) {
            current_column--;
            buffer.remove(current_column);
            this.setChanged();
            this.notifyObservers(Constants.STRING_BACKSPACE);
        }
    }

    public String toString() {

        StringBuilder str = new StringBuilder(buffer.size());
        for (Character character : buffer) {
            str.append(character);
        }
        return str.toString();
    }

}