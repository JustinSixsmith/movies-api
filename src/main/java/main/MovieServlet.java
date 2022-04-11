package main;

import com.google.gson.Gson;
import data.*;

import java.io.*;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "main.MovieServlet", urlPatterns = "/movies/*")
public class MovieServlet extends HttpServlet {
    private MoviesDao dao = MoviesDaoFactory.getMoviesDao(MoviesDaoFactory.DaoType.MYSQL);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");

        try {
            PrintWriter out = response.getWriter();
            String movieString = new Gson().toJson(dao.all().toArray());
            out.println(movieString);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        Movie[] newMovies = new Gson().fromJson(request.getReader(), Movie[].class);

        try {
            dao.insertAll(newMovies);
            PrintWriter out = response.getWriter();
            out.println("Movie(s) created");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] uriParts = req.getRequestURI().split("/");
        int targetId = Integer.parseInt(uriParts[uriParts.length - 1]);
        Movie updatedMovie = new Gson().fromJson(req.getReader(), Movie.class);
        updatedMovie.setId(targetId);

        try {
            dao.update(updatedMovie);
            PrintWriter out = resp.getWriter();
            out.println(updatedMovie);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] uriParts = req.getRequestURI().split("/");
        int targetId = Integer.parseInt(uriParts[uriParts.length - 1]);

            try {
                dao.delete(targetId);
                PrintWriter out = resp.getWriter();
                out.println("Movie deleted");
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
    public void destroy() {
            try {
                dao.cleanUp();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
