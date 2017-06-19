package test;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

//import org.hibernate.query.Query;


public class DataBase {

    private static SessionFactory sessionFactory;
    private static Session session;
    private ArrayList<Ticket> list;
    private ArrayList<Place> list2;

    private ArrayList<Place> nowe;
    private ArrayList<Place> uzywane;

    Map<Long,ArrayList<Place>> map;

    private static ArrayList<Ticket> wygaszone = new ArrayList<>();

    public void generate(long period){

        list = new ArrayList<>();
        list2 = new ArrayList<>();

        nowe = new ArrayList<>();
        uzywane = new ArrayList<>();
        map = new HashMap<>();
        map.clear();

        //wygaszone = new ArrayList<>();
        LocalDateTime time1 = LocalDateTime.now();
        Timestamp now = Timestamp.valueOf(time1);
        LocalDateTime time2 = time1.minusSeconds(period);

        LocalDateTime time3 = time2.minusSeconds(period);
        Timestamp time11 = Timestamp.valueOf(time2);
        Timestamp time22 = Timestamp.valueOf(time3);
        Configuration configuration = new Configuration();
        configuration.configure();

        sessionFactory = configuration.buildSessionFactory();
        session = getSession();

        try {

            final Query query = session.createQuery("from Ticket ");
            for (Object o : query.list()) {
                    Ticket t = (Ticket)o;
                    list.add(t);
                    if(t.getEndTime().before(now) && wygaszone.indexOf(t)<0) {
                        Simulator.add(t.getStrefa()-1);
                        wygaszone.add(t);
                    }
            }
        } catch (Throwable ex) {
        }

        try {
            Timestamp timestamp = Simulator.timeStart;
            final Query query1 = session.createQuery("from Place");
            for (Object o : query1.list()) {
                Place p = (Place) o;
                list2.add(p);
                if (p.getTime().after(time11)) {
                    System.out.println("Nowy "+p.getStrefa()+p.getId());
                    nowe.add(p);
                }
                else if (p.getTime().after(time22)) {
                    System.out.println("Uzywane "+p.getStrefa()+p.getId());
                    uzywane.add(p);
                }

                long difference = (p.getTime().getTime()-timestamp.getTime())/30000;
                ArrayList<Place> test = map.get(difference);
                if(test==null){
                    test= new ArrayList<>();
                    test.add(p);
                    map.put(difference,test);
                } else {
                    test.add(p);
                    map.put(difference,test);
                }

            }
        }catch (Throwable ex) {
        }
    }



    public static Session getSession() throws HibernateException {
        return sessionFactory.openSession();
    }

    public ArrayList<Ticket> getTicket() {
        return list;
    }
    public ArrayList<Place> getPlace() {
        return list2;
    }

    public int count(int strefa){
        int i=0;
        for(Ticket t:list){
            if(t.getStrefa()==strefa+1) i++;
        }
        return i;
    }
    public int count2(int strefa) {
        int i=0;
        for(Place p:list2){
            if(p.getStrefa()==strefa+1) i++;
        }
        return i;
    }

    public int nowe(int strefa){
        int i=0;
        for(Place p:nowe){
            if(p.getStrefa()==strefa+1) i++;
        }
        return i;
    }
    public int uzywane(int strefa){
        int i=0;
        for(Place p:uzywane){
            if(p.getStrefa()==strefa+1) i++;
        }
        return i;
    }

    public ArrayList<Ticket> findInacive(Timestamp date,int ile,int strefa){
        ArrayList<Ticket> temp = new ArrayList<>();
        LocalDateTime localDateTime = date.toLocalDateTime().minusSeconds(30);
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        for (Ticket ticket: list){
            if(ticket.getEndTime().before(date) && ticket.getStrefa()==strefa && ticket.getEndTime().after(timestamp)) {
                temp.add(0,ticket);
            }
        }

        return temp;
    }

    public String messageToSend(Timestamp date,ArrayList<Ticket> tickets){
        String msg = "Ktos nie odjechal powinien mozliwe do sprawdzenia : ";
        Set<Integer> set = new HashSet<>();
        long diff=(-5);
        for (Ticket ticket:tickets) {
            long timeTicket = ticket.getStartTime().getTime();
            long startTime = date.getTime();

            diff = (timeTicket - startTime) / 30000;
            if(diff==0) diff++;
            ArrayList<Place> toSend = map.get(diff - 1);
            if(toSend==null) return null;
            for (Place place : toSend) {
                set.add(place.getId());
            }
        }
        ArrayList<Place> older = map.get(diff-2);
        ArrayList<Place> newest = map.get(diff);
        if(set.size()==0) {
            if(older==null && newest==null) return null;
            else msg+=" brak";
        }
        for(int i: set){
            msg+=" "+i;
        }

        if(older!=null || newest!=null){
            msg+="Chociaz podejrzane moga byc tez";
            for(int i=0;i<older.size();i++){
                msg+=" "+older.get(i);
            }
            for(int i=0;i<newest.size();i++){
                msg+=" "+newest.get(i);
            }

        }
        return msg;
    }



    public ArrayList<Place> getNowe(int strefa) {
        ArrayList<Place> temp = new ArrayList<>();
        for(Place place: nowe){
            if(place.getStrefa()==strefa) temp.add(place);
        }
        return temp;
    }

    public ArrayList<Place> getUzywane(int strefa) {
        ArrayList<Place> temp = new ArrayList<>();
        for(Place place: uzywane){
            if(place.getStrefa()==strefa) temp.add(place);
        }
        return temp;
    }
}
