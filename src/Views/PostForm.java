package Views;

import Modules.Post;
import Services.PostServices;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;

import java.util.ArrayList;

public class PostForm  extends SideMenuBaseForm{
    public PostForm(Resources res) {
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

        add(new Label("Posts", "TodayTitle"));

        FontImage arrowDown = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_DOWN, "Label", 3);
        Container by = new Container();
        ArrayList<Post> posts = PostServices.getInstance().getAllposts();
        for(int i=0;i<posts.size();i++){
            by = BoxLayout.encloseY(
                    postCell(posts.get(i),res)
            );
            add(by);
        }
        //getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());

        /*addButtonBottom(arrowDown, "Finish landing page concept", 0xd997f1, true);
        addButtonBottom(arrowDown, "Design app illustrations", 0x5ae29d, false);
        addButtonBottom(arrowDown, "Javascript training ", 0x4dc2ff, false);
        addButtonBottom(arrowDown, "Surprise Party for Matt", 0xffc06f, false);*/
        setupSideMenu(res);
        fab.addActionListener(evt -> {
            Toolbar.setGlobalToolbar(false);
            new AddPost(res).show();
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

    public Container postCell(Post e, Resources theme){
        Image profilePic = theme.getImage("user-picture.jpg");
        Image mask = theme.getImage("round-mask.png");
        mask.scaled(20,20);
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        ImageViewer profil = new ImageViewer(mask.scaled(50, 50));
        Label profilePicLabel = new Label(profilePic, "ProfilePicTitle");
        profilePicLabel.setWidth(20);
        profilePicLabel.setHeight(20);
        profilePicLabel.setMask(mask.createMask());

        EncodedImage enc = EncodedImage.createFromImage(theme.getImage("BackgroundRect.png"), true);
        Image img = URLImage.createToStorage(enc, e.getMediaURL(), e.getMediaURL(),URLImage.RESIZE_SCALE);
        ImageViewer image = new ImageViewer(img.scaled(1000, 350));



        Container EventCmp = BoxLayout.encloseY(
                new Label("", "StatusBarSideMenu"),
                BorderLayout.centerAbsolute(
                        BoxLayout.encloseY(
                        )
                ).add(BorderLayout.WEST, image),
                // new TextArea(Events.get(0).getDescription());
                new Label(e.getContent(), "TodayEntry")
        );

        EventCmp.setUIID("EventCmp");

        Container OrganizerCmp = BoxLayout.encloseY(
                BorderLayout.centerAbsolute(
                        BoxLayout.encloseY(
                                new Label("Organizer Name", "UserName"),
                                new Label("12/12/2021", "Role")
                        )
                ).add(BorderLayout.WEST, profilePicLabel),
                GridLayout.encloseIn(1, EventCmp)
        );
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

    }
}
