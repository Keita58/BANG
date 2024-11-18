package factory;

public interface DAOFactory <T>{
    T create(String type);
}
