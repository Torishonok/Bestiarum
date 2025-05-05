/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.torishonok.bestiarum;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;

/**
 *
 * @author vikus
 */
public class Depository {
    private static List<Creature> jsonStorage;
    private static List<Creature> yamlStorage;
    private static List<Creature> xmlStorage;

    public static List<Creature> getJsonStorage() {
        return jsonStorage;
    }

    public static List<Creature> getYamlStorage() {
        return yamlStorage;
    }

    public static List<Creature> getXmlStorage() {
        return xmlStorage;
    }

    
    
    public static void writeData(List<Creature> creatures, String ex) {
        switch (ex) {
            case ".xml":
                xmlStorage = creatures;
                break;
            case ".json":
                jsonStorage = creatures;
                break;
            case ".yaml":
                yamlStorage = creatures;
                break;
        }
    }

    public static boolean isEmpty(String storage) {
        boolean check = true;
        switch (storage) {
            case ".xml":
                if (xmlStorage != null) {
                    check = false;
                }
                break;
            case ".json":
                if (jsonStorage != null) {
                    check = false;
                }
                break;
            case ".yaml":
                if (yamlStorage != null) {
                    check = false;
                }
                break;
        }
        return check;
    }
    
    

    public static void editData(int creatureId, String vulnerabilities, int dangerLvl, String ex) {
        List<Creature> storage = null;
        switch (ex) {
        case ".xml":
            storage = xmlStorage;
            break;
        case ".json":
            storage = jsonStorage;
            break;
        case ".yaml":
            storage = yamlStorage;
            break;
        default:
            throw new IllegalArgumentException("Unsupported storage type: " + ex);
    }
    
        if (storage == null) {
        throw new IllegalStateException("Storage for " + ex + " is not initialized.");
    }
         // Проверяем, что creatureId корректен
    if (creatureId < 1 || creatureId > storage.size()) {
        throw new IndexOutOfBoundsException("Creature ID is out of bounds: " + creatureId);
    }

    // Проверяем, что vulnerabilities не null
    if (vulnerabilities == null) {
        throw new IllegalArgumentException("Vulnerabilities cannot be null.");
    }

    // Обновляем данные существа
    Creature creature = storage.get(creatureId - 1);
     if (creature == null) {
        throw new IllegalStateException("Creature with ID " + creatureId + " is null.");
    }
    creature.setDangerLevel(dangerLvl);
    creature.setVulnerabilities(vulnerabilities);
    }

}
