package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gamelevel.*;
import cleancode.minesweeper.tobe.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.io.ConsoleOutputHandler;
import cleancode.minesweeper.tobe.io.InputHandler;
import cleancode.minesweeper.tobe.io.OutputHandler;

public class GameApplication {

    public static void main(String[] args) {
        GameLevel gameLevel = new VeryBeginner();

        // 지뢰찾기 입장에선 실행 시점에 난이도를 주입받으므로 난이도 변경에 따른 확장에 열려있음.
        // 추후 사용자 선택 난이도 운용 등으로도 변경 가능성 있음
        InputHandler input = new ConsoleInputHandler();
        OutputHandler output = new ConsoleOutputHandler();
        Minesweeper minesweeper = new Minesweeper(gameLevel, input, output);
        minesweeper.initialize();
        minesweeper.run();
    }

    /* 세 가지 비슷한 개념의 구분
    *
    * DIP (Dependency Inversion Principle)
    * - 의존성 역전
    *
    * DI (Dependency Injection) - 의존성 주입
    * - 필요한 의존성을 내가 직접 주입하는 것이 아닌, 외부에서 주입받는 것.
    * - "3" : 제 3자(IoC 컨테이너 - SpringContext)가 의존성을 주입
    *
    * IoC (Inversion of Control)
    * - 제어의 역전
    * - 프로그램의 흐름을 개발자가 아닌 프레임워크가 담당하도록 제어를 역전시킴.
    * - 제어의 순방향 = 개발자가 프로그램의 흐름을 주도
    * - 제어의 역방향 = 프레임워크가 프로그램의 흐름을 주도
    * - Spring의 규격에 맞게 개발
    * - IoC 컨테이너가 객체(Spring Bean)의 생명주기를 관리함(개발자가 new 하지 않음)
    *
    * */
}
