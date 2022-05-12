package Views;

import Views.ProfileForm;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.util.Resources;

public class AddClub extends Form {
    public AddClub(Resources res) {
        super(new LayeredLayout());

        Label bottomSpaceTab2 = new Label();
        Image duke = res.getImage("duke.png");


        Button backButton = new Button("");
        backButton.setUIID("EventCell");
        FontImage.setMaterialIcon(backButton, FontImage.MATERIAL_ARROW_BACK);
        backButton.addActionListener(e ->
                new WalkthruForm(res).show()
        );



        Label Role= new Label("Club");
        TextField FirstName = new TextField("", "Enter your name", 20,TextField.ANY) ;
        TextField LastName = new TextField("", "Enter your Last name", 20,TextField.ANY) ;
        TextField Email = new TextField("", "Enter your Email", 20, TextField.EMAILADDR) ;
        TextField Cin = new TextField("", "Enter your CIN", 20, TextField.NUMERIC) ;
        TextField password = new TextField("", "Enter your Password", 20, TextField.PASSWORD);

        ComboBox Type = new ComboBox(
                "Association",
                "Interne",
                "Junior_entreprise"
        );

        Button Add = new Button("Add");
        Add.setUIID("LoginButton");

        Add.addActionListener(e -> new ProfileForm(res).show());

        Add.getUnselectedStyle().setBorder(
                RoundBorder.create().rectangle(true).stroke(new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 4)).
                        strokeColor(0xff).strokeOpacity(120)
        );

        int color= Add.getUnselectedStyle().getBgColor();
        Add.getPressedStyle().setBgColor(color);

        Add.getPressedStyle().setBorder(
                RoundBorder.create().rectangle(true).stroke(new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 4)).
                        strokeColor(0xff).strokeOpacity(120)
        );

        Container southLayout = BoxLayout.encloseY(
                Add
        );

        Container tab2 = BorderLayout.centerAbsolute(
                BoxLayout.encloseY(
                    backButton,
                    new Label(duke, "ProfilePic"),
                    FirstName,
                    LastName,
                    Email,
                    Cin,
                    password,
                    Type,
                    bottomSpaceTab2,
                    // Add
                    southLayout
        ));

        tab2.setUIID("WalkthruTab2");


        /*
        add(BorderLayout.south(
                southLayout
        )); */


        add(BorderLayout.CENTER, tab2);

        // for low res and landscape devices
        tab2.setScrollableY(true);
        tab2.setScrollVisible(false);

    }

}