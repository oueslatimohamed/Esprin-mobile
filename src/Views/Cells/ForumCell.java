package Views.Cells;

import Modules.Event;
import Modules.Forum;
import Services.EventServices;
import Services.ForumServices;
import com.codename1.components.FloatingActionButton;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;

import java.util.ArrayList;

public class ForumCell extends Form {
    public ForumCell(Resources theme,Form previous) {
        //super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        this.setTitle("Forums");
        this.setLayout(BoxLayout.y());
        Container by = new Container();
        ArrayList<Forum> forums = ForumServices.getInstance().getAllforums();
        for(int i=0;i<forums.size();i++){
            by = BoxLayout.encloseY(forumcell(forums.get(i),theme));
            add(by);
        }
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public Container forumcell(Forum f , Resources theme) {
        Image profilePic = theme.getImage("user-picture.jpg");
        Image mask = theme.getImage("round-mask.png");
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label(profilePic, "ProfilePicTitle");
        profilePicLabel.setMask(mask.createMask());


        Container ForumCmp = BoxLayout.encloseY(

                new Label(f.getTitle(), "StatusBarSideMenu"),


                new Label(f.getContent(), "TodayEntry")



        );

        ForumCmp.setUIID("EventCmp");

        Container OwnerCmp = BoxLayout.encloseY(
                BorderLayout.centerAbsolute(
                        BoxLayout.encloseY(
                                new Label("Owner Name", "UserName"),
                                new Label("Creation Date", "TodayEntry")

                        )
                ).add(BorderLayout.WEST, profilePicLabel),
                GridLayout.encloseIn(1, ForumCmp)
        );
        // EventCmp.getAllStyles().setPadding(LEFT, 30);
        OwnerCmp.setUIID("EventCell");

        OwnerCmp.getAllStyles().setPadding(80, 80, 80, 80);

        // OrganizerCmp.getAllStyles().setMargin(LEFT, 25);
        OwnerCmp.getAllStyles().setBorder(
                RoundRectBorder.create().
                        shadowOpacity(90).
                        stroke(new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 4)).
                        shadowSpread(Display.getInstance().convertToPixels(4)).
                        strokeOpacity(120)
        );



        //Container by = BoxLayout.encloseY(OwnerCmp);

       /* add(BorderLayout.CENTER, by);

        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);*/
        return OwnerCmp;
    }
}
