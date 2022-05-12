package Services;

import Modules.User;

import java.sql.SQLException;
import java.util.List;

public class UserServices {
    private static UserServices instance;
    private final String URL="http://127.0.0.1:8000/api/";

    private UserServices(){

    }

    public static UserServices getInstance(){
        if (instance == null)
            instance = new UserServices();
        return instance;
    }

    public List<User> getUsers(){
        String UrlgetUser=URL+"getStudents";


        return null;
    }
}
