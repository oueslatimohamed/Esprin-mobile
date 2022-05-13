package Services;

import Modules.Forum;
import Modules.Post;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PostServices {
    private static PostServices instance;
    private final String URL="http://127.0.0.1:8000/api/";
    public ArrayList<Post> postArrayList;
    public boolean resultOK;
    private ConnectionRequest req;

    private PostServices() {
        req = new ConnectionRequest();
    }

    public static PostServices getInstance(){
        if(instance == null)
            instance = new PostServices();
        return instance;
    }

    public boolean addPost(Post f) {
        String url = URL + "addPost";
        req.setUrl(url);
        req.setPost(false);
        //req.addArgument("title", f.getTitle());
        req.addArgument("content", f.getContent());
        req.addArgument("idower", String.valueOf(f.getIdowner()));
        req.addArgument("categorie", f.getCategories().toString());
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

    public ArrayList<Post> parseTasks(String jsonText){
        try {
            postArrayList=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson =
                    j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Post f = new Post();
                float id = Float.parseFloat(obj.get("idpost").toString());
                f.setIdPost((int)id);
                f.setContent((obj.get("content").toString()));
                if (obj.get("mediaurl")==null || obj.get("categorie")==null)
                    f.setCreatedBy("null");
                else
                    f.setMediaURL(obj.get("mediaurl").toString());
                postArrayList.add(f);
            }


        } catch (IOException ex) {

        }
        return postArrayList;
    }

    public ArrayList<Post> getAllposts(){
        req = new ConnectionRequest();
        String url = URL+"getposts";
        //System.out.println("===>"+url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                postArrayList = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return postArrayList;
    }
}
