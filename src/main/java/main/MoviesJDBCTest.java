package main;

import com.mysql.cj.jdbc.Driver;
import config.Config;
import data.Movie;

import java.sql.*;
import java.util.ArrayList;

public class MoviesJDBCTest {
    private static Connection connection = null;

    public static void main(String[] args) throws SQLException {
        DriverManager.registerDriver(new Driver());
        connection = DriverManager.getConnection(
                "jdbc:mysql://" + Config.DB_HOST + ":3306/justin?allowPublicKeyRetrieval=true&useSSL=false",
                Config.DB_USER,
                Config.DB_PW
        );

        ArrayList<Movie> movies = fetchAllMovies();
        System.out.println(movies);

        // insert a movie
        Statement st = connection.createStatement();

        // let's assume the below variables come from an end-user
        String newTitle = "Test Movie";
        int newYear = 2000;
        double newRating = 8.7;

        // never ever do this: concatenate user input into ANY query string
//        String mySQLString = "insert into movies (vin, year, mileage) values ('" + newVin + "', " + newYear + ", " + newMileage + ")";
//        st.executeUpdate(mySQLString);

        // general sql injection prevention technique is use parameterized queries
        // jdbc calls them prepared statements
        PreparedStatement ps = connection.prepareStatement("insert into movies (title, year, rating) values (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, newTitle);
        ps.setInt(2, newYear);
        ps.setDouble(3, newRating);
        ps.executeUpdate();

        ResultSet newKeys = ps.getGeneratedKeys();
        newKeys.next();
        int newId = newKeys.getInt(1);
        System.out.println("new record id is " + newId);
        newKeys.close();

        movies = fetchAllMovies();
        System.out.println(movies);

        newYear = 2010;

        ps = connection.prepareStatement("update movies set year = ? where id = ?");
        ps.setInt(1, newYear);
        ps.setInt(2, newId);
        ps.executeUpdate();

        movies = fetchAllMovies();
        System.out.println(movies);

        ps = connection.prepareStatement("delete from movies where id = ?");
        ps.setInt(1, newId);
        ps.executeUpdate();

        movies = fetchAllMovies();
        System.out.println(movies);

        ps.close();
        st.close();
        connection.close();
    }

    private static ArrayList<Movie> fetchAllMovies() throws SQLException {
        ArrayList<Movie> movies = new ArrayList<>();

        // use the connection for sql work
        Statement st = connection.createStatement();
        ResultSet movieRows = st.executeQuery("select title, year, rating from movies");
        // iterate over the rows and print out fields for each row
        // can rig up a manual counter if we want
//        int rowCount = 0;
        while (movieRows.next()) { // for each row in the resultSet
//            rowCount++;
            // make a movie object from that row
            Movie movie = new Movie();
            movie.setTitle(movieRows.getString("title"));
            movie.setYear(movieRows.getInt("year"));
            movie.setRating(movieRows.getDouble("rating"));
            System.out.println(movie);

            movies.add(movie);
        }
//        System.out.println("row count: " + rowCount);

//        carRows = st.executeQuery("select count(id) from movies");
//        movieRows.next();
//        System.out.println("row count via count function: " + movieRows.getInt("count(id)"));

        return movies;
    }
}
