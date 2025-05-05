/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.torishonok.bestiarum;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vikus
 */
public class ChangeInfo {
    private final View view;
    private final XMLHandler xml;
    private final JSONHandler json;
    private final YAMLHandler yaml;

    public ChangeInfo() throws URISyntaxException {
        this.view = new View(this);
        xml = new XMLHandler();
        json = new JSONHandler();
        yaml = new YAMLHandler();
        xml.setNext(json).setNext(yaml);
    }

    public List<Creature> importData(String path) {
        return (xml.doImport(path));
    }
    
    public void exportData(String path){
        xml.doExport(path);
    }
    
    public void saveToStorage(int creatureId, String vulnerabilities, int dangerLvl, String ex){
        Depository.editData(creatureId, vulnerabilities, dangerLvl, ex);
    }
}
