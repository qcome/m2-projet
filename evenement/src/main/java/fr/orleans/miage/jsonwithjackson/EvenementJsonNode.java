package fr.orleans.miage.jsonwithjackson;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fr.orleans.miage.domain.EvenementGlobal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class EvenementJsonNode {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    JsonNode rootNode;
    ObjectMapper objectMapper;
    URL jsonUrl;

    public EvenementJsonNode()throws IOException {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );

    }

    public List<EvenementGlobal> getAllEventsWithJsonNode() throws IOException {
        jsonUrl = new URL("https://data.orleans-metropole.fr/api/records/1.0/search/?dataset=evenements-publics-openagenda&rows=100");
        rootNode = objectMapper.readTree(jsonUrl);
        JsonNode events = rootNode.get("records");
        Iterator<JsonNode> eventsIterator = events.elements();
        ArrayNode myEventsNode = objectMapper.createArrayNode();
        while(eventsIterator.hasNext()){
            JsonNode event = eventsIterator.next();
            myEventsNode.add(event.get("fields"));
        }
        //logger.info("\n----------------------------\n"+myEventsNode.toString());
        List<EvenementGlobal> myEvents = Arrays.asList(objectMapper.readValue(myEventsNode.traverse(), EvenementGlobal[].class));
        for(EvenementGlobal evenementGlobal : myEvents){
            logger.info("\n----------------------------\n"+ evenementGlobal.toString());
        }

        return myEvents;
    }

    public List<EvenementGlobal> getLastEventsUpdatedWithJsonNode(Date date) throws IOException {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        jsonUrl = new URL("https://data.orleans-metropole.fr/api/records/1.0/search/?dataset=evenements-publics-openagenda&q=updated_at>=" + formater.format(date) + "&rows=-1");
        logger.debug(jsonUrl.toString());
        rootNode = objectMapper.readTree(jsonUrl);
        JsonNode events = rootNode.get("records");
        Iterator<JsonNode> eventsIterator = events.elements();
        ArrayNode myEventsNode = objectMapper.createArrayNode();
        while(eventsIterator.hasNext()){
            JsonNode event = eventsIterator.next();
            myEventsNode.add(event.get("fields"));
        }
        //logger.info("\n----------------------------\n"+myEventsNode.toString());
        List<EvenementGlobal> myEvents = Arrays.asList(objectMapper.readValue(myEventsNode.traverse(), EvenementGlobal[].class));
        for(EvenementGlobal evenementGlobal : myEvents){
            logger.info("\n----------------------------\n"+ evenementGlobal.toString());
        }

        return myEvents;
    }
}
