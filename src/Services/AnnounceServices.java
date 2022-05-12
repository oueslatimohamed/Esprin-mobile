package Services;

import Modules.Annoucement;
import Modules.Post;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AnnounceServices {
    private static AnnounceServices instance;
    private final String URL="http://127.0.0.1:8000/api/";
    public ArrayList<Annoucement> announceArrayList;
    public boolean resultOK;
    private ConnectionRequest req;

    private AnnounceServices() {
        req = new ConnectionRequest();
    }

    public static AnnounceServices getInstance(){
        if(instance == null)
            instance = new AnnounceServices();
        return instance;
    }

    public boolean addann(Annoucement f) {
        String url = URL + "addannounce";
        req.setUrl(url);
        req.setPost(false);
        //req.addArgument("title", f.getTitle());

        req.addArgument("content", f.getContentAnn());
        req.addArgument("subject", f.getSubjectAnn());
        req.addArgument("destination", f.getDestAnn().toString());
        req.addArgument("idowner", String.valueOf(f.getIdSender()));
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

    public ArrayList<Annoucement> parseTasks(String jsonText){
        try {
            announceArrayList=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson =
                    j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Annoucement f = new Annoucement();
                float id = Float.parseFloat(obj.get("idann").toString());
                f.setIdAnn((int)id);
                f.setContentAnn((obj.get("content").toString()));
                if (obj.get("destination")==null)
                    f.getDestAnn();
                else
                    f.getDestAnn();
                announceArrayList.add(f);
            }


        } catch (IOException ex) {

        }
        return announceArrayList;
    }

    public ArrayList<Annoucement> getAllann(){
        req = new ConnectionRequest();
        String url = URL+"getanns";
        //System.out.println("===>"+url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                announceArrayList = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return announceArrayList;
    }
}
