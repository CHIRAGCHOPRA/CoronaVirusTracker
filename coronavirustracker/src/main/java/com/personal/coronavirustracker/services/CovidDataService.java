package com.personal.coronavirustracker.services;

import com.personal.coronavirustracker.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CovidDataService {
    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private List<LocationStats> allStats = new ArrayList<>();

    public List<LocationStats> getAllStats() {
        return allStats;
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusData() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        List<LocationStats> newStats = new ArrayList<>();
        for (CSVRecord record : records) {
            LocationStats locationStats = new LocationStats();

           locationStats.setState(record.get("Province/State"));
           locationStats.setCountry(record.get("Country/Region"));
           locationStats.setLatestTotalCases(Integer.parseInt(record.get(record.size() - 2)));
           int latestCases = Integer.parseInt(record.get(record.size() - 2));
            int prevDayCases = Integer.parseInt(record.get(record.size() - 3));
           locationStats.setDiffFromPrevDay(latestCases - prevDayCases);
           newStats.add(locationStats);

        }
        this.allStats = newStats;

    }
}
