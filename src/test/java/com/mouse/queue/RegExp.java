package com.mouse.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author gongchangyou
 * @version 1.0
 * @date 2022/12/5 20:29
 */
@SpringBootTest
public class RegExp {
    @Test
    void contextLoads() throws InterruptedException {
        List<String> contentList = new ArrayList<>() {{
            add("xxx{ \"a\":\"{b}\"}yyy");
            add("{ \"a\":\"{b}\"}");
            add("xxx{ \"a\":\"{b}\"}");
            add("{ \"a\":\"{b}\"}yyy");
            add("");
            add("{ \"a\",\"{b}\"}yyy");
        }};

        val pattern = Pattern.compile("(?<prefix>.*?)(?<json>\\{.*})(?<suffix>.*$)");
        val objectMapper = new ObjectMapper();
        for (val content : contentList) {
            val matcher = pattern.matcher(content);
            System.out.println(matcher);
            if (matcher.find()) {
                System.out.println(matcher.group("prefix"));
                System.out.println(matcher.group("json"));
                //convert json
                try {
                    val map = objectMapper.readValue(matcher.group("json"), Map.class);
                    map.put("a", "c");
                    System.out.println(map);
                } catch (JsonProcessingException e) {
                    //无法解析json,将这次的变更写入
                    throw new RuntimeException();
                }
                System.out.println(matcher.group("suffix"));

            } else {
                //replace it
                System.out.println("replace");
            }

        }
    }
}
