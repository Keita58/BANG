package factory;

public class DAOFactoryImpl {
    public static DAOFactory getFactory(String choice){
        if("MySQL".equalsIgnoreCase(choice)){
            return new MySQLFactory();
        }
        return null;
    }

}
