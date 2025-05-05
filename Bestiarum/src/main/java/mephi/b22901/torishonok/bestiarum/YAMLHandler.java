/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.torishonok.bestiarum;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(new File(path));
            Yaml yaml = new Yaml();
            List<Map<String, Object>> data = yaml.load(inputStream);
            for (Map<String, Object> creatureData : data) {
                Creature creature = new Creature();
                creature.setId((Integer) creatureData.get("id"));
                creature.setName((String) creatureData.get("name"));
                creature.setDescription((String) creatureData.get("description"));
                creature.setDangerLevel((Integer) creatureData.get("dangerLevel"));
                creature.setHabitat((String) creatureData.get("habitat"));
                creature.setActivity((String) creatureData.get("activity"));
                creature.setFirstMentioned((String) creatureData.get("firstMentioned"));
                creature.setImmunities((String) creatureData.get("immunities"));
                creature.setVulnerabilities((String) creatureData.get("vulnerabilities"));
                creature.setHeight((String) creatureData.get("height"));
                creature.setWeight((String) creatureData.get("weight"));
                creature.setPoisonRecipe((String) creatureData.get("poisonRecipe"));
                creature.setTime((Integer) creatureData.get("time"));
                creature.setEfficiency((String) creatureData.get("efficiency"));
                creature.setRecievedFrom(extension);
                bestiarium.add(creature);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(YAMLHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bestiarium;
    }

    @Override
    protected void writeData(String path) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
