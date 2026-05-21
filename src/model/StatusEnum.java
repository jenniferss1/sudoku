package model;

public enum StatusEnum {
    NOT_STARTED("não iniciado"),
    INCOMPLETE("incompleto"),
    COMPLETE("completo");

    private final String label;

    StatusEnum(final String label){
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
