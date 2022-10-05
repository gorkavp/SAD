package noMVC;

import java.util.*;

public class Line {

    private List<Character> buffer;
    private int current_column;
    private boolean insert_type;

    public Line() {
        current_column = 0;
        insert_type = false;
        buffer = new ArrayList<>();
    }

    public void add_character(char character) {

        if (current_column == buffer.size()) { // End of the line
            buffer.add(character);
            System.out.print(character);
            current_column++;
        } else { // Middle of the line
            if (insert_type) {
                buffer.set(current_column, character);
            } else {
                buffer.add(current_column, character);
            }
            System.out.print(character);
            current_column++;
            int position = 0;
            while (position + current_column < buffer.size()) { // Update terminal
                System.out.print(buffer.get(position + current_column));
                position++;
            }
            for (int i = 0; i < position; i++) { // Move the cursor to the original column
                System.out.print(Constants.STRING_LEFT);
            }
        }
    }

    public void left() {

        if (current_column > 0) {
            current_column--;
            System.out.print(Constants.STRING_LEFT);
        }
    }

    public void right() {

        if (current_column < buffer.size()) {
            current_column++;
            System.out.print(Constants.STRING_RIGHT);
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
            int position = 0;
            while (position + current_column < buffer.size()) { // Update terminal
                System.out.print(buffer.get(position + current_column));
                position++;
            }
            System.out.print(" ");
            position++;
            for (int i = 0; i < position; i++) { // Move the cursor to the original column
                System.out.print(Constants.STRING_LEFT);
            }
        }
    }

    public void backspace() {

        if (current_column > 0) {
            current_column--;
            buffer.remove(current_column);
            System.out.print(Constants.STRING_LEFT);
            int position = 0;
            while (position + current_column < buffer.size()) { // Update terminal
                System.out.print(buffer.get(position + current_column));
                position++;
            }
            System.out.print(" ");
            position++;
            for (int i = 0; i < position; i++) { // Move the cursor to the original column
                System.out.print(Constants.STRING_LEFT);
            }
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