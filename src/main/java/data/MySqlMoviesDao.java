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

        // get resultset from user
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

            // and add that object to the arraylist of movies
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

    }

    @Override
    public void insertAll(Movie[] movies) throws SQLException {

    }

    @Override
    public void update(Movie movie) throws SQLException {

    }

    @Override
    public void delete(int id) throws SQLException {

    }
}
