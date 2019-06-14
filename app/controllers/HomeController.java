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
      List<List<String>> notejson = getJson();
      ObjectMapper mapper = new ObjectMapper();
      ArrayNode result = mapper.createArrayNode();
      for (List<String> l : notejson) {
        for (String i : l) {
          result.add(i);
        }
      }      
      return ok(result);
    }

    public Result post() {
      String text = request().body().asText();
      System.out.println("text: " + text);
      List<List<String>> noteJson = getJson();
      List<String> textl = new ArrayList<String>();
      textl.add(text);
      noteJson.add(textl);
      saveJson(noteJson);
      return ok(text);
    }

    public Result reset() {
        System.out.println("RESET!");
      try {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        ArrayList<String> textl = new ArrayList<String>();
        textl.add("false");
        textl.add("filler");
        list.add(textl);
        mapper.writeValue(new File("/tmp/notes.json"), list);
      } catch(Exception ex){
        throw new RuntimeException(ex);
      }
      return ok("");
    }

    private List<List<String>> getJson() {
      try {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File("/tmp/notes.json"), new TypeReference<List<List<String>>>(){});
      } catch(Exception ex){
        throw new RuntimeException(ex);
      }
    } 
    
    private void saveJson(List<List<String>> noteJson) {
      try {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("/tmp/notes.json"), noteJson);
      } catch(Exception ex){
        throw new RuntimeException(ex);
      }
    } 
}
