package com.assignment.newsaggregator.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class GuardianResponseData {
    private String status;
    private String userTier;
    private int total;
    private int startIndex;
    private int pageSize;
    private int currentPage;
    private int pages;

    public String getStatus() {
        return status;
    }

    public String getUserTier() {
        return userTier;
    }

    public int getTotal() {
        return total;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPages() {
        return pages;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public List<GuardianResponseResult> getResults() {
        return results;
    }

    private String orderBy;
    private List<GuardianResponseResult> results;
}
