package chess.field;

import chess.figure.*;

import java.io.*;

public class FieldReader {
    public static ChessField fieldFromFile(String path) throws FileNotFoundException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            int y = 0;
            String line = br.readLine();
            ChessField chessField = new ChessField(line.length() / 3);
            while (line != null) {
                var chars = line.toCharArray();
                int x = 0;
                for (int cursor = 0; cursor < chars.length; cursor += 3) {
                    x = (cursor + 2) / 3;
                    switch (chars[cursor]) {
                        case 'b':
                        case 'q':
                        case 'c':
                        case 'C':
                        case 'k':
                        case 'r':
                        case 'R':
                        case 'p':
                        case 'P':
                            chessField.set(x, y, createFigure(chars[cursor], chars[cursor + 1]));
                            break;
                        case '#':
                            break;
                        default:
                            throw new IllegalArgumentException("unexpected figure: " + chars[cursor]);
                    }
                }
                y++;
                line = br.readLine();
            }
            return chessField;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Figure createFigure(char figure, char color) {
        FigureColor figureColor;
        if (color == 'w') {
            figureColor = FigureColor.White;
        } else if (color == 'b') {
            figureColor = FigureColor.Black;
        } else {
            throw new IllegalArgumentException("unexpected  color: " + color);
        }
        Figure f;
        switch (figure) {
            case 'R':
                f = new Rook(figureColor);
                break;
            case 'r':
                f = new Rook(figureColor, false);
                break;
            case 'k':
                f = new Knight(figureColor);
                break;
            case 'b':
                f = new Bishop(figureColor);
                break;
            case 'q':
                f = new Queen(figureColor);
                break;
            case 'C':
                f = new King(figureColor);
                break;
            case 'c':
                f = new King(figureColor, false);
                break;
            case 'P':
                f = new Pawn(figureColor);
                break;
            case 'p':
                f = new Pawn(figureColor, false);
                break;
            default:
                throw new IllegalArgumentException("invalid figure: " + figure);
        }
        return f;
    }
}
