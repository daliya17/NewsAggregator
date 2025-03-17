package com.assignment.newsaggregator.dto.response;

import lombok.Data;

@Data
public class GuardianResponseResult {
    private String id;
    private String type;

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getSectionId() {
        return sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public boolean isHosted() {
        return isHosted;
    }

    public String getPillarId() {
        return pillarId;
    }

    public String getPillarName() {
        return pillarName;
    }

    private String sectionId;
    private String sectionName;
    private String webPublicationDate;
    private String webTitle;
    private String webUrl;
    private String apiUrl;
    private boolean isHosted;
    private String pillarId;
    private String pillarName;
}
