package Views.Cells;

import Modules.Event;
import Services.EventServices;
import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.util.Resources;
import vikings.MyApplication;

import java.util.ArrayList;

public class EventCell extends Form {
    public EventCell(Resources theme,Form previous) {
        this.setTitle("Events");
        this.setLayout(BoxLayout.y());
        Container by = new Container();
        ArrayList<Event> Events = EventServices.getInstance().getAllevents();
        for(int i=0;i<Events.size();i++){
            by = BoxLayout.encloseY(
                    eventCell(Events.get(i),theme)
        );
            add(by);
        }
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public Container eventCell(Event e,Resources theme){
        Image profilePic = theme.getImage("user-picture.jpg");
        Image mask = theme.getImage("round-mask.png");
        mask.scaled(50,50);
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        ImageViewer profil = new ImageViewer(mask.scaled(50, 50));
        Label profilePicLabel = new Label(profilePic, "ProfilePicTitle");
        profilePicLabel.setWidth(20);
        profilePicLabel.setHeight(20);
        profilePicLabel.setMask(mask.createMask());

        EncodedImage enc = EncodedImage.createFromImage(theme.getImage("BackgroundRect.png"), true);
        Image img = URLImage.createToStorage(enc, e.getImgURL(), e.getImgURL(),URLImage.RESIZE_SCALE);
        ImageViewer image = new ImageViewer(img.scaled(250, 350));



        Container EventCmp = BoxLayout.encloseY(
                new Label(e.getTitleEvent(), "StatusBarSideMenu"),
                BorderLayout.centerAbsolute(
                        BoxLayout.encloseY(
                        )
                ).add(BorderLayout.WEST, image),
                // new TextArea(Events.get(0).getDescription());
                new Label(e.getDescription(), "TodayEntry"),

                new Label("Location", "TodayEntry"),

                BoxLayout.encloseX(
                        //  new Label(Events.get(0).getDateEvent().toString(), "Role"),
                        new Label("End Date", "Role")

                )
        );

        EventCmp.setUIID("EventCmp");

        Container OrganizerCmp = BoxLayout.encloseY(
                BorderLayout.centerAbsolute(
                        BoxLayout.encloseY(
                                new Label("Organizer Name", "UserName"),
                                new Label("Club", "Role")
                        )
                ).add(BorderLayout.WEST, profilePicLabel),
                GridLayout.encloseIn(1, EventCmp)
        );
        // EventCmp.getAllStyles().setPadding(LEFT, 30);
        OrganizerCmp.setUIID("EventCell");

        OrganizerCmp.getAllStyles().setPadding(80, 80, 80, 80);

        // OrganizerCmp.getAllStyles().setMargin(LEFT, 25);
        OrganizerCmp.getAllStyles().setBorder(
                RoundRectBorder.create().
                        shadowOpacity(90).
                        stroke(new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 4)).
                        shadowSpread(Display.getInstance().convertToPixels(4)).
                        strokeOpacity(120)
        );





        //Container by = BoxLayout.encloseY(OrganizerCmp);


        //System.out.println(EventServices.getInstance().getAllevents().toString());
        return OrganizerCmp;
    }

}
