package com.assignment.newsaggregator.controller;

import com.assignment.newsaggregator.dto.response.NewsArticle;
import com.assignment.newsaggregator.dto.response.NewsResponse;
import com.assignment.newsaggregator.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @Operation(summary = "Get News Articles", description = "Fetch news articles based on the query")
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/search")
    public ResponseEntity<NewsResponse> getNews(@Parameter(description = "Search Query") @RequestParam String query,
                                                @Parameter(description = "Page Number") @RequestParam(required = false, defaultValue = "0") int pageNumber,
                                                @Parameter(description = "Page Size") @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(newsService.fetchNews(query, pageNumber, pageSize));

    }
}


