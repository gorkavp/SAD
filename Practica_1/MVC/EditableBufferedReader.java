package MVC;

import java.io.*;

public class EditableBufferedReader extends BufferedReader {

    public static final int CR = 13;
    public static final int ESC = 27;
    public static final int BACKSPACE = 127;
    public static final int LEFT = -1;
    public static final int RIGHT = -2;
    public static final int HOME = -3;
    public static final int END = -4;
    public static final int INSERT = -5;
    public static final int DELETE = -6;

    public EditableBufferedReader(Reader in) {
        super(in);
    }

    public static void setRaw() {
        try {
            String[] command = { "/bin/sh", "-c", "stty -echo raw </dev/tty" };
            Runtime.getRuntime().exec(command).waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void unsetRaw() {
        try {
            String[] command = { "/bin/sh", "-c", "stty echo cooked </dev/tty" };
            Runtime.getRuntime().exec(command).waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int read() throws IOException {

        int character = super.read();
        if (character == ESC) {
            character = super.read(); // Read '['
            character = super.read();
            switch (character) {
                case 'D':
                    return LEFT;
                case 'C':
                    return RIGHT;
                case 'H':
                    return HOME;
                case 'F':
                    return END;
                case '2':
                    character = super.read(); // Read '~'
                    return INSERT;
                case '3':
                    character = super.read(); // Read '~'
                    return DELETE;
            }
        }
        return character;
    }

    @Override
    public String readLine() throws IOException {

        setRaw();
        int character = 0;
        Line line = new Line();

        while (character != CR) {
            character = read();
            if (character > LEFT && character != CR && character != BACKSPACE) {
                line.add_character((char) character);
            } else {
                switch (character) {
                    case LEFT:
                        line.left();
                        break;
                    case RIGHT:
                        line.right();
                        break;
                    case HOME:
                        line.home();
                        break;
                    case END:
                        line.end();
                        break;
                    case INSERT:
                        line.insert();
                        break;
                    case DELETE:
                        line.delete();
                        break;
                    case BACKSPACE:
                        line.backspace();
                        break;
                }
            }
        }

        unsetRaw();
        return line.toString();
    }

}