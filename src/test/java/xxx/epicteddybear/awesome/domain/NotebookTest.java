package xxx.epicteddybear.awesome.domain;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: dnelson
 * Date: 7/1/13
 * Time: 9:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class NotebookTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCreate() throws Exception {

    }

    @Test
    public void testJsonDeserialize() throws IOException {
        String notebookJson = "{\"id\":\"4f608cd6-ac83-4373-b70e-6ebf2e79224b\",\"owner\":\"admin\",\"created\":1372701758396,\"updated\":1372701758396,\"title\":\"test list\",\"flashCards\":[{\"front\":\"Card 1\",\"back\":\"Back\"},{\"front\":\"Card 2\",\"back\":\"Back\"},{\"front\":\"Card 3\",\"back\":\"Back\"}]}";

        ObjectMapper mapper = new ObjectMapper();

        Notebook notebook = mapper.readValue(notebookJson, Notebook.class);

        assertEquals(notebook.getOwner(), "admin");

    }
}
