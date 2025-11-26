package cleancode.minesweeper.tobe;

public class Cell {
    
    private static final String FLAG_SIGN = "⚑";
    private static final String LAND_MINE_SIGN = "☼";
    private static final String UNCHECKED_SIGN = "□";
    private static final String EMPTY_SIGN = "■";

    private final String sign;
    private int nearbyLandMineCount;
    private boolean isLandMine;
    private boolean isFlagged;

    //Cell이 가진 속성 : 근처 지뢰의 숫자, 지뢰 여부
    //Cell의 상태 : 깃발 유무, 열림/닫힘, 사용자가 확인함.

    private Cell(String sign, int nearbyLandMineCount, boolean isLandMine, boolean isFlagged) {
        this.sign = sign;
        this.nearbyLandMineCount = nearbyLandMineCount;
        this.isLandMine = isLandMine;
        this.isFlagged = isFlagged;
    }

    public static Cell of(String sign, int nearbyLandMineCount, boolean isLandMine, boolean isFlagged) {
        return new Cell(sign, nearbyLandMineCount, isLandMine, isFlagged);
    }

    public static Cell create() {
        return of("", 0, false, false);
    }

    public static Cell ofFlag() {
        return of(FLAG_SIGN, 0, false);
    }

    public static Cell ofLandMine() {
        return of(LAND_MINE_SIGN, 0, false);
    }

    public static Cell ofClosed() {
        return of(UNCHECKED_SIGN, 0, false);
    }

    public static Cell ofOpened() {
        return of(EMPTY_SIGN, 0, false);
    }

    public static Cell ofNearbyLandMineCount(int count) {
        return of(String.valueOf(count), 0, false);
    }

    public void turnOnLandMine() {
        this.isLandMine = true;
    }

    public String getSign() {
        return sign;
    }

    public boolean isClosed() {
        return UNCHECKED_SIGN.equals(this.sign);
    }

    public boolean doesNotClosed() {
        return !isClosed();
    }

    public void updateNearbyLandMineCount(int count) {
        this.nearbyLandMineCount = count;
    }

    public void flag() {
        this.isFlagged = true;
    }
}
