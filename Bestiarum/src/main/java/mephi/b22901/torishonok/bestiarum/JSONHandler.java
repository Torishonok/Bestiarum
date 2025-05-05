/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.torishonok.bestiarum;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author vikus
 */
public class JSONHandler extends BaseHandler {

    public JSONHandler() {
        super(".json");
    }

    @Override
    protected List<Creature> readData(String path) {
        ObjectMapper mapper = new ObjectMapper();
        List<Creature> creatures = null;
        File file = new File(path);
        try {
            creatures = mapper.readValue(file, new TypeReference<List<Creature>>() {});
            for (Creature creature : creatures) {
                if (creature.getRecievedFrom() == null) {
                    creature.setRecievedFrom(extension); 
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(JSONHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return creatures;
    }

    @Override
    protected void writeData(String path) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(path), Depository.getJsonStorage());
        } catch (IOException ex) {
            Logger.getLogger(JSONHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}