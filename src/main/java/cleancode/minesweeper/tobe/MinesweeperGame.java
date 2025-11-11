package cleancode.minesweeper.tobe;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class MinesweeperGame {

    public static final int BOARD_ROW_SIZE = 8;
    public static final int BOARD_COL_SIZE = 10;
    public static final Scanner SCANNER = new Scanner(System.in);
    private static final String[][] BOARD = new String[BOARD_ROW_SIZE][BOARD_COL_SIZE];
    private static final Integer[][] NEARBY_LAND_MINE_COUNTS = new Integer[BOARD_ROW_SIZE][BOARD_COL_SIZE];
    private static final boolean[][] LAND_MINES = new boolean[BOARD_ROW_SIZE][BOARD_COL_SIZE];
    public static final int LAND_MINE_COUNT = 10;
    public static final String FLAG_SIGN = "⚑";
    public static final String LAND_MINE_SIGN = "☼";
    public static final String CLOSED_CELL_SIGN = "□";
    public static final String OPENED_CELL_SIGN = "■";

    private static int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    public static void main(String[] args) {
        showGameStartComments();
        initializeGame();
        while (true) {
            showBoard();
            if (doesUserWinTheGame()) {
                System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
                break;
            }
            if (doesUserLoseTheGame()) {
                System.out.println("지뢰를 밟았습니다. GAME OVER!");
                break;
            }
            String cellInput = getCellInputFromUser();
            String userActionInput = getUserActionInputFrom();
            actOnCell(cellInput, userActionInput);
        }
    }

    private static void actOnCell(String cellInput, String userActionInput) {
        int selectedColumnIndex = getSelectedColumnIndex(cellInput);
        int selectedRowIndex = getSelectedRowIndex(cellInput);
        if (doesUserChooseToPlantFlag(userActionInput)) { // 사용자가 깃발꽂기를 선택한다면
            BOARD[selectedRowIndex][selectedColumnIndex] = FLAG_SIGN;
            checkIfGameIsOver();
            return;
        }
        if (doesUserChooseToOpenCell(userActionInput)) { // 셀 오픈을 선택한다면
            if (isLandMineCell(selectedRowIndex, selectedColumnIndex)) { // 사용자가 지뢰 셀을 선택한 경우
                BOARD[selectedRowIndex][selectedColumnIndex] = LAND_MINE_SIGN;
                changeGameStatusToLose();
                return;
            }
            // 사용자가 일반 셀을 선택한 경우 open
            open(selectedRowIndex, selectedColumnIndex);
            checkIfGameIsOver();
            return;
        }
        System.out.println("잘못된 번호를 선택하셨습니다.");
    }

    private static boolean isLandMineCell(int selectedRowIndex, int selectedColumnIndex) {
        return LAND_MINES[selectedRowIndex][selectedColumnIndex];
    }

    private static void changeGameStatusToLose() {
        gameStatus = -1;
    }

    private static boolean doesUserChooseToOpenCell(String userActionInput) {
        return userActionInput.equals("1");
    }

    private static boolean doesUserChooseToPlantFlag(String userActionInput) {
        return userActionInput.equals("2");
    }

    private static int getSelectedRowIndex(String cellInput) {
        char cellInputRow = cellInput.charAt(1);
        return convertRowFrom(cellInputRow);
    }

    private static int getSelectedColumnIndex(String cellInput) {
        char cellInputColumn = cellInput.charAt(0);
        return convertColumnFrom(cellInputColumn); // 파라미터와 메서드명을 전치사로 연결해 의미를 명확히함
    }

    private static String getUserActionInputFrom() {
        System.out.println("선택한 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
        return SCANNER.nextLine();
    }

    private static String getCellInputFromUser() {
        System.out.println("선택할 좌표를 입력하세요. (예: a1)");
        return SCANNER.nextLine();
    }

    private static boolean doesUserLoseTheGame() {
        return gameStatus == -1;
    }

    private static boolean doesUserWinTheGame() {
        return gameStatus == 1;
    }

    private static void checkIfGameIsOver() {
        boolean isAllOpened = isAllCellOpened();
        if (isAllOpened) {
            changeGameStatusToWin();
        }
    }

    private static void changeGameStatusToWin() {
        gameStatus = 1;
    }

    private static boolean isAllCellOpened() {
        return Arrays.stream(BOARD) //Stream<String[]>
                .flatMap(Arrays::stream) //Stream<String>
                .noneMatch(cell -> cell.equals(CLOSED_CELL_SIGN));
    }

    private static int convertRowFrom(char cellInputRow) {
        return Character.getNumericValue(cellInputRow) - 1;
    }

    private static int convertColumnFrom(char cellInputColumn) {
        switch (cellInputColumn) { //입력된 알파벳을 열 인덱스 번호로 치환
            case 'a':
                return 0;
            case 'b':
                return 1;
            case 'c':
                return 2;
            case 'd':
                return 3;
            case 'e':
                return 4;
            case 'f':
                return 5;
            case 'g':
                return 6;
            case 'h':
                return 7;
            case 'i':
                return 8;
            case 'j':
                return 9;
            default:
                return -1;
        }
    }

    private static void showBoard() {
        System.out.println("   a b c d e f g h i j");
        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            System.out.printf("%d  ", row + 1);
            for (int col = 0; col < BOARD_COL_SIZE; col++) {
                System.out.print(BOARD[row][col] + " ");
            }
            System.out.println();
        }
    }

    private static void initializeGame() {
        for (int row = 0; row < BOARD_ROW_SIZE; row++) { //i, j 를 명확히 구분 가능한 row, col로 변경
            for (int col = 0; col < BOARD_COL_SIZE; col++) {
                BOARD[row][col] = CLOSED_CELL_SIGN;
            }
        }
        for (int i = 0; i < LAND_MINE_COUNT; i++) {
            int col = new Random().nextInt(BOARD_COL_SIZE);
            int row = new Random().nextInt(BOARD_ROW_SIZE);
            LAND_MINES[row][col] = true;
        }
        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            for (int col = 0; col < BOARD_COL_SIZE; col++) {
                int count = 0;
                if (!isLandMineCell(row, col)) {
                    if (row - 1 >= 0 && col - 1 >= 0 && isLandMineCell(row - 1, col - 1)) { // 선택 지점의 왼쪽 위에 지뢰가 있는지 확인
                        count++;
                    }
                    if (row - 1 >= 0 && isLandMineCell(row - 1, col)) { // 선택 지점의 정윗방향에 지뢰가 있는지 확인
                        count++;
                    }
                    if (row - 1 >= 0 && col + 1 < BOARD_COL_SIZE && isLandMineCell(row - 1, col + 1)) { // 선택 지점의 오른쪽 위에 지뢰가 있는지 확인
                        count++;
                    }
                    if (col - 1 >= 0 && isLandMineCell(row, col - 1)) { //선택 지점의 정왼쪽에 지뢰가 있는지 확인
                        count++;
                    }
                    if (col + 1 < BOARD_COL_SIZE && isLandMineCell(row, col + 1)) { // 선택 지점의 정 오른쪽 방향에 지뢰가 있는지 확인
                        count++;
                    }
                    if (row + 1 < BOARD_ROW_SIZE && col - 1 >= 0 && isLandMineCell(row + 1, col - 1)) { //선택 지점의 왼쪽 아래에 지뢰가 있는지 확인
                        count++;
                    }
                    if (row + 1 < BOARD_ROW_SIZE && isLandMineCell(row + 1, col)) { //선택 지점의 정아래에 지뢰가 있는지 확인
                        count++;
                    }
                    if (row + 1 < BOARD_ROW_SIZE && col + 1 < BOARD_COL_SIZE && isLandMineCell(row + 1, col + 1)) { //선택 지점의 오른쪽 아래에 지뢰가 있는지 확인
                        count++;
                    }
                    NEARBY_LAND_MINE_COUNTS[row][col] = count; // 각 위치에 확인한 지뢰 개수 입력
                    continue;
                }
                NEARBY_LAND_MINE_COUNTS[row][col] = 0;
            }
        }
    }

    private static void showGameStartComments() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    private static void open(int row, int col) {
        if (row < 0 || row >= BOARD_ROW_SIZE || col < 0 || col >= BOARD_COL_SIZE) {
            return;
        }
        if (!BOARD[row][col].equals(CLOSED_CELL_SIGN)) { // 이미 열린 셀인 경우
            return;
        }
        if (isLandMineCell(row, col)) { // 지뢰셀인 경우
            return;
        }
        if (NEARBY_LAND_MINE_COUNTS[row][col] != 0) { // 사용자가 지뢰 개수가 표시된 셀을 선택했다면
            BOARD[row][col] = String.valueOf(NEARBY_LAND_MINE_COUNTS[row][col]); // 지뢰 개수를 보드에 표기
            return;
        } else {
            BOARD[row][col] = OPENED_CELL_SIGN; // 열린 빈 셀로 표시
        }
        open(row - 1, col - 1);
        open(row - 1, col);
        open(row - 1, col + 1);
        open(row, col - 1);
        open(row, col + 1);
        open(row + 1, col - 1);
        open(row + 1, col);
        open(row + 1, col + 1);
    }

}
