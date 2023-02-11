package org.pricegrab.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.pricegrab.ProductInfo;
import org.pricegrab.UserManagement;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

public class API {
    //just do API.post(); in main and see wassup, its gonna make you wait like 25sec after receiving the job_id before
    //  giving you the data. may say "status(working)" with no data, wait abit more. ¯\_(ツ)_/¯

    private String country;
    private String searchValue;
    private String username;
    private boolean loggedInOrNot;

    public API() {
        country = "us";
        searchValue = "iphone";
    }

    public API(String store, String country, String searchValue, String username) {
        this.country = "us";
        this.searchValue = "iphone";
        this.username = username;
    }

    public API(String country, String searchValue) throws IOException {
        this.country = country;
        this.searchValue = searchValue;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isLoggedInOrNot() {
        return loggedInOrNot;
    }

    public void setLoggedInOrNot(boolean loggedInOrNot) {
        this.loggedInOrNot = loggedInOrNot;
    }

    public void post(String store) throws IOException {
        //calls for the API to gather data, then gives us a "job id" for that call, which we will use to receive the
        //  gathered data from a Get call.
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://price-analytics.p.rapidapi.com/search-by-term"))
                .header("content-type", "application/x-www-form-urlencoded")
                .header("X-RapidAPI-Key", "d366a00dd9mshb2815bd04b4cc8ep108e2fjsne8b23608c808")
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
                        "src/main/java/org/pricegrab/APIResults.json", false)) {
                    fileWriter.write(response.body());
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (count > 0)
                System.out.println("almost there!...");
            count = +1;
        } while (response.body().startsWith("working", 11));

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());    // Because I am using LocalDateTime

        String jsonString = new String(
                Files.readAllBytes(Paths.get("src/main/java/org/pricegrab/APIResults.json")));
        Job job = mapper.readValue(jsonString, Job.class);

        ArrayList<ProductInfo> productInfos = new ArrayList<>();
        for (JobOffer jobOffers : job.results().get(0).content().offers()) {
            productInfos.add(
                    new ProductInfo(jobOffers.sellerUrl(), jobOffers.seller(), jobOffers.currency(),
                            jobOffers.name(), jobOffers.price()));
        }

        removeStringPrices(productInfos);

        // Sorts the productInfos array list by price
        Collections.sort(productInfos, new Comparator<ProductInfo>() {
            @Override
            public int compare(ProductInfo o1, ProductInfo o2) {
                return Float.valueOf(Float.parseFloat(o1.getPrice())).compareTo(Float.parseFloat(o2.getPrice()));
            }
        });

        printSearchResults(productInfos);

        if (loggedInOrNot) {
            System.out.println("\n-----------------------------------");
            System.out.println("\n\t● Would you like to add a product to your favorite list? y/n");
            Scanner sc = new Scanner(System.in);
            String yesOrNo = sc.nextLine();
            if (yesOrNo.equalsIgnoreCase("y"))
                addingToFavoriteList(productInfos, sc);
        }
    }

    public void addingToFavoriteList(ArrayList<ProductInfo> productInfos, Scanner sc) {
        ArrayList<Integer> productIds = new ArrayList<>();
        String done;
        do {
            System.out.print("Enter product ID: ");
            productIds.add(Integer.parseInt(sc.nextLine()) - 1);
            System.out.println("Are you done adding items to your favorite list? (y) or (n)");
            done = sc.nextLine();
        } while (!done.equalsIgnoreCase("y"));
        System.out.println("Items has been added to your favorite list successfully.");

        ArrayList<ProductInfo> favoriteList = new ArrayList<>();
        for (Integer productId : productIds) {
            favoriteList.add(productInfos.get(productId));
        }

        new UserManagement().insertFavoriteToDB(username, favoriteList);
    }


    //tests if a string can be a number/float
    public boolean isNumeric(String string) {
        float intValue;

        if (string == null || string.equals("")) {
            return false;
        }

        try {
            intValue = Float.parseFloat(string);
            return true;
        } catch (NumberFormatException e) {
        }
        return false;

    }

    //removes string prices
    public void removeStringPrices(ArrayList<ProductInfo> productInfos) {
        for (int i = 0; i < productInfos.size(); i++) {
            if (productInfos.get(i).getPrice() == null || !(isNumeric(productInfos.get(i).getPrice()))) {
                productInfos.remove(i);
                i = -1;
            }
        }

    }

    public void printSearchResults(ArrayList<ProductInfo> productInfos) {
        System.out.println("---------------Search Result--------------");
        for (int i = 0; i < productInfos.size(); i++) {
            System.out.println(i + 1 + "- " + "Price: " + productInfos.get(i).getPrice() + " " + productInfos.get(i).getCurrency() +
                    " -> " + productInfos.get(i).getName() + " -> " + productInfos.get(i).getSellerURL());
        }
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
                @JsonProperty("seller_url") String sellerUrl, String seller, String condition,
                int shipping, String currency, String price, String url, String name) {
}