package com.assignment.newsaggregator.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class GuardianResponse {

    public GuardianResponseData getResponse() {
        return response;
    }

    private GuardianResponseData response;

}