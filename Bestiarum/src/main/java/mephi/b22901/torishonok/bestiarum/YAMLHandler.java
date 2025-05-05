/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.torishonok.bestiarum;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author vikus
 */
public class YAMLHandler extends BaseHandler {

    public YAMLHandler() {
        super(".yaml");
    }

    @Override
    protected List<Creature> readData(String path) {
    List<Creature> bestiarium = new ArrayList<>();
    try (InputStream inputStream = new FileInputStream(new File(path))) {
        Yaml yaml = new Yaml();
        List<Map<String, Object>> data = yaml.load(inputStream);
        for (Map<String, Object> creatureData : data) {
            Creature creature = Creature.fromMap(creatureData);
            bestiarium.add(creature);
        }
    } catch (FileNotFoundException ex) {
        Logger.getLogger(YAMLHandler.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
        Logger.getLogger(YAMLHandler.class.getName()).log(Level.SEVERE, null, ex);
    }
    return bestiarium;
}

@Override
protected void writeData(String path) {
    List<Creature> creatures = getCreatures();
    Yaml yaml = new Yaml();
    try (FileWriter writer = new FileWriter(path)) {
        List<Map<String, Object>> data = new ArrayList<>();
        for (Creature creature : creatures) {
            data.add(creature.toMap());
        }
        yaml.dump(data, writer);
    } catch (IOException ex) {
        Logger.getLogger(YAMLHandler.class.getName()).log(Level.SEVERE, null, ex);
    }}

    
    private List<Creature> getCreatures() {
         
    List<Creature> creatures = Depository.getYamlStorage();
    if (creatures == null) {
      
        return new ArrayList<>();
    }
    return creatures;
    }
}
