package Practica_1;

import java.io.*;

public class EditableBufferedReader extends BufferedReader {

    public static final int CR = 13;
    public static final int ESC = 27;
    public static final int BACKSPACE = 127;
    public static final int RIGHT = 300;
    public static final int LEFT = 301;
    public static final int HOME = 302;
    public static final int END = 303;
    public static final int INSERT = 304;
    public static final int DEL = 305;

    public EditableBufferedReader(Reader in) {
        super(in);
    }

    public static void setRaw() {
        try {
            String[] command = { "/bin/sh", "-c", "stty raw </dev/tty" };
            Runtime.getRuntime().exec(command).waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void unsetRaw() {
        try {
            String[] command = { "/bin/sh", "-c", "stty cooked </dev/tty" };
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
                case 'C':
                    return RIGHT;
                case 'D':
                    return LEFT;
                case 'H':
                    return HOME;
                case 'F':
                    return END;
                case '1':
                    character = super.read(); // Read '~'
                    return HOME;
                case '2':
                    character = super.read(); // Read '~'
                    return INSERT;
                case '3':
                    character = super.read(); // Read '~'
                    return DEL;
                case '4':
                    character = super.read(); // Read '~'
                    return END;
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
            character = this.read();
            if (character < BACKSPACE && character != CR) {
                line.add_character((char) character);
            } else {
                switch (character) {
                    case RIGHT:
                        line.right();
                        break;
                    case LEFT:
                        line.left();
                        break;
                    case HOME:
                        line.home();
                        break;
                    case INSERT:
                        line.insert();
                        break;
                    case DEL:
                        line.delete();
                        break;
                    case END:
                        line.end();
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
