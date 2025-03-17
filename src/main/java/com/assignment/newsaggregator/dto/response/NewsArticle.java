package com.assignment.newsaggregator.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
public class NewsArticle implements Serializable {
    private String headline;
    private String articleUrl;
    private String newsWebSite;

    public String getHeadline() {
        return headline;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public String getNewsWebSite() {
        return newsWebSite;
    }

    public NewsArticle(String headline, String articleUrl, String newsWebSite) {
        this.headline = headline;
        this.articleUrl = articleUrl;
        this.newsWebSite = newsWebSite;
    }


}
