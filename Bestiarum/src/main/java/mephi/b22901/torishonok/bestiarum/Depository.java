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
        switch (ex) {
            case ".xml":
                xmlStorage.get(creatureId - 1).setDangerLevel(dangerLvl);
                xmlStorage.get(creatureId-1).setVulnerabilities(vulnerabilities);
                break;
            case ".json":
                jsonStorage.get(creatureId - 1).setDangerLevel(dangerLvl);
                jsonStorage.get(creatureId-1).setVulnerabilities(vulnerabilities);
                break;
            case ".yaml":
                yamlStorage.get(creatureId - 1).setDangerLevel(dangerLvl);
                yamlStorage.get(creatureId-1).setVulnerabilities(vulnerabilities);
                break;
        }
    }

}
