package nure.knt.entity.enums;

public enum TypeState implements IEnumId{
    ALL,REGISTRATION,EDITING,REGISTERED;

    private int id = -1;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
