package com.assignment.newsaggregator.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class NewsResponse implements Serializable {
    public void setTotalResults(Long totalResults) {
        this.totalPages = totalPages;
    }

    public void setArticles(List<NewsArticle> articles) {
        this.articles = articles;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public List<NewsArticle> getArticles() {
        return articles;
    }

    public long totalPages;
    List<NewsArticle> articles;
}
