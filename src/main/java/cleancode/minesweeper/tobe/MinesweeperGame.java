package cleancode.minesweeper.tobe;

import java.util.Random;
import java.util.Scanner;

public class MinesweeperGame {

    private static String[][] board = new String[8][10];
    private static Integer[][] landMineCounts = new Integer[8][10];
    private static boolean[][] landMines = new boolean[8][10];
    private static int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    public static void main(String[] args) {
        showGameStartComments();
        Scanner scanner = new Scanner(System.in);
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
            String cellInput = getCellInputFromUser(scanner);
            String userActionInput = getUserActionInputFrom(scanner);
            int selectedColumnIndex = getSelectedColumnIndex(cellInput);
            int selectedRowIndex = getSelectedRowIndex(cellInput);
            if (doesUserChooseToPlantFlag(userActionInput)) { // 사용자가 깃발꽂기를 선택한다면
                board[selectedRowIndex][selectedColumnIndex] = "⚑";
                checkIfGameIsOver();
            } else if (doesUserChooseToOpenCell(userActionInput)) { // 셀 오픈을 선택한다면
                if (isLandMineCell(selectedRowIndex, selectedColumnIndex)) { // 사용자가 지뢰 셀을 선택한 경우
                    board[selectedRowIndex][selectedColumnIndex] = "☼";
                    changeGameStatusToLose();
                    continue;
                } else { // 사용자가 일반 셀을 선택한 경우
                    open(selectedRowIndex, selectedColumnIndex);
                }
                checkIfGameIsOver();
            } else {
                System.out.println("잘못된 번호를 선택하셨습니다.");
            }
        }
    }

    private static boolean isLandMineCell(int selectedRowIndex, int selectedColumnIndex) {
        return landMines[selectedRowIndex][selectedColumnIndex];
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

    private static String getUserActionInputFrom(Scanner scanner) {
        System.out.println("선택한 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
        return scanner.nextLine();
    }

    private static String getCellInputFromUser(Scanner scanner) {
        System.out.println("선택할 좌표를 입력하세요. (예: a1)");
        return scanner.nextLine();
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
        boolean isAllOpened = true;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 10; j++) {
                if (board[i][j].equals("□")) {
                    isAllOpened = false;
                }
            }
        }
        return isAllOpened;
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
        for (int row = 0; row < 8; row++) {
            System.out.printf("%d  ", row + 1);
            for (int col = 0; col < 10; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
    }

    private static void initializeGame() {
        for (int row = 0; row < 8; row++) { //i, j 를 명확히 구분 가능한 row, col로 변경
            for (int col = 0; col < 10; col++) {
                board[row][col] = "□";
            }
        }
        for (int i = 0; i < 10; i++) {
            int col = new Random().nextInt(10);
            int row = new Random().nextInt(8);
            landMines[row][col] = true;
        }
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 10; col++) {
                int count = 0;
                if (!isLandMineCell(row, col)) {
                    if (row - 1 >= 0 && col - 1 >= 0 && isLandMineCell(row - 1, col - 1)) { // 선택 지점의 왼쪽 위에 지뢰가 있는지 확인
                        count++;
                    }
                    if (row - 1 >= 0 && isLandMineCell(row - 1, col)) { // 선택 지점의 정윗방향에 지뢰가 있는지 확인
                        count++;
                    }
                    if (row - 1 >= 0 && col + 1 < 10 && isLandMineCell(row - 1, col + 1)) { // 선택 지점의 오른쪽 위에 지뢰가 있는지 확인
                        count++;
                    }
                    if (col - 1 >= 0 && isLandMineCell(row, col - 1)) { //선택 지점의 정왼쪽에 지뢰가 있는지 확인
                        count++;
                    }
                    if (col + 1 < 10 && isLandMineCell(row, col + 1)) { // 선택 지점의 정 오른쪽 방향에 지뢰가 있는지 확인
                        count++;
                    }
                    if (row + 1 < 8 && col - 1 >= 0 && isLandMineCell(row + 1, col - 1)) { //선택 지점의 왼쪽 아래에 지뢰가 있는지 확인
                        count++;
                    }
                    if (row + 1 < 8 && isLandMineCell(row + 1, col)) { //선택 지점의 정아래에 지뢰가 있는지 확인
                        count++;
                    }
                    if (row + 1 < 8 && col + 1 < 10 && isLandMineCell(row + 1, col + 1)) { //선택 지점의 오른쪽 아래에 지뢰가 있는지 확인
                        count++;
                    }
                    landMineCounts[row][col] = count; // 각 위치에 확인한 지뢰 개수 입력
                    continue;
                }
                landMineCounts[row][col] = 0;
            }
        }
    }

    private static void showGameStartComments() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    private static void open(int row, int col) {
        if (row < 0 || row >= 8 || col < 0 || col >= 10) {
            return;
        }
        if (!board[row][col].equals("□")) { // 이미 열린 셀인 경우
            return;
        }
        if (isLandMineCell(row, col)) { // 지뢰셀인 경우
            return;
        }
        if (landMineCounts[row][col] != 0) { // 사용자가 지뢰 개수가 표시된 셀을 선택했다면
            board[row][col] = String.valueOf(landMineCounts[row][col]); // 지뢰 개수를 보드에 표기
            return;
        } else {
            board[row][col] = "■"; // 열린 빈 셀로 표시
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
