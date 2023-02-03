package org.example.utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class API {
    // just do API.post(); in main and see wassup, its gonna make you wait like 20sec after receiving the job_id before
    // giving you the data. may say "status(working)" with no data, wait abit more ¯\_(ツ)_/¯

    private final String country;
    private final String searchValue;

    public API(String[] stores, String country, String searchValue) {
        this.country = country;
        this.searchValue = searchValue;
        for (int i = 0; i < stores.length; i++) {
            post(stores[i]);
        }
    }

    public void post(String store) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://price-analytics.p.rapidapi.com/search-by-term"))
                .header("content-type", "application/x-www-form-urlencoded")
                .header("X-RapidAPI-Key", "86f79dead5mshdd5051a62609c27p10e6c0jsnb915eee2251c")
                .header("X-RapidAPI-Host", "price-analytics.p.rapidapi.com")
                .method("POST", HttpRequest.BodyPublishers.ofString(
                        "source=" + store + "&country=" + country + "&values=" + searchValue))
                .build();
        HttpResponse<String> response;
        try {
            response =
                    HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(response.body());
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (response.body().charAt(9) == 'f') {
            get(response.body().substring(25, 49));
        } else {
            System.out.println("an error occurred");
            System.exit(0);
        }
    }

    public void get(String jobid) {
        HttpResponse<String> response;
        do {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://price-analytics.p.rapidapi.com/poll-job/" + jobid))
                    .header("X-RapidAPI-Key", "86f79dead5mshdd5051a62609c27p10e6c0jsnb915eee2251c")
                    .header("X-RapidAPI-Host", "price-analytics.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();

            try {
                response = HttpClient.newHttpClient()
                        .send(request, HttpResponse.BodyHandlers.ofString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println(response.body());
        } while (response.body().startsWith("working", 11));

    }
}
