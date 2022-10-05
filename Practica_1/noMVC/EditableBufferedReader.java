package noMVC;

import java.io.*;

public class EditableBufferedReader extends BufferedReader {

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
        if (character == Constants.INT_ESC) {
            character = super.read(); // Read '['
            character = super.read();
            switch (character) {
                case 'D':
                    return Constants.INT_LEFT;
                case 'C':
                    return Constants.INT_RIGHT;
                case 'H':
                    return Constants.INT_HOME;
                case 'F':
                    return Constants.INT_HOME;
                case '2':
                    character = super.read(); // Read '~'
                    return Constants.INT_INSERT;
                case '3':
                    character = super.read(); // Read '~'
                    return Constants.INT_DELETE;
            }
        }
        return character;
    }

    @Override
    public String readLine() throws IOException {

        setRaw();
        int character = 0;
        Line line = new Line();

        while (character != Constants.INT_CR) {
            character = read();
            if (character > Constants.INT_LEFT && character != Constants.INT_CR
                    && character != Constants.INT_BACKSPACE) {
                line.add_character((char) character);
            } else {
                switch (character) {
                    case Constants.INT_LEFT:
                        line.left();
                        break;
                    case Constants.INT_RIGHT:
                        line.right();
                        break;
                    case Constants.INT_HOME:
                        line.home();
                        break;
                    case Constants.INT_END:
                        line.end();
                        break;
                    case Constants.INT_INSERT:
                        line.insert();
                        break;
                    case Constants.INT_DELETE:
                        line.delete();
                        break;
                    case Constants.INT_BACKSPACE:
                        line.backspace();
                        break;
                }
            }
        }
        unsetRaw();
        return line.toString();
    }
}