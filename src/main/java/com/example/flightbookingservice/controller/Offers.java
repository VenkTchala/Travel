package com.example.flightbookingservice.controller;

import com.fasterxml.jackson.core.JsonParser;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

@Getter
@Slf4j
public class Offers {
    @Getter
 private static JSONArray jsonArray = new JSONArray();
 static {
//     JSONObject jo = new JSONObject();
//     jo.put("origin","");
//     jo.put("Destination", "");
//     jo.put("airline","");
//     jo.put("available", 20);
//     jo.put("connections", 1);
//     jo.put("price" ,  20);
 }


}
