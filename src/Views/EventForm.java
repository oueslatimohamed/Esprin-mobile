package Views;

import Modules.Event;
import Services.EventServices;
import Utils.Enums.State;
import Views.Cells.EventCell;
import com.codename1.charts.compat.Paint;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.codename1.components.FloatingActionButton;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;

import java.sql.Date;
import java.util.ArrayList;

//import com.codename1.uikit.materialscreens.SideMenuBaseForm;

/**
 * Represents a user profile in the app, the first form we open after the walkthru
 *
 * @author Eya Kasmi
 */
public class EventForm extends SideMenuBaseForm {
    public EventForm(Resources res) {
        super(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);
        Image profilePic = res.getImage("user-picture.jpg");
        Image mask = res.getImage("round-mask.png");
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label(profilePic, "ProfilePicTitle");
        profilePicLabel.setMask(mask.createMask());

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());

        Container remainingTasks = BoxLayout.encloseY(
                new Label("12", "CenterTitle"),
                new Label(" My Event Number", "CenterSubTitle")
        );
        remainingTasks.setUIID("RemainingTasks");
        Container completedTasks = BoxLayout.encloseY(
                new Label("32", "CenterTitle"),
                new Label("All Event Number", "CenterSubTitle")
        );
        completedTasks.setUIID("CompletedTasks");

        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(menuButton),
                BorderLayout.centerAbsolute(
                        BoxLayout.encloseY(
                                new Label("User Name", "Title"),
                                new Label("Role", "SubTitle")
                        )
                ).add(BorderLayout.WEST, profilePicLabel),
                GridLayout.encloseIn(2, remainingTasks, completedTasks)
        );

        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        fab.getAllStyles().setMarginUnit(Style.UNIT_TYPE_PIXELS);
        fab.getAllStyles().setMargin(BOTTOM, completedTasks.getPreferredH() - fab.getPreferredH() / 2);
        tb.setTitleComponent(fab.bindFabToContainer(titleCmp, CENTER, BOTTOM));

        add(new Label("Events", "TodayTitle"));

        FontImage arrowDown = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_DOWN, "Label", 3);
        Container by = new Container();
        ArrayList<Event> Events = EventServices.getInstance().getAllevents();
        for(int i=0;i<Events.size();i++){
                by = BoxLayout.encloseY(eventCell(Events.get(i),res));
                add(by);

        }
        //getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());

        /*addButtonBottom(arrowDown, "Finish landing page concept", 0xd997f1, true);
        addButtonBottom(arrowDown, "Design app illustrations", 0x5ae29d, false);
        addButtonBottom(arrowDown, "Javascript training ", 0x4dc2ff, false);
        addButtonBottom(arrowDown, "Surprise Party for Matt", 0xffc06f, false);*/
        setupSideMenu(res);

        fab.addActionListener(evt ->{
            Toolbar.setGlobalToolbar(false);
            new AddEvent(res).show();
            Toolbar.setGlobalToolbar(true);
        });

    }

    private void addButtonBottom(Image arrowDown, String text, int color, boolean first) {
        MultiButton finishLandingPage = new MultiButton(text);
        finishLandingPage.setEmblem(arrowDown);
        finishLandingPage.setUIID("Container");
        finishLandingPage.setUIIDLine1("TodayEntry");
        finishLandingPage.setIcon(createCircleLine(color, finishLandingPage.getPreferredH(),  first));
        finishLandingPage.setIconUIID("Container");
        add(FlowLayout.encloseIn(finishLandingPage));
    }

    public Container eventCell(Event e,Resources theme){
        Image profilePic = theme.getImage("user-picture.jpg");
        Image mask = theme.getImage("round-mask.png");

        Button optionBtn = new Button("");
        optionBtn.setUIID("More");
        FontImage.setMaterialIcon(optionBtn, FontImage.MATERIAL_MORE_VERT);
        Button EditBtn = new Button("EditCmd") ;
        Button DeleteBtn = new Button("DeleteCmd") ;
        EditBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new UpdateEvent(theme,e).show();
            }
        });
        DeleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                e.setState(State.Deleted);
                if( EventServices.getInstance().deletedevent(e)){
                    Dialog.show("Success","Connection accepted",new Command("OK"));
                }else {
                    Dialog.show("Failed","Connection rejected",new Command("OK"));
                }
            }
        });
        optionBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Dialog dialog = new Dialog("Example", BoxLayout.y());
                dialog.add(EditBtn);
                dialog.add(DeleteBtn);
                dialog.setDisposeWhenPointerOutOfBounds(true);
                dialog.show();
            }
        });
        /*optionBtn.addActionListener(k -> {
            Dialog dlg = new Dialog();

            // makes the dialog transparent
            dlg.setDialogUIID("Container");
            dlg.setLayout(BoxLayout.y());
            dlg.setDisposeWhenPointerOutOfBounds(true);

            Command EditCmd = new Command("Edit");
            Command DeleteCmd = new Command("Delete");
            Command cancelCmd = new Command("Cancel");

            if (cancelCmd != null) {
                add(ComponentGroup.enclose(new Button(cancelCmd)));
            }

            Button EditBtn = new Button(EditCmd) ;
            Button DeleteBtn = new Button(DeleteCmd) ;



            EditCmd.actionPerformed(new ActionEvent(dlg));




            //    new UpdateEvent(theme).show();


            dlg.add(
                    ComponentGroup.enclose(
                            EditBtn,
                            DeleteBtn
                    )).
                    add(ComponentGroup.enclose(new Button(cancelCmd)));


            dlg.showStretched(BorderLayout.SOUTH, true);

        });*/

        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label(profilePic, "ProfilePicTitle");
        profilePicLabel.setMask(mask.createMask());

        // Image imgEvnt = theme.getImage("BackgroundRect.png");
        EncodedImage enc = EncodedImage.createFromImage(theme.getImage("BackgroundRect.png"), true);
        Image img = URLImage.createToStorage(enc, e.getImgURL(), e.getImgURL(),URLImage.RESIZE_SCALE);
        ImageViewer image = new ImageViewer(img.scaled(1000, 350));


        FontImage Loc = FontImage.createMaterial(FontImage.MATERIAL_LOCATION_CITY, "Label", 3);


        Container EventCmp = BoxLayout.encloseY(
                new Label(e.getTitleEvent(), "StatusBarSideMenu"),
                BorderLayout.centerAbsolute(
                        BoxLayout.encloseY(
                        )
                ).add(BorderLayout.WEST, image),
                // new TextArea(Events.get(0).getDescription());
                new Label(e.getDescription(), "TodayEntry"),
                new Label(e.getLocation(), "TodayEntry"),

                BoxLayout.encloseXCenter(
                        //  new Label(Events.get(0).getDateEvent().toString(), "Role"),
                        new Label("Start Date", "Role"),
                        new Label("End Date", "Role")
                )
        );

        EventCmp.setUIID("EventCmp");



        FloatingActionButton PartButton = FloatingActionButton.createFAB(FontImage.MATERIAL_PERSON);
        PartButton.getAllStyles().setMarginUnit(Style.UNIT_TYPE_PIXELS);
        PartButton.getAllStyles().setMargin(BOTTOM,0);
        // PartButton.getAllStyles().setMargin(RIGHT,5);
        // PartButton.getAllStyles().setMarginRight(0);

        Container Participate = BoxLayout.encloseXRight(
                new Label(String.valueOf(e.getParticipateNumber()), "Role"),
                PartButton
        );
        //Participate.getAllStyles().setAlignment(50);

        Participate.getAllStyles().setMargin(RIGHT,0);

        Container OrganizerCmp = BoxLayout.encloseY(
                FlowLayout.encloseRight(optionBtn),
                BorderLayout.centerAbsolute(
                        BoxLayout.encloseY(
                                new Label("Organizer Name", "UserName"),
                                new Label("Club", "Role")
                        )
                ).add(BorderLayout.WEST, profilePicLabel),
                GridLayout.encloseIn(1, EventCmp),
                Participate
        );

        // OrganizerCmp.add(BorderLayout.CENTER, PartButton);

        OrganizerCmp.setUIID("EventCell");
        OrganizerCmp.getAllStyles().setPadding(80, 80, 80, 80);
        OrganizerCmp.getAllStyles().setBorder(
                RoundRectBorder.create().
                        shadowOpacity(90).
                        stroke(new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 4)).
                        shadowSpread(Display.getInstance().convertToPixels(4)).
                        strokeOpacity(120)
        );


        return OrganizerCmp;
    }

    private Image createCircleLine(int color, int height, boolean first) {
        Image img = Image.createImage(height, height, 0);
        Graphics g = img.getGraphics();
        g.setAntiAliased(true);
        g.setColor(0xcccccc);
        int y = 0;
        if(first) {
            y = height / 6 + 1;
        }
        g.drawLine(height / 2, y, height / 2, height);
        g.drawLine(height / 2 - 1, y, height / 2 - 1, height);
        g.setColor(color);
        g.fillArc(height / 2 - height / 4, height / 6, height / 2, height / 2, 0, 360);
        return img;
    }





    @Override
    protected void showOtherForm(Resources res) {
        Form current = this;
        new EventCell(res,current).show();
    }
}
