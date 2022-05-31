package nure.knt.database.idao.core;

public interface IDAOCore<T>  extends IDAOCoreSave<T>,IDAOCoreUpdate<T>,IDAOCoreDelete<T>,IDAOCoreFind<T>{

   public long size();
}
