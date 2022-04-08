package data;

import com.mysql.cj.jdbc.Driver;
import config.Config;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlMoviesDao implements MoviesDao {

    private Connection connection;

    public MySqlMoviesDao() {
        try {
            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection(
                    "jdbc:mysql://" + Config.DB_HOST + ":3306/justin?allowPublicKeyRetrieval=true&useSSL=false",
                    Config.DB_USER,
                    Config.DB_PW
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Movie> all() throws SQLException {
        ArrayList<Movie> movies = new ArrayList<>();

        // make a statement (don't need a prepared statement since not using any variables from end user)
        PreparedStatement st = connection.prepareStatement("select * from movies");

        // get result set from user
        ResultSet rs = st.executeQuery();

        // iterate over the result set
        while (rs.next()) {
            // and make movie object for each row
            Movie movie = new Movie();
            movie.setId(rs.getInt("id"));
            movie.setTitle(rs.getString("title"));
            movie.setRating(rs.getDouble("rating"));
            movie.setPoster(rs.getString("poster"));
            movie.setYear(rs.getInt("year"));
            movie.setGenre(rs.getString("genre"));
            movie.setDirector(rs.getString("director"));
            movie.setPlot(rs.getString("plot"));
            movie.setActors(rs.getString("actors"));

            // add that object to the arraylist of movies
            movies.add(movie);
        }

        rs.close();
        st.close();

        // return the arraylist of movies
        return movies;
    }

    @Override
    public Movie findOne(int id) {
        return null;
    }

    @Override
    public void insert(Movie movie) {

        try {
            // make a prepared statement
            PreparedStatement ps = connection.prepareStatement("insert into movies (title, rating, poster, year, genre, director, plot, actors) " + "values (?, ?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);

            // set all of the user data into the statement
            ps.setString(1, movie.getTitle());
            ps.setDouble(2, movie.getRating());
            ps.setString(3, movie.getPoster());
            ps.setInt(4, movie.getYear());
            ps.setString(5, movie.getGenre());
            ps.setString(6, movie.getDirector());
            ps.setString(7, movie.getPlot());
            ps.setString(8, movie.getActors());

            // execute the query
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            keys.next();
            int newId = keys.getInt(0);

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override

    public void insertAll(Movie[] movies) throws SQLException {
        // iterate over the given movies
        for (Movie movie : movies) {
            // call insert() for each of the movies
            insert(movie);
        }
    }


    @Override
    public void update(Movie movie) throws SQLException {

    }

    @Override
    public void delete(int id) throws SQLException {

    }

    public void cleanUp() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
