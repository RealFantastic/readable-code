package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gamelevel.*;

public class GameApplication {

    public static void main(String[] args) {
        GameLevel gameLevel = new VeryBeginner();

        // 지뢰찾기 입장에선 실행 시점에 난이도를 주입받으므로 난이도 변경에 따른 확장에 열려있음.
        // 추후 사용자 선택 난이도 운용 등으로도 변경 가능성 있음
        Minesweeper minesweeper = new Minesweeper(gameLevel);
        minesweeper.initialize();
        minesweeper.run();
    }
}
