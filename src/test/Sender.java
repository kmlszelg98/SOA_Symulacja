package test;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import test.Message;

import java.io.IOException;

/**
 * Created by Kamil on 28.05.2017.
 */
public class Sender {

    public static void send(Message message,boolean type) throws IOException {

        Client client = Client.create();
        WebResource webResource;
        if(!type) {
            webResource = client
                    .resource("http://localhost:8080/Glowny/info/message/danger");
        } else {
            webResource = client
                    .resource("http://localhost:8080/Glowny/info/message/empty");
        }

        Gson gson = new Gson();
        String string = gson.toJson(message);
        System.out.println(string);
        client.setFollowRedirects(false);
        ClientResponse resp = webResource.type("application/json").post(ClientResponse.class, string);
        System.out.println(resp);

    }
}
