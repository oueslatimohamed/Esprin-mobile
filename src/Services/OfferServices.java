package Services;

import Modules.Offre;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OfferServices {
    private static OfferServices instance;
    private final String URL="http://127.0.0.1:8000/api/";
    public ArrayList<Offre> offreArrayList;
    public boolean resultOK;
    private ConnectionRequest req;

    private OfferServices() {
        req = new ConnectionRequest();
    }

    public static OfferServices getInstance(){
        if(instance == null)
            instance = new OfferServices();
        return instance;
    }

    public boolean addoffer(Offre f) {
        String url = URL + "addOffer";
        req.setUrl(url);
        req.setPost(false);
        //req.addArgument("title", f.getTitle());
        req.addArgument("titleoffer", f.getTitleOffer());
        req.addArgument("descoffer", f.getDescOffer());
        //req.addArgument("imgoffre", f.getImgOffre());
        req.addArgument("categorieoffer", f.getCategory().toString());
        req.addArgument("offerprovider", String.valueOf(f.getOfferProvider()));
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

    public ArrayList<Offre> parseTasks(String jsonText){
        try {
            offreArrayList=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson =
                    j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Offre f = new Offre();
                float id = Float.parseFloat(obj.get("idoffer").toString());
                f.setIdOffre((int)id);
                f.setDescOffer((obj.get("descoffer").toString()));
                if (obj.get("imgoffre")==null || obj.get("catoffre") == null)
                    f.setImgOffre("null");
                else
                    f.setImgOffre(obj.get("imgoffre").toString());
                offreArrayList.add(f);
            }


        } catch (IOException ex) {

        }
        return offreArrayList;
    }

    public ArrayList<Offre> getAllevents(){
        req = new ConnectionRequest();
        String url = URL+"getoffers";
        //System.out.println("===>"+url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                offreArrayList = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return offreArrayList;
    }
}
