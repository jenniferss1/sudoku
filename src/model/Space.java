package model;

public class Space {
    private Integer actualValue;
    private final int expectedValue;
    private final boolean fixedPosition;

    public Space(final int expectedValue, final boolean fixedPosition) {
        this.expectedValue = expectedValue;
        this.fixedPosition = fixedPosition;
        if (fixedPosition) {
            actualValue = expectedValue;
        }
    }

    public Integer getActualValue() {
        return actualValue;
    }

    public void setActualValue(final Integer actualValue) {
        if (fixedPosition) return;
        this.actualValue = actualValue;
    }

    public void clearSpace() {
        setActualValue(null);
    }

    public int getExpectedValue() {
        return expectedValue;
    }

    public boolean isFixedPosition() {
        return fixedPosition;
    }
}
