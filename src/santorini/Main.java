package santorini;
import santorini.board.Board;
import santorini.board.BoardGUI;


public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        new BoardGUI(board);
    }
}
