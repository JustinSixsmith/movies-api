package data;

public class MoviesDaoFactory {

    public enum DaoType {MYSQL, IN_MEMORY};

    public static MoviesDao getMoviesDao(DaoType daoType) {
        // make the right DAO based on the given daoType
        switch (daoType) {
            case MYSQL:
                return new MySqlMoviesDao();
            case IN_MEMORY:
                return new InMemoryMoviesDao();
            default:
                return null;
        }
    }

}
