import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.Arrays;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName){
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies(){return movies;}
    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t")) searchTitles();
        else if (option.equals("c")) searchCast();
        else if (option.equals("k")) searchKeywords();
        else if (option.equals("g")) listGenres();
        else if (option.equals("r")) listHighestRated();
        else if (option.equals("h")) listHighestRevenue();
        else System.out.println("Invalid choice!");
    }

    private void searchTitles()
    {
        System.out.print("Enter a tital search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.contains(searchTerm))
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = results.get(choice - 1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();
            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast(){
        System.out.print("Enter the name of a cast member: ");
        String searchTerm = scanner.nextLine();
        searchTerm = searchTerm.toLowerCase();
        // arraylist to hold search results
        ArrayList<String> results = new ArrayList<String>(); // creates a list of strings to get a cast name of anyone who has a name in searchTerm
        // search through ALL movies in collection
        // iterates through each movie to find
        for (Movie movie : movies) {
            String[] movieTitle = movie.getCast().split("[|]");
            for (String s : movieTitle) {
                if (s.toLowerCase().contains(searchTerm) && !results.contains(s)) results.add(s);
            }
        }
        for(int i = 0; i < results.size(); i ++){
            System.out.println(i+1 + "." + results.get(i));
        }
        System.out.println("Which actor would you like to choose? (Choose a number from the list above)");
        int itemInList = scanner.nextInt() - 1;
        ArrayList<Movie> actorInMov = new ArrayList<Movie>();
        for(Movie mov : movies){
            if(mov.getCast().contains(results.get(itemInList))){
                actorInMov.add(mov);
            }
        }
        for(int i = 0; i < actorInMov.size(); i++){
            System.out.println(i + 1 + "." + actorInMov.get(i).getTitle());
        }
        System.out.println("Which movie would you like to check? (Choose a number from the list above");
        itemInList = scanner.nextInt();
        displayMovieInfo(actorInMov.get(itemInList-1));
        scanner.nextLine();
    }

    private void searchKeywords(){
        System.out.println("Enter a Keyword search term: ");
        String searchTerm = scanner.nextLine();
        for(Movie mov : movies){
            if(mov.getKeywords().toLowerCase().contains(searchTerm.toLowerCase()));
        }
    }

    private void listGenres() {
        ArrayList<String> genres = new ArrayList<String>();
        for (Movie movie : movies) {
            String[] movieGenres = movie.getGenres().split("\\|");
            for (String movieGenre : movieGenres) if (!genres.contains(movieGenre)) genres.add(movieGenre);
        }
        for (int j = 1; j < genres.size(); j++) {
            String temp = genres.get(j);
            int maybeIndex = j;
            while (maybeIndex > 0 && temp.compareTo(genres.get(maybeIndex - 1)) < 0) {
                genres.set(maybeIndex, genres.get(maybeIndex - 1));
                maybeIndex--;
            }
            genres.set(maybeIndex, temp);
        }
        for (int i = 0; i < genres.size(); i++) {
            String title = genres.get(i);
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + title);
        }
        System.out.print("Chose a genre number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        ArrayList<Movie> movieResults = new ArrayList<Movie>();
        for (Movie m : movies) {
            if (m.getGenres().contains(genres.get(choice - 1))) {
                movieResults.add(m);
            }
        }
        for(int i = 0; i < movieResults.size(); i++){
            System.out.println(i + 1 + "." + movieResults.get(i).getTitle());
        }
        System.out.println("Choose a movie to learn about: ");
        choice = scanner.nextInt() - 1;
        scanner.nextLine();
        displayMovieInfo(movieResults.get(choice));
    }

    private void listHighestRated() {
        ArrayList<Movie> topMovies = new ArrayList<Movie>();
        topMovies.add(movies.get(0));
        for (Movie mov : movies) {
            int i = 0;
            while (mov.getUserRating() < topMovies.get(i).getUserRating() && i < topMovies.size() - 1) i++;
            topMovies.add(i, mov);
        }
        for (int i = 0; i < 50; i++) {
            String title = topMovies.get(i).getTitle();
            double rating = topMovies.get(i).getUserRating();
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + title + ": " + rating);
        }
        System.out.println("Choose a movie to learn about: ");
        int choice = scanner.nextInt() + 1;
        displayMovieInfo(topMovies.get(choice));
        scanner.nextLine();
    }

    private void listHighestRevenue(){
        ArrayList<Movie> richMovie = new ArrayList<Movie>();
        richMovie.add(movies.get(0));
        for (Movie mov : movies){
            int i = 0;
            while (mov.getRevenue() < richMovie.get(i).getRevenue() && i < richMovie.size() - 1) i++;
            richMovie.add(i, mov);
        }
        for (int i = 0; i < 50; i++){
            String title = richMovie.get(i).getTitle();
            int revenue = richMovie.get(i).getRevenue();
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + title + ": " + revenue);
        }
        System.out.println("Choose a movie to learn about: ");
        int choice = scanner.nextInt() + 1;
        displayMovieInfo(richMovie.get(choice));
        scanner.nextLine();
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}
