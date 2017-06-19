package test;

import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.codehaus.jackson.map.ObjectMapper;
import start.PrzyjazdService;
import org.codehaus.jackson.type.TypeReference;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Kamil on 02.06.2017.
 */


@WebService
public class Simulator {


    static int[] licznik = new int[3];
    static int l = 0;
    TimerTask timerTask;
    Timer timer;

    public static DataBase dataBase = new DataBase();
    public static final Client client = Client.create();
    static Timestamp timeStart;


    //W ramach przyspieszenia dzialania, 5 minut w rzeczywistosci to 1 minuta u nas
    public void start(){
        timeStart = Timestamp.valueOf(LocalDateTime.now());
        timerTask = new TimerTask() {
            @Override
            public void run() {
                check();
            }
        };
        timer = new Timer();
        timer.schedule(timerTask,0,30000);
    }

    @WebMethod(operationName = "increment")
    @WebResult(name = "incr")
    public static void add(@WebParam(name = "strefa") int strefa) {
        licznik[strefa]++;
        System.out.println("Dodaje");
    }

    @WebMethod(operationName = "decrement")
    @WebResult(name = "decr")
    public void del(@WebParam(name = "strefa") int strefa) {
        licznik[strefa-1]--;
        System.out.println("Usuwam");
    }



    public void sendMessage(ArrayList<Place> p1, ArrayList<Place> p2, boolean which){
        Message message;
        String s1="",s2="";

        for(Place place:p1){
            s1+=" "+place.getId();
        }

        for(Place place:p2){
            s2+=" "+place.getId();
        }
        if(p1.size()>0) {
            if (which) {
                System.out.println("Na pewno ktos nie zaplacil");
                message = new Message("Na pewno ktos nie zaplacil, sprawdz: " + s1, p1.get(0).getStrefa());
            } else {
                System.out.println("Byc moze ktos nie zaplacil");
                message = new Message("Mozliwe ze ktos nie zaplacil, najpierw: " + s1 + " potem: " + s2, p1.get(0).getStrefa());
            }
            Sender sender = new Sender();
            try {
                sender.send(message,false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendError(String msg,int strefa){
        Message message = new Message(msg,strefa);
        Sender sender = new Sender();
        try {
            sender.send(message,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void check(){
        System.out.println("ZACZYNAM");
        if(l>0) {
            dataBase.generate(30);

            ArrayList<Place> places = dataBase.getPlace();
            System.out.println("Dzialam");
            for (int i = 0; i < 3; i++) {
                System.out.println(licznik[i]);
                if (licznik[i] > 0) {
                    System.out.println("UWAGA*************************************************");
                    LocalDateTime time = LocalDateTime.now();
                    Timestamp timestamp = Timestamp.valueOf(time);
                    ArrayList<Ticket> ticket = dataBase.findInacive(timestamp,licznik[i],i+1);
                    String msg = dataBase.messageToSend(timeStart,ticket);
                    System.out.println(msg);
                    if(msg!=null) {
                        System.out.println("Ktos nie odjechal, sprawdz to");
                        System.out.println(msg);
                        sendError(msg,i+1);
                    }

                }
            }


            for (int i=0;i<3;i++){
                System.out.println("Strefa"+ (i+1));
                ArrayList<Place> n = dataBase.getNowe(i+1);
                ArrayList<Place> u = dataBase.getUzywane(i+1);
                for (Place place:u){
                    System.out.println(place.getId()+" "+place.getStrefa());
                }
                int s = dataBase.count2(i)-dataBase.nowe(i)-licznik[i];
                if(s>dataBase.count(i)){
                    sendMessage(u,n,true);
                } else if(s==dataBase.count(i) && s!=0 && dataBase.nowe(i)!=0) {
                    sendMessage(u,n,false);
                }

            }

        } else l=1;

    }
}
