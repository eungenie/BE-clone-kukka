package com.clone.kukka;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class KukkaFlowerDataService {
    private static String KUKKA_FLOWER_DATA_URL = "https://kukka.kr/contents/2356/";

    @PostConstruct
    public void getFlowerData() throws IOException {
        Document doc = Jsoup.connect(KUKKA_FLOWER_DATA_URL).get();
        System.out.println(doc);
    }
}
