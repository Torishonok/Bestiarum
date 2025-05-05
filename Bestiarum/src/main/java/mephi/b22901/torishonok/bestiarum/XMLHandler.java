/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.torishonok.bestiarum;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author vikus
 */
public class XMLHandler extends BaseHandler {

    public XMLHandler() {
        super(".xml");
        
    }

    @Override
    protected List<Creature> readData(String path) {
        List<Creature> creatures = new ArrayList<>();
        Creature creature = null;
        XMLInputFactory input = XMLInputFactory.newInstance();
        try {
            XMLEventReader reader = input.createXMLEventReader(new FileInputStream(path));
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startEl = event.asStartElement();
                    if (startEl.getName().getLocalPart().equals("creature")) {
                        creature = new Creature();
                        creature.setRecievedFrom(super.extension);
                    } else if (startEl.getName().getLocalPart().equals("id")) {
                        event = reader.nextEvent();
                        creature.setId(Integer.parseInt(event.asCharacters().getData()));
                    } else if (startEl.getName().getLocalPart().equals("name")) {
                        event = reader.nextEvent();
                        creature.setName(event.asCharacters().getData());
                    } else if (startEl.getName().getLocalPart().equals("description")) {
                        event = reader.nextEvent();
                        creature.setDescription(event.asCharacters().getData());
                    } else if (startEl.getName().getLocalPart().equals("dangerLevel")) {
                        event = reader.nextEvent();
                        creature.setDangerLevel(Integer.parseInt(event.asCharacters().getData()));
                    } else if (startEl.getName().getLocalPart().equals("habitat")) {
                        event = reader.nextEvent();
                        creature.setHabitat(event.asCharacters().getData());
                    } else if (startEl.getName().getLocalPart().equals("activity")) {
                        event = reader.nextEvent();
                        creature.setActivity(event.asCharacters().getData());
                    } else if (startEl.getName().getLocalPart().equals("firstMentioned")) {
                        event = reader.nextEvent();
                        creature.setFirstMentioned(event.asCharacters().getData());
                    } else if (startEl.getName().getLocalPart().equals("immunities")) {
                        event = reader.nextEvent();
                        creature.setImmunities(event.asCharacters().getData());
                    } else if (startEl.getName().getLocalPart().equals("vulnerabilities")) {
                        event = reader.nextEvent();
                        creature.setVulnerabilities(event.asCharacters().getData());
                    } else if (startEl.getName().getLocalPart().equals("height")) {
                        event = reader.nextEvent();
                        creature.setHeight(event.asCharacters().getData());
                    } else if (startEl.getName().getLocalPart().equals("weight")) {
                        event = reader.nextEvent();
                        creature.setWeight(event.asCharacters().getData());
                    } else if (startEl.getName().getLocalPart().equals("poisonRecipe")) {
                        event = reader.nextEvent();
                        creature.setPoisonRecipe(event.asCharacters().getData());
                    } else if (startEl.getName().getLocalPart().equals("time")) {
                        event = reader.nextEvent();
                        creature.setTime(Integer.parseInt(event.asCharacters().getData()));
                    } else if (startEl.getName().getLocalPart().equals("efficiency")) {
                        event = reader.nextEvent();
                        creature.setEfficiency(event.asCharacters().getData());
                    }                    
                }
                if (event.isEndElement()) {
                    EndElement endEl = event.asEndElement();
                    if (endEl.getName().getLocalPart().equals("creature")) {
                        creatures.add(creature);
                    }
                }
            }
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
        return creatures;
    }

     @Override
    protected void writeData(String path) {
        XMLOutputFactory output = XMLOutputFactory.newInstance();
        //List <Creature> cre
        try{
            XMLStreamWriter writer = output.createXMLStreamWriter(new FileOutputStream(path), "UTF-8");
            writer.writeStartDocument("1.0");
            writer.writeStartElement("creatures");
            for (Creature creature : Depository.getXmlStorage()){
                writer.writeStartElement("creature");
                
                writer.writeStartElement("id");
                writer.writeCharacters(Integer.toString(creature.getId()));
                writer.writeEndElement();
                
                writer.writeStartElement("name");
                writer.writeCharacters(creature.getName());
                writer.writeEndElement();
                
                writer.writeStartElement("description");
                writer.writeCharacters(creature.getDescription());
                writer.writeEndElement();
                
                writer.writeStartElement("dangerLevel");
                writer.writeCharacters(Integer.toString(creature.getDangerLevel()));
                writer.writeEndElement();
                
                writer.writeStartElement("habitat");
                writer.writeCharacters(creature.getHabitat());
                writer.writeEndElement();
                
                writer.writeStartElement("activity");
                writer.writeCharacters(creature.getActivity());
                writer.writeEndElement();
                
                writer.writeStartElement("firstMentioned");
                writer.writeCharacters(creature.getFirstMentioned());
                writer.writeEndElement();
                
                writer.writeStartElement("immunities");
                writer.writeCharacters(creature.getImmunities());
                writer.writeEndElement();
                
                writer.writeStartElement("vulnerabilities");
                writer.writeCharacters(creature.getVulnerabilities());
                writer.writeEndElement();
                
                writer.writeStartElement("height");
                writer.writeCharacters(creature.getHeight());
                writer.writeEndElement();
                
                writer.writeStartElement("weight");
                writer.writeCharacters(creature.getWeight());
                writer.writeEndElement();
                
                writer.writeStartElement("poisonRecipe");
                writer.writeCharacters(creature.getPoisonRecipe());
                writer.writeEndElement();
                
                writer.writeStartElement("time");
                writer.writeCharacters(Integer.toString(creature.getTime()));
                writer.writeEndElement();
                
                writer.writeStartElement("efficiency");
                writer.writeCharacters(creature.getEfficiency());
                writer.writeEndElement();
                
                
                
                writer.writeEndElement();              
            }
            writer.writeEndElement();
            writer.writeEndDocument();
            writer.flush();
        } catch (FileNotFoundException | XMLStreamException ex) {
            Logger.getLogger(XMLHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}