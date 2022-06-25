package nure.knt.database.idao.temporary;

public interface IDAOSetRating {

    public boolean hasRating(Long idCustomer,Long idTourAd);
    public boolean updateRating(Long idCustomer,Long idTourAd,int value);
    public boolean insertRating(Long idCustomer,Long idTourAd,int value);
    public static int checkReting(int value){
        if(value > 5){
            return 5;
        }
        if(value < 1){
            return 1;
        }
        return value;
    }
}
