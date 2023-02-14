package org.erkebaev;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();


        // get
        /*String url = "https://reqres.in/api/users/2";
        String response = restTemplate.getForObject(url, String.class);*/

        // post
        Map<String, String> jsonToSend = new HashMap<>();
        jsonToSend.put("name","Test name");
        jsonToSend.put("job","Test job");
        String url = "https://reqres.in/api/users";
        HttpEntity<Map<String, String>> request = new HttpEntity<>(jsonToSend);
        String response = restTemplate.postForObject(url, request, String.class);

        System.out.println(response);
    }
}
