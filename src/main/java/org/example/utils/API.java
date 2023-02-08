package org.example.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.ProductInfo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

public class API {
    //just do API.post(); in main and see wassup, its gonna make you wait like 25sec after receiving the job_id before
    //  giving you the data. may say "status(working)" with no data, wait abit more. ¯\_(ツ)_/¯

    private final String country;
    private final String searchValue;

    public API(String store, String country, String searchValue) throws IOException {
        this.country = country;
        this.searchValue = searchValue;
        post(store);
    }

    public void post(String store) throws IOException {
        //calls for the API to gather data, then gives us a "job id" for that call, which we will use to receive the
        //  gathered data from a Get call.
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
        System.out.println("Fetching your request. hold on a moment!");
        //sends job id to the get method.
        if (response.body().charAt(9) == 'f') {
            get(response.body().substring(25, 49));
        } else {
            System.out.println("an error occurred");
            System.exit(0);
        }
    }

    public void get(String jobid) throws IOException {

        HttpResponse<String> response;
        //wait for the post call to finish its work before calling the get call, otherwise we will call, just to wait more.
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int count = 0;
        do {
            //if we call Get and its still on "Working" status, itll loop around and wait abit before calling again.
            try {
                    Thread.sleep(10000);
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

            if (response != null) {

                try (FileWriter fileWriter = new FileWriter(
                        "src/main/java/org/example/APIResults.json", false)) {
                    fileWriter.write(response.body());
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            if(count > 0)
                System.out.println("almost there!...");
            count =+ 1;
        } while (response.body().startsWith("working", 11));

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());    // Because I am using LocalDateTime

        String jsonString = new String(
                Files.readAllBytes(Paths.get("src/main/java/org/example/APIResults.json")));
        Job job = mapper.readValue(jsonString, Job.class);

        ArrayList<ProductInfo> productInfos = new ArrayList<>();
        for (JobOffer jobOffers : job.results().get(0).content().offers()) {
            productInfos.add(
                    new ProductInfo(jobOffers.sellerUrl(), jobOffers.seller(), jobOffers.currency(),
                            jobOffers.name(), jobOffers.price()));
        }
        Collections.sort(productInfos, new Comparator<ProductInfo>() {
            @Override
            public int compare(ProductInfo o1, ProductInfo o2) {
                return Integer.valueOf(o1.getPrice()).compareTo(o2.getPrice());
            }
        });

        printSearchResults(productInfos);
    }

    public void printSearchResults(ArrayList<ProductInfo> productInfos) {
        System.out.println("---------------Search Result--------------");

        for (int i = 0; i < productInfos.size(); i++) {
            System.out.println(i + 1 + "- " + "Price: " + productInfos.get(i).getPrice()+
                    " -> " + productInfos.get(i).getName()  );
        }

    }
    public void sortProductinfo(){

    }

}


record Job(@JsonProperty("job_id") String jobId, String status, List<JobResult> results) {
}


record JobResult(@JsonProperty("updated_at") LocalDateTime updatedAt, ResultQuery query,
                 JobOffers content, boolean success) {
}


record ResultQuery(@JsonProperty("max_age") int maxGae, @JsonProperty("max_pages") int maxPages,
                   String value, String key, String country, String topic, String source) {
}


record JobOffers(@JsonProperty("offers_count") int offersCount, List<JobOffer> offers) {
}


record JobOffer(@JsonProperty("review_count") Integer reviewCount,
                @JsonProperty("review_rating") Double reviewRating,
                @JsonProperty("seller_url") URL sellerUrl, String seller, String condition,
                int shipping, String currency, BigDecimal price, URL url, String name) {
}