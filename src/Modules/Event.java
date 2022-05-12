package Modules;

import Utils.Enums.State;

import java.util.Date;

public class Event {

        private int idEvent;
        private  String TitleEvent;
        private String Description;
        private Date DateEvent;
        private Date DateDebut;
        private Date DateFin;
        private int ParticipateNumber;
        private String Location;
        private String ImgURL;
        private long idOrganizer;
        private State state;



        public long getIdOrganizer() {
            return idOrganizer;
        }

        public void setIdOrganizer(long idOrganizer) {
            this.idOrganizer = idOrganizer;
        }

        public int getIdEvent() {
            return idEvent;
        }

        public void setIdEvent(int idEvent) {
            this.idEvent = idEvent;
        }

        public String getTitleEvent() {
            return TitleEvent;
        }

        public void setTitleEvent(String titleEvent) {
            TitleEvent = titleEvent;
        }

        public String getDescription() { return Description; }

        public void setDescription(String description) {
            Description = description;
        }

        public Date getDateEvent() {
            return DateEvent;
        }

        public void setDateEvent(Date dateEvent) {
            DateEvent = dateEvent;
        }

        public String getImgURL() {
            return ImgURL;
        }

        public void setImgURL(String imgURL) {
            ImgURL = imgURL;
        }

        public State getState() { return state; }

        public Date getDateDebut() {
            return DateDebut;
        }

        public void setDateDebut(Date dateDebut) {
            DateDebut = dateDebut;
        }

        public Date getDateFin() {
            return DateFin;
        }

        public void setDateFin(Date dateFin) {
            DateFin = dateFin;
        }

        public int getParticipateNumber() {
            return ParticipateNumber;
        }

        public void setParticipateNumber(int participateNumber) {
            ParticipateNumber = participateNumber;
        }

        public String getLocation() {
            return Location;
        }

        public void setLocation(String location) {
            Location = location;
        }

        public Event(){

        }

        public Event(String TitleEvent, String Description){
            this.TitleEvent=TitleEvent;
            this.Description=Description;
        }

        public Event(String TitleEvent, String Description,Date DateEvent){
            this.TitleEvent=TitleEvent;
            this.Description=Description;
            this.DateEvent=DateEvent;
        }


        public Event(String TitleEvent, String Description, String ImgURL, Date DateEvent, long idOrganizer){
                this.TitleEvent=TitleEvent;
                this.Description=Description;
                this.ImgURL=ImgURL;
                this.DateEvent=DateEvent;
                this.idOrganizer= idOrganizer;
            }


            public Event(int idEvent, String TitleEvent, String Description,String ImgURL,Date DateEvent, long idOrganizer, State state, Date DateDebut, Date DateFin, String Location, int ParticipateNumber ){
                this.idEvent=idEvent;
                this.TitleEvent=TitleEvent;
                this.Description=Description;
                this.ImgURL=ImgURL;
                this.DateEvent=DateEvent;
                this.idOrganizer= idOrganizer;
                this.state=state;
                this.DateDebut=DateDebut;
                this.DateFin=DateFin;
                this.Location=Location;
                this.ParticipateNumber=ParticipateNumber;
            }


            @Override
            public String toString() {
                return "idEvent=" + idEvent +
                        ",  TitleEvent=" + TitleEvent +
                        ",  Description=" + Description +
                        ",  DateEvent=" + DateEvent +
                        ",  ImgURL=" + ImgURL +
                        ",  idOrganizer=" + idOrganizer +
                        ",  State" + state +
                        '\n';
            }
}
