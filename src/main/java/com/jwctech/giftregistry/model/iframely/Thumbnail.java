package com.jwctech.giftregistry.model.iframely;

import lombok.*;

import java.util.ArrayList;

@Data
public class Thumbnail{
    public String href;
    public String type;
    public ArrayList<String> rel;
    public int content_length;
    public Media media;
}