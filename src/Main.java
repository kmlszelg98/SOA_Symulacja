import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Kamil on 04.06.2017.
 */
public class Main {

    public static void main(String[] args) {

        java.sql.Timestamp ts1 = java.sql.Timestamp.valueOf("2005-04-06 09:01:10");
        java.sql.Timestamp ts2 = java.sql.Timestamp.valueOf("2005-04-06 09:01:45");

        System.out.println(ts1.getTime());
        System.out.println(ts2.getTime());
        System.out.println((ts2.getTime()-ts1.getTime())/30000);


        /*Client client = Client.create();
        Type listType = new TypeToken<ArrayList<Integer>>() {
        }.getType();
        TypeReference reference = new TypeReference<ArrayList<Integer>>(){};
        ObjectMapper mapper = new ObjectMapper();
        WebResource active = client.resource("http://localhost:8080/Bilet/start/tickets/check");
        ClientResponse responseActive = active.accept("application/json").get(ClientResponse.class);
        String outputActive = responseActive.getEntity(String.class);

        System.out.println(outputActive);
        try {
            ArrayList<Integer> bilety = mapper.readValue(outputActive,reference);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
