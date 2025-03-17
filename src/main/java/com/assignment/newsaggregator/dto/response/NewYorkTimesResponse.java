package com.assignment.newsaggregator.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
@Data
public class NewYorkTimesResponse implements Serializable {
    private String status;
    private String copyright;

    public ResponseData getResponse() {
        return response;
    }

    private ResponseData response;

    @Getter
    @Data
    public static class ResponseData {
        private List<Article> docs;

        public Meta getMeta() {
            return meta;
        }

        private Meta meta;

        public List<Article> getDocs() {
            return docs;
        }
    }

    public static class Meta {
        private int hits;

        public int getHits() {
            return hits;
        }

        public int getOffset() {
            return offset;
        }

        public int getTime() {
            return time;
        }

        private int offset;
        private int time;
    }

    @Data
    public static class Article {
        public String getWebUrl() {
            return webUrl;
        }

        @JsonProperty("web_url")
        private String webUrl;
        private String snippet;

        public String getAbstractText() {
            return abstractText;
        }

        private String abstractText;

        public String getLeadParagraph() {
            return leadParagraph;
        }

        @JsonProperty("lead_paragraph")
        private String leadParagraph;

        private String print_section;
        private String print_page;

        private String source;

        public String getSource() {
            return source;
        }

        private List<Multimedia> multimedia;

        private HeadLine headline;
        public HeadLine getHeadline() {
            return headline;
        }

    }

    @Data
    public static class Multimedia {
        private int rank;
        private String subtype;
        private String caption;
        private String credit;
        private String type;
        private String url;
        private int height;
        private int width;
        private Legacy legacy;
        private String cropName;
    }

    @Data
    public static class Legacy {
        private String xlarge;
        private int xlargewidth;
        private int xlargeheight;
    }

    @Getter
    @Data
    public static class HeadLine {
        private String main;

        public String getPrintHeadLine() {
            return printHeadLine;
        }

        @JsonProperty("print_headline")
        private String printHeadLine;
        private String kicker;
    }
}
