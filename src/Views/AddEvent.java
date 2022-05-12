package Views;

import Modules.Event;
import Services.EventServices;
import com.codename1.io.Log;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import javafx.scene.control.DatePicker;

import javax.swing.*;
import java.text.SimpleDateFormat;

public class AddEvent extends Form {
    public AddEvent(Resources theme) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
       // setUIID("AddEvent");
        Container AddNew = FlowLayout.encloseCenter(
                new Label("Add New Event ", "EventTitle")

        );
        //AddNew.getAllStyles().setPadding(TOP,0);

        Button backButton = new Button("");
        backButton.setUIID("EventCell");
        FontImage.setMaterialIcon(backButton, FontImage.MATERIAL_ARROW_BACK);
        backButton.addActionListener(e ->
                new EventForm(theme).show()
        );



        TextField TitleEv = new TextField("", "Enter title", 20,TextField.ANY) ;
        TextArea content =new TextArea("Description!..",4, 20);

        TextField Location = new TextField("", "Location", 20, TextField.ANY) ;

       // TextField FromDate = new TextField("", "start ",10, TextField.ANY) ;
      //  TextField ToDate = new TextField("",  "end ",10, TextField.ANY) ;

        Picker FromDate = new Picker();
        FromDate.setType(Display.PICKER_TYPE_DATE);
        FromDate.setUIID("AddEvent");


        Picker ToDate = new Picker();

        ToDate.setType(Display.PICKER_TYPE_DATE);
        ToDate.setUIID("AddEvent");

        Container Date =BoxLayout.encloseXCenter(

                 BoxLayout.encloseYCenter(
                        new Label("From"),
                        FromDate
                ),
                BoxLayout.encloseYCenter(
                        new Label("To"),
                        ToDate
                )

        );


        Button addButton = new Button("ADD EVENT");
        addButton.setUIID("LoginButton");

        addButton.getUnselectedStyle().setBorder(
                RoundBorder.create().rectangle(true).stroke(new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 4)).
                        strokeColor(0xff).strokeOpacity(120)
        );

        int color=addButton.getUnselectedStyle().getBgColor();
        addButton.getPressedStyle().setBgColor(color);

        addButton.getPressedStyle().setBorder(
                RoundBorder.create().rectangle(true).stroke(new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 4)).
                        strokeColor(0xff).strokeOpacity(120)
        );





        TitleEv.setUIID("AddEvent");
        content.setUIID("AddEvent");
        Location.setUIID("AddEvent");

        TitleEv.getAllStyles().setBorder(
                RoundRectBorder.create())
        ;
        content.getAllStyles().setBorder(
                RoundRectBorder.create())
        ;
        Location.getAllStyles().setBorder(
                RoundRectBorder.create())
        ;

        TitleEv.getAllStyles().setMargin(LEFT,0);
        TitleEv.getAllStyles().setMargin(BOTTOM,50);

        content.getAllStyles().setMargin(LEFT, 0);
        content.getAllStyles().setMargin(BOTTOM,50);

        Location.getAllStyles().setMargin(LEFT, 0);
        Location.getAllStyles().setMargin(BOTTOM,50);

        Date.getAllStyles().setMargin(BOTTOM, 50);


        FromDate.getAllStyles().setMargin(RIGHT, 30);
        ToDate.getAllStyles().setMargin(LEFT, 30);


        Container by = BoxLayout.encloseY(
                FlowLayout.encloseIn(backButton),
                AddNew,
                TitleEv,
                content,
                Location,
                Date,
                addButton
        );

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if(TitleEv.getText().isEmpty() || content.getText().isEmpty() || Location.getText().isEmpty()){
                    Dialog.show("Alert", "Please fill all the fields",new Command("OK"));
                }else {
                    Event Event = new Event();
                    Event.setTitleEvent(TitleEv.getText());
                    Event.setDescription(content.getText());
                    Event.setLocation(Location.getText());
                    Event.setIdOrganizer(1010101);
                    if( EventServices.getInstance().addevent(Event)){
                        Dialog.show("Success","Connection accepted",new Command("OK"));
                    }else {
                        Dialog.show("Failed","Connection rejected",new Command("OK"));
                    }
                }
            }
        });



        add(BorderLayout.CENTER, by);

        //for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);

    }
}
