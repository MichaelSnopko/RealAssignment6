/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;



/**
 *
 * @author Michael
 */
@ApplicationScoped
public class MessageController {
    
    
    private SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    List<Message> message;
    
    
    public MessageController() {
        message = new ArrayList<>();
    }
    
    //GET ALL
    public JsonArray getallJson(){
    JsonArrayBuilder json = Json.createArrayBuilder();
            for (Message m : message){
               json.add(m.toJSON());
}
            return json.build();
    }
    
    //GET BY ID
    public Message getById(int id) {
        for (Message m : message){
        if(m.getMessageId() == id){
            return m;
        }
        }
        return null;
    }
    
    public JsonObject getByIdJson(int id) {
        Message m = getById(id);
        if (m != null) {
            return getById(id).toJSON();
        } else {
            return null;
        }
    }
    
    //GET DATA
    public JsonArray getByDateJson(Date from, Date to) {
        JsonArrayBuilder json = Json.createArrayBuilder();
        for (Message m : message) {
            if ((m.getSentTime().after(from) && m.getSentTime().before(to))
                    || m.getSentTime().equals(from) || m.getSentTime().equals(to)) {
                json.add(m.toJSON());
            }
        }
        return json.build();
    }
    
    //ADD
    public JsonObject add(JsonObject json) {       
        Message m = new Message(json);
        message.add(m);
        return m.toJSON();
    }
    
    //EDIT 
    public JsonObject edit(int id, JsonObject json) {
        Message m = getById(id);
            m.setTitle(json.getString("title", ""));
            m.setContents(json.getString("contents", ""));
            m.setAuthor(json.getString("author", ""));
            String time = json.getString("sentTime", "");
            try{
            m.setSentTime(sd.parse(time));
            } catch (ParseException ex) {
            m.setSentTime(new Date());
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, "Failed Parsing Date: " + time);
        }
        return m.toJSON();
    }
    
    //DELETE
    public boolean deleteById(int id) {
   Message m = getById(id);
        if (m != null) {
            message.remove(m);
            return true;
        } else {
            return false;
        }
    }

}

    
