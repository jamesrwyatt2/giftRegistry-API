package com.jwctech.giftregistry.model.iframely;

import lombok.*;

import java.util.ArrayList;

@Data
public class IFramelyResponse {

        public String url;
        public Meta meta;
        public Links links;
        public ArrayList<String> rel;
        public String html;

}
