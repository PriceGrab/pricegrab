package utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class API {
    // just do API.post(); in main and see wassup, its gonna make you wait like 20sec after receiving the job_id before
    // giving you the data, otherwise you will receive "status(working)" with no data, if so then try again
    public static void post() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://price-analytics.p.rapidapi.com/search-by-term"))
                .header("content-type", "application/x-www-form-urlencoded")
                .header("X-RapidAPI-Key", "86f79dead5mshdd5051a62609c27p10e6c0jsnb915eee2251c")
                .header("X-RapidAPI-Host", "price-analytics.p.rapidapi.com")
                .method("POST", HttpRequest.BodyPublishers.ofString("source=amazon&country=us&values=iphone%2011"))
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(response.body());
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (response.body().substring(9, 10).equals("f")) {
            get(response.body().substring(25, 49));
        } else {
            System.out.println("an error occurred");
            System.exit(0);
        }
    }

    public static void get(String i) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://price-analytics.p.rapidapi.com/poll-job/" + i))
                .header("X-RapidAPI-Key", "86f79dead5mshdd5051a62609c27p10e6c0jsnb915eee2251c")
                .header("X-RapidAPI-Host", "price-analytics.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;

        {
            try {
                response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println(response.body());

        }
    }
}