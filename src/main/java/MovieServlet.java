import com.google.gson.Gson;
import data.Movie;

import java.io.*;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "MovieServlet", urlPatterns = "/movies/*")
public class MovieServlet extends HttpServlet {


    ArrayList<Movie> movies = new ArrayList<>();
    int nextID = 1;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");

        try {
            PrintWriter out = response.getWriter();
            String movieString = new Gson().toJson(movies.toArray());
            out.println(movieString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        Movie[] newMovies = new Gson().fromJson(request.getReader(), Movie[].class);
        for (Movie movie : newMovies) {
            movie.setId(nextID++);
            movies.add(movie);
        }

        try {
            PrintWriter out = response.getWriter();
            out.println("Movie(s) created");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String [] uriParts = req.getRequestURI().split("/");
        int targetId = Integer.parseInt(uriParts[uriParts.length - 1]);
        Movie newMovie = new Gson().fromJson(req.getReader(), Movie.class);

        for (Movie movie : movies) {
            if (movie.getId() == targetId) {
                if (newMovie.getTitle() != null) {
                    movie.setTitle(newMovie.getTitle());
                }
                if (newMovie.getRating() != null) {
                    movie.setRating(newMovie.getRating());
                }
                if (newMovie.getPoster() != null) {
                    movie.setPoster(newMovie.getPoster());
                }
                if (newMovie.getYear() != null) {
                    movie.setYear(newMovie.getYear());
                }
                if (newMovie.getGenre() != null) {
                    movie.setGenre(newMovie.getGenre());
                }
                if (newMovie.getDirector() != null) {
                    movie.setDirector(newMovie.getDirector());
                }
                if (newMovie.getPlot() != null) {
                    movie.setPlot(newMovie.getPlot());
                }
                if (newMovie.getActors() != null) {
                    movie.setActors(newMovie.getActors());
                }
            }
        }

        try {
            PrintWriter out = resp.getWriter();
            out.println(newMovie);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
