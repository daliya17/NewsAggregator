package com.assignment.newsaggregator.service;

import com.assignment.newsaggregator.dto.response.GuardianResponse;
import com.assignment.newsaggregator.dto.response.NewYorkTimesResponse;
import com.assignment.newsaggregator.dto.response.NewsArticle;
import com.assignment.newsaggregator.dto.response.NewsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsService {


    @Value("${api.guardian.key}")
    private String guardianApiKey;

    @Value("${api.nytimes.key}")
    private String nyTimesApiKey;

    private static final String GUARDIAN_URL = "https://content.guardianapis.com/search?page={page}&q={query}&api-key={apiKey}";
    private static final String NYTIMES_URL = "https://api.nytimes.com/svc/search/v2/articlesearch.json?page={page}&q={query}&api-key={apiKey}";


    @Cacheable(value = "newsCache", key = "#query", unless = "#result == null")
    public NewsResponse fetchNews(String query, int pageNumber, int pageSize) {
        CompletableFuture<List<NewsArticle>> guardianResponseCompletableFuture = CompletableFuture
                .supplyAsync(() -> fetchGuardianNews(query, pageNumber))
                .thenApply(response -> mapGuardianResponse(response, pageSize / 2));
        CompletableFuture<List<NewsArticle>> nyTimesResponseCompletableFuture = CompletableFuture
                .supplyAsync(() -> fetchNyTimesNews(query, pageNumber))
                .thenApply(response -> mapNyTimesResponse(response, pageSize / 2));

//        CompletableFuture<NewsResponse> combinedNewsFuture = CompletableFuture.allOf(guardianResponseCompletableFuture, nyTimesResponseCompletableFuture)
//                .thenApply(v -> {
//                    System.out.println("Combining news started at :" + System.currentTimeMillis());
//                    GuardianResponse guardianResponse = guardianResponseCompletableFuture.join();
//                    NewYorkTimesResponse nyTimesResponse = nyTimesResponseCompletableFuture.join();
//                    if (guardianResponse != null && guardianResponse.getResponse().getResults() != null) {
//                        newsResponse.getArticles().addAll(mapGuardianResponse(guardianResponse).subList(0, pageSize / 2));
//                        newsResponse.totalPages = guardianResponse.getResponse().getPages() / pageSize;
//                    }
//                    if (nyTimesResponse != null && nyTimesResponse.getResponse() != null) {
//                        newsResponse.getArticles().addAll(mapNyTimesResponse(nyTimesResponse).subList(0, pageSize / 2));
//                        newsResponse.totalPages += nyTimesResponse.getResponse().getMeta().getHits() / pageSize;
//                    }
//                    newsResponse.setArticles(newsResponse.getArticles().stream()
//                            .distinct()
//                            .collect(Collectors.toList()));
//                    System.out.println("Combining news completed at :" + System.currentTimeMillis());
//                    return newsResponse;
//                });
//        System.out.println("Fetching news completed at :" + System.currentTimeMillis());
        return guardianResponseCompletableFuture.thenCombine(nyTimesResponseCompletableFuture, (guardianArticles, nyTimesArticles) -> {
            NewsResponse newsResponse = new NewsResponse();
            newsResponse.setArticles(new ArrayList<>());
            if (guardianArticles != null)
                newsResponse.getArticles().addAll(guardianArticles);
            if (nyTimesArticles != null)
                newsResponse.getArticles().addAll(nyTimesArticles);
            newsResponse.setArticles(newsResponse.getArticles().stream()
                    .distinct()
                    .collect(Collectors.toList()));
            return newsResponse;
        }).join();
    }

    private GuardianResponse fetchGuardianNews(String query, int page) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject(GUARDIAN_URL, GuardianResponse.class, page, query, guardianApiKey);
        } catch (Exception e) {
            return null;
        }
    }

    private NewYorkTimesResponse fetchNyTimesNews(String query, int page) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject(NYTIMES_URL, NewYorkTimesResponse.class, page, query, nyTimesApiKey);
        } catch (Exception e) {
            return null;
        }
    }

    private List<NewsArticle> mapGuardianResponse(GuardianResponse guardianResponse, int limit) {
        if (guardianResponse == null || guardianResponse.getResponse().getResults() == null)
            return new ArrayList<>();
        return guardianResponse.getResponse().getResults().stream()
                .map(result -> new NewsArticle(result.getWebTitle(), result.getWebUrl(), "The Guardian"))
                .limit(limit)
                .collect(Collectors.toList());
    }

    private List<NewsArticle> mapNyTimesResponse(NewYorkTimesResponse nyTimesResponse, int limit) {
        if (nyTimesResponse == null || nyTimesResponse.getResponse() == null || nyTimesResponse.getResponse().getDocs() == null)
            return new ArrayList<>();
        return nyTimesResponse.getResponse().getDocs().stream()
                .map(doc -> new NewsArticle(doc.getHeadline() != null && doc.getHeadline().getPrintHeadLine() != null ?
                        doc.getHeadline().getPrintHeadLine() : doc.getLeadParagraph(),
                        doc.getWebUrl(), doc.getSource() != null ? doc.getSource() : "The New York Times"))
                .limit(limit)
                .collect(Collectors.toList());
    }
}
