package data;

public class Movie {
    // Movie fields
    private String title;
    private Double rating;
    private String poster;
    private Integer year;
    private String genre;
    private String director;
    private String plot;
    private String actors;
    private int id;


    // Constructors
    public Movie() {
    }

    public Movie(String title, Double rating, String poster, Integer year, String genre, String director, String plot, String actors, int id) {
        this.title = title;
        this.rating = rating;
        this.poster = poster;
        this.year = year;
        this.genre = genre;
        this.director = director;
        this.plot = plot;
        this.actors = actors;
        this.id = id;
    }


    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", rating=" + rating +
                ", poster='" + poster + '\'' +
                ", year=" + year +
                ", genre='" + genre + '\'' +
                ", director='" + director + '\'' +
                ", plot='" + plot + '\'' +
                ", actors='" + actors + '\'' +
                ", id=" + id +
                '}';
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
