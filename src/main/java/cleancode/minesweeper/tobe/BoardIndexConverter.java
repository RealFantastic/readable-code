package cleancode.minesweeper.tobe;

// cell input을 사용자로부터 받아서, 인덱스로 각각 변환해주는 class
// 각각 변환해주는 로직을 책임에 따라 분리하기 위해 SRP를 적용하여 별도 Converter 생성
public class BoardIndexConverter {
    private static final char BASE_CHAR_FOR_COL = 'a';

    public int getSelectedRowIndex(String cellInput) {
        String cellInputRow = cellInput.substring(1);
        return convertRowFrom(cellInputRow);
    }

    public int getSelectedColumnIndex(String cellInput) {
        char cellInputColumn = cellInput.charAt(0);
        return convertColumnFrom(cellInputColumn); // 파라미터와 메서드명을 전치사로 연결해 의미를 명확히함
    }

    private int convertRowFrom(String cellInputRow) {
        // 난이도 변경 요구사항 대응 - 지뢰 보드 크기에 따른 두 자리수 이상 행 대응 가능하도록 변경
        // char -> String으로 인자를 받아 Integer로 parsing
        int rowIndex = Integer.parseInt(cellInputRow) -1;
        if (rowIndex < 0) {
            throw new GameException("잘못된 입력입니다.");
        }
        return rowIndex;
    }

    private int convertColumnFrom(char cellInputColumn) {
        // 난이도 변경 요구사항 추가에 따른 컬럼 개수 증가 반영
        // 기존 j까지 대응에서 a~z 전부 대응 가능하도록 ASCII 연산으로 변경
        int colIndex = cellInputColumn - BASE_CHAR_FOR_COL; //입력된 알파벳을 열 인덱스 번호로 치환

        if(colIndex < 0) {
            throw new GameException("입력이 잘못되었습니다.");
        }
        return colIndex;
    }
}
