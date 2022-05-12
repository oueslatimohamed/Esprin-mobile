/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

package Views;

import com.codename1.ui.*;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.util.Resources;

/**
 * A swipe tutorial for the application
 *
 * @author Shai Almog
 */
public class WalkthruForm extends Form {
    public WalkthruForm(Resources res) {
        super(new LayeredLayout());
        getTitleArea().removeAll();
        getTitleArea().setUIID("Container");
        
        setTransitionOutAnimator(CommonTransitions.createUncover(CommonTransitions.SLIDE_HORIZONTAL, true, 400));

        
        Image notes = res.getImage("Logo.png");

        Label notesPlaceholder = new Label("","ProfilePic");
        Label notesLabel = new Label(notes, "ProfilePic");
        Component.setSameHeight(notesLabel, notesPlaceholder);
        Component.setSameWidth(notesLabel, notesPlaceholder);
        Label bottomSpace = new Label();


        Button ProfessorBtn = new Button("Professor");
        Button StudentBtn = new Button("Student");
        Button ClubBtn = new Button("Club");




        ProfessorBtn.addActionListener(e -> new AddProfessor(res).show());

        StudentBtn.addActionListener(e -> new AddStudent(res).show());

        ClubBtn.addActionListener(e -> new AddClub(res).show());

        /* ******* */
        Button skip = new Button("Already have an account?");
        skip.setUIID("SkipButton");


        skip.addActionListener(e -> new LoginForm(res).show());


        Container tab1 = BorderLayout.centerAbsolute(BoxLayout.encloseY(
                notesPlaceholder,
                new Label("Select your Role", "WalkthruWhite"),
                ProfessorBtn,
                StudentBtn,
                ClubBtn,
                bottomSpace,
                skip
        ));
        tab1.setUIID("WalkthruTab1");


        add(BorderLayout.CENTER, tab1);

        // for low res and landscape devices
        tab1.setScrollableY(true);
        tab1.setScrollVisible(false);


        // visual effects in the first show
        addShowListener(e -> {
            notesPlaceholder.getParent().replace(notesPlaceholder, notesLabel, CommonTransitions.createFade(1500));
        });
    }    
}
