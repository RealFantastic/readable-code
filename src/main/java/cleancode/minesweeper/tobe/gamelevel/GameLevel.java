package cleancode.minesweeper.tobe.gamelevel;

// 게임 난이도 변경 요구사항 대응 - 난이도에 따라 지뢰찾기 보드판의 크기, 지뢰 개수를 다르게 해야함
public interface GameLevel {

    int getRowSize();

    int getColSize();

    int getLandMineCount();
}
