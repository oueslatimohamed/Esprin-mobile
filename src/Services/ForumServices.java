package Services;

import Modules.Event;
import Modules.Forum;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ForumServices {
    private static ForumServices instance;
    private final String URL="http://127.0.0.1:8000/api/";
    public ArrayList<Forum> forumArrayList;
    public boolean resultOK;
    private ConnectionRequest req;

    private ForumServices() {
        req = new ConnectionRequest();
    }

    public static ForumServices getInstance(){
        if(instance == null)
            instance = new ForumServices();
        return instance;
    }

    public boolean addForum(Forum f) {
        String url = URL + "addforum";
        req.setUrl(url);
        req.setPost(false);
        req.addArgument("title", f.getTitle());
        req.addArgument("content", f.getContent());
        req.addArgument("categorieforum", f.getCategoryForum());
        req.addArgument("idowner", String.valueOf(f.getIdOwner()));
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

    public boolean updateForum(Forum f) {
        String url = URL + "updateforum/"+ f.getIdForum();
        req.setUrl(url);
        req.setPost(false);
        req.addArgument("title", f.getTitle());
        req.addArgument("content", f.getContent());
        req.addArgument("categorieforum", f.getCategoryForum());
        req.addArgument("idowner", String.valueOf(f.getIdOwner()));
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

    public boolean deleteforum(Forum f) {
        String url = URL + "deletforum/" + f.getIdForum();
        req.setUrl(url);
        req.setPost(false);
        req.addArgument("state", f.getState().toString());
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

    public ArrayList<Forum> parseTasks(String jsonText){
        try {
            forumArrayList=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson =
                    j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Forum f = new Forum();
                float id = Float.parseFloat(obj.get("idforum").toString());
                f.setIdForum((int)id);
                f.setContent((obj.get("content").toString()));
                if (obj.get("title")==null || obj.get("categorieforum")==null)
                    f.setTitle("null");
                else
                    f.setTitle(obj.get("title").toString());
                forumArrayList.add(f);
            }


        } catch (IOException ex) {

        }
        return forumArrayList;
    }

    public ArrayList<Forum> getAllforums(){
        req = new ConnectionRequest();
        String url = URL+"getforums";
        //System.out.println("===>"+url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                forumArrayList = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return forumArrayList;
    }
}
