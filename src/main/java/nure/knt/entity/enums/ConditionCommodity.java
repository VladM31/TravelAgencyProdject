package nure.knt.entity.enums;

public enum ConditionCommodity implements IEnumId{
    ALL,GONE, CANCELED, CONFIRMED, NOT_CONFIRMED;
    private int id = -1;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public static String getUkraineName(ConditionCommodity state){
        switch (state){
            case GONE:
                return "Використано";
            case CANCELED:
                return "Скасовано";
            case CONFIRMED:
                return "Підтверджено";
            case NOT_CONFIRMED:
                return "Не підтверджено";
        }
        return "";
    }
}
