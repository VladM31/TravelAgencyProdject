package nure.knt.tools;

public interface WorkWithCountries {
    public static final int NAME_NOT_FOUND = -1;
    public String[] getCountry();
    public int getIdByCountry(String country);
    public String getCountryById(int id);
}
