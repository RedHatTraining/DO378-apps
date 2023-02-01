package com.redhat.training;

public class Speaker {

    public String name;
    public String uuid;

    public static Speaker fromUUID( String uuid ) {
        Speaker speaker = new Speaker();
        speaker.uuid = uuid;
        return speaker;
    }

    public static Speaker from( SpeakerFromService speakerFromService ) {
        Speaker speaker = new Speaker();
        enrichFromService( speakerFromService, speaker );
        return speaker;
    }

    public static void enrichFromService( SpeakerFromService speakerFromService, Speaker speaker ) {
        speaker.name = speakerFromService.nameFirst + " " + speakerFromService.nameLast;
        speaker.uuid = speakerFromService.uuid;
    }

}
