package fr.orleans.miage.jsonwithjackson;

import fr.orleans.miage.domain.EvenementGlobal;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class EvenementGlobalJsonNodeTest {

    @Test
    public void getAllEventsWithJsonNode() throws Exception {
        EvenementJsonNode jsonNode = new EvenementJsonNode();
        List<EvenementGlobal> evenementGlobals = jsonNode.getAllEventsWithJsonNode();
        Assert.assertFalse(evenementGlobals.isEmpty());
    }
}
