package nure.knt.entity.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority,IEnumId {
    ALL,CUSTOMER,ADMINISTRATOR,TRAVEL_AGENCY,MODERATOR,COURIER;

    @Override
    public String getAuthority() {
        return this.name();
    }

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
