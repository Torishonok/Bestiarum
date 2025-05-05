/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.torishonok.bestiarum;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author vikus
 */
    
public class Creature {
    private int id;
    private String name;
    private String description;
    private int dangerLevel;
    private String habitat;
    private String activity;
    private String firstMention;
    private String immunities;
    private String vulnerabilities;
    private String height;
    private String weight;
    private String poisonRecipe;
    private int time;
    private String efficiency;
    @JsonIgnore
    private String recievedFrom;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getDangerLevel() {
        return dangerLevel;
    }

    public String getHabitat() {
        return habitat;
    }

    public String getActivity() {
        return activity;
    }

    public String getFirstMentioned() {
        return firstMention;
    }

    public String getImmunities() {
        return immunities;
    }

    public String getVulnerabilities() {
        return vulnerabilities;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getPoisonRecipe() {
        return poisonRecipe;
    }

    public int getTime() {
        return time;
    }

    public String getEfficiency() {
        return efficiency;
    }

    public String getRecievedFrom() {
        return recievedFrom;
    }

    public void setDangerLevel(int dangerLevel) {
        this.dangerLevel = dangerLevel;
    }

    public void setVulnerabilities(String vulnerabilities) {
        this.vulnerabilities = vulnerabilities;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public void setFirstMentioned(String firstMentioned) {
        this.firstMention = firstMentioned;
    }

    public void setImmunities(String immunities) {
        this.immunities = immunities;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setPoisonRecipe(String poisonRecipe) {
        this.poisonRecipe = poisonRecipe;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setEfficiency(String efficiency) {
        this.efficiency = efficiency;
    }

    public void setRecievedFrom(String recievedFrom) {
        this.recievedFrom = recievedFrom;
    }

    @Override
    public String toString() {
        return name;
    }

}