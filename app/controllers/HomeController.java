package controllers;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.*;
import com.fasterxml.jackson.core.type.*;
import play.mvc.*;
import java.util.*;
import java.io.*;
import java.lang.*;

public class HomeController extends Controller {
    public Result get() {
      List<String> notejson = getJson();
      ObjectMapper mapper = new ObjectMapper();
      ArrayNode result = mapper.createArrayNode();
      for(String s: notejson) {
        result.add(s);
      }
      return ok(result);
    }

    public Result post() {
      String text = request().body().asText();
      System.out.println("text: " + text);
      List<String> noteJson = getJson();
      noteJson.add(text);
      saveJson(noteJson);
      return ok(text);

    }

    private List<String> getJson() {
      try {
        ObjectMapper mapper = new ObjectMapper();
      return mapper.readValue(new File("/tmp/notes.json"), new TypeReference<List<String>>(){});
      } catch(Exception ex){
        throw new RuntimeException(ex);
      }
    } 
    
    private void saveJson(List<String> noteJson) {
      try {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("/tmp/notes.json"), noteJson);
      } catch(Exception ex){
        throw new RuntimeException(ex);
      }
    } 
}
