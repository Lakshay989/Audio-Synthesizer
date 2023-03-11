package com.example.synthesizer;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javax.sound.sampled.*;



import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.util.ArrayList;

public class SynthesizeApplication extends Application {

    AnchorPane mainCanvas_ ;
    public static Circle Speaker ;
    public static ArrayList<AudioComponentWidget> widgets_ = new ArrayList<>() ;

    public static ArrayList<AudioComponentWidget> connectedWidgetsToSpeaker_ = new ArrayList<>() ;

    public static ArrayList<VolumeComponentWidget> connectedVolumeWidgetsToSpeaker_ = new ArrayList<>() ;

    public static double volume_ ;
    @Override
    public void start( Stage stage) throws IOException {

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 600, 400);

        stage.setTitle("Audio Synthesizer");

        ///////////////////////////////////////////////////

        // Right Pane of the Border Pane
        // Adding Buttons

        VBox rightPanel = new VBox();

        rightPanel.setPadding(new Insets(10));
        rightPanel.setStyle("-fx-background-color: darkgrey");


        Button SineWaveButton = new Button("Sine Wave");
        rightPanel.getChildren().add(SineWaveButton);
        SineWaveButton.setOnAction(e -> createComponent("SineWave"));

//
//        Button VolumeButton = new Button("Volume") ;
//        rightPanel.getChildren().add(VolumeButton) ;
//        VolumeButton.setOnAction(e -> createComponentVolume("Volume"));

        /////////////////////////////////////////////////////////
        // Center Panel

        mainCanvas_ = new AnchorPane() ;
        mainCanvas_.setStyle("-fx-background-color:grey");

        Speaker = new Circle(400,200,15) ;
        Speaker.setFill(Color.BLACK);
        mainCanvas_.getChildren().add(Speaker);

        ////////////////////////////////////////////////////////////
        // Bottom panel
        HBox bottomPanel = new HBox() ;
        Button playButton = new Button("Play") ;
        bottomPanel.getChildren().add(playButton);
        playButton.setOnAction(e -> playNetwork());

        ////////////////////////////////////////////////////////////////////////////////////////////////////

        HBox volLayout =new HBox();
        volLayout.setStyle("-fx-border-color: black ; -fx-border-image-width: 5 ; -fx-background-color: white");
        VBox volRightSide =new VBox() ;
        //Button volCloseButton = new Button("x") ;
//closeButton.setOnAction(e -> closeWidget());

        //volRightSide.getChildren().add(volCloseButton);
        volRightSide.setAlignment(Pos.CENTER);
        volRightSide.setPadding(new Insets(5));
        volRightSide.setSpacing(5);

        VBox volCenter = new VBox() ;
        volCenter.setStyle("-fx-background-color: skyblue");
        volCenter.setAlignment(Pos.CENTER);
        Label volNameLabel_ = new Label() ;
        volNameLabel_.setMouseTransparent(true);
        volNameLabel_.setText("Volume");

        Slider volSlider = new Slider(0,1, 0.5) ;
        Label volLabel = new Label() ;
        volLabel.setMouseTransparent(true);
        volLabel.setText("Volume: 0.5");
        volCenter.getChildren().add(volLabel);
        volCenter.getChildren().add(volSlider);

        volLayout.getChildren().add(volRightSide);
        volLayout.getChildren().add(volCenter);
        mainCanvas_.getChildren().add(volLayout);
        volSlider.setOnMouseDragged( e -> handleVolSlider(e,volSlider,volLabel));


        root.setRight(rightPanel);
        root.setCenter(mainCanvas_);
        root.setBottom(bottomPanel);

        stage.setScene(scene);
        stage.show();
    }

    private void handleVolSlider(MouseEvent e, Slider volSlider, Label volLabel)
    {
        // double value = (double) slider.getValue() ;
        double value = (double) Math.round(volSlider.getValue() * 10) / 10;
        volLabel.setText("Volume(" + value + ")");
        volume_ = value ;
        //audioComponent_ = new Filters(value) ;
    }

    private void playNetwork()
    {
        if(widgets_.size() == 0)
        {
            return ;
        }
        try {
            Clip c = AudioSystem.getClip();

            AudioListener listener = new AudioListener(c) ;

            Mixer mixer = new Mixer();
            AudioComponent ac = null ;
            for(AudioComponentWidget w : connectedWidgetsToSpeaker_)
            {
                ac = w.getAudioComponent() ;
                mixer.connectInput(ac);
            }


            AudioFormat format = new AudioFormat( 44100, 16, 1, true, false );

            //AudioComponentWidget acw = widgets_.get(0) ;
            //AudioComponent ac = acw.getAudioComponent();
            Filters filter = new Filters(volume_) ;
            filter.connectInput(mixer);
            byte[] data = filter.getClip().getData();

            c.open( format, data, 0, data.length ); // Reads data from our byte array to play it.
            c.start();

            c.addLineListener(listener);
        }
        catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    private void createComponent(String sineWave)
    {
        AudioComponent sinewave = new SineWave(440) ;
        AudioComponentWidget acw = new AudioComponentWidget(sinewave,mainCanvas_,sineWave) ;
        System.out.println("Component Created ");
        widgets_.add(acw) ;
    }

//    private void createComponentVolume(String volume)
//    {
//        AudioComponent volumecontrol = new Filters(0.5) ;
//        VolumeComponentWidget vcw = new VolumeComponentWidget(volumecontrol,mainCanvas_, volume);
//        connectedVolumeWidgetsToSpeaker_.add(vcw) ;
//    }

    public static void main(String[] args) {
        launch();
    }
}