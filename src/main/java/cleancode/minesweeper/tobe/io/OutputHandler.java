package cleancode.minesweeper.tobe.io;

import cleancode.minesweeper.tobe.GameBoard;
import cleancode.minesweeper.tobe.GameException;

public interface OutputHandler {

    void showGameStartComments();

    void showBoard(GameBoard board);


    // 기존 'print'라는 단어 자체는 너무 Console에 표현하는 것처럼 구체화된 단어로 보이므로 print -> show로 변경
    void showGameWinningComment();

    void showGameLosingComment();

    void showCommentForSelectingCell();

    void showCommentForUserAction();

    void showExceptionMessage(GameException e);

    void showSimpleMessage(String message);
}
