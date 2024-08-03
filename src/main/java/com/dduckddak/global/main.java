package com.dduckddak.global;서울시 행정동 json

import net.minidev.json.JSONArray;
import net.minidev.json.parser.JSONParser;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.io.FileReader;
import java.io.Reader;

@Configuration
public class main implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {

        JSONParser parser = new JSONParser();
        // JSON 파일 읽기
        Reader reader = new FileReader("resources/json/길단위인구.json");
        JSONArray dateArray = (JSONArray) parser.parse(reader);

        System.out.println(dateArray.get(0));
    }
}
