import com.google.gson.Gson;
import data.Movie;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "MovieServlet", urlPatterns = "/movies/*")
public class MovieServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        try {
            PrintWriter out = response.getWriter();
            Movie newMovie = new Movie("Much Ado About Nothing", 3.5, "a link to a poster", 2022, "drama", "Dirk Funk", "This is a movie about nothing", "Brock Dude, Julia Gal", 1);
            String movieString = new Gson().toJson(newMovie);
            out.println(movieString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
