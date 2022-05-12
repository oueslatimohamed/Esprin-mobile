package Services;

import Modules.Event;
import Modules.Post;
import Utils.UsefulMethodes;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EventServices {
    private static EventServices instance;
    private final String URL="http://127.0.0.1:8000/api/";
    public ArrayList<Event> eventArrayList;
    public boolean resultOK;
    private ConnectionRequest req;

    private EventServices() {
        req = new ConnectionRequest();
    }

    public static EventServices getInstance(){
        if(instance == null)
            instance = new EventServices();
        return instance;
    }

    public boolean addevent(Event f) {
        String url = URL + "addevent";
        req.setUrl(url);
        req.setPost(false);
        //req.addArgument("title", f.getTitle());
        req.addArgument("titleevent", f.getTitleEvent());
        req.addArgument("contentevent", f.getDescription());
        //req.addArgument("imgurl", f.getImgURL());
        req.addArgument("locationEvent", f.getLocation());
        req.addArgument("idOrganizer", String.valueOf(f.getIdOrganizer()));
        // req.addArgument("eventlocal", f.get());
        //req.addArgument("categorie", f.getCategories());
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public ArrayList<Event> parseEvent(String jsonText){
        try {
            eventArrayList=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson =
                    j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Event f = new Event();
                float id = Float.parseFloat(obj.get("idevent").toString());
                f.setIdEvent((int)id);
                f.setTitleEvent((obj.get("titleevent").toString()));
                f.setDescription((obj.get("contentevent").toString()));
                //f.setDateDebut(UsefulMethodes.convertStringToDate(obj.get("datedebut").toString()));

                if (obj.get("imgurl")==null)
                    f.setImgURL("null");
                else
                    f.setImgURL(obj.get("imgurl").toString());
                eventArrayList.add(f);
            }


        } catch (IOException ex) {

        }
        return eventArrayList;
    }

    public ArrayList<Event> getAllevents(){
        req = new ConnectionRequest();
        String url = URL+"getevents";
        //System.out.println("===>"+url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                eventArrayList = parseEvent(new String(req.getResponseData()));
                req.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return eventArrayList;
    }
}
