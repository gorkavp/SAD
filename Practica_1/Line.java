import java.util.*;

public class Line {

    public static final char ESC = (char) 27;
    public static final String ESC_LEFT = ESC + "[D";
    public static final String ESC_RIGHT = ESC + "[C";
    public static final String ESC_HOME = ESC + "[H";
    public static final String ESC_END = ESC + "[F";
    public static final String ESC_INSERT = ESC + "[2~";
    public static final String ESC_DELETE = ESC + "[3~";
    public static final String ESC_BACKSPACE = ESC + "[8~";

    private List<Character> buffer;
    private int column;

    public Line() {
        column = 0;
        buffer = new ArrayList<>();
    }

    public void add_character(char character) {
        if (column == buffer.size()) { // End of the line
            buffer.add(character);
            System.out.print(character);
            column++;
        } else { // Middle of the line
            System.out.print(character);
            column++;
            int positions = 0;
            for (int i = 0; i + column < buffer.size(); i++) { // Update terminal
                System.out.print(buffer.get(i + column));
                positions++;
            }
            for (int i = 0; i < positions; i++) { // Move the cursor to the original column
                System.out.print(ESC_LEFT);
            }
        }
    }

    public void left() {
        if (column > 0) {
            column--;
            System.out.print(ESC_LEFT);
        }
    }

    public void right() {
        if (column < buffer.size()) {
            column++;
            System.out.print(ESC_RIGHT);
        }
    }

    public void delete() {
        if (column < buffer.size()) {
            buffer.remove(column);
            int positions = 0;
            for (int i = 0; i + column < buffer.size(); i++) { // Update terminal
                System.out.print(buffer.get(i + column));
                positions++;
            }
            System.out.print(" ");
            positions++;
            for (int i = 0; i < positions; i++) { // Move the cursor to the original column
                System.out.print(ESC_LEFT);
            }
        }
    }

    public void backspace() {
        if (column > 0) {
            column--;
            buffer.remove(column);
            System.out.print(ESC_LEFT);
            int positions = 0;
            for (int i = 0; i + column < buffer.size(); i++) { // Update terminal
                System.out.print(buffer.get(i + column));
                positions++;
            }
            System.out.print(" ");
            positions++;
            for (int i = 0; i < positions; i++) { // Move the cursor to the original column
                System.out.print(ESC_LEFT);
            }
        }
    }

    public void insert() {

    }

    public void home() {

        while (column > 0) {
            this.left();
        }
    }

    public void end() {

        while (column < buffer.size()) {
            this.right();
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