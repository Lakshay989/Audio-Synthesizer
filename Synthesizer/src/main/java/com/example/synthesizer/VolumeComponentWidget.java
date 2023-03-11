package com.example.synthesizer;

import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.control.Slider ;
import javafx.scene.shape.Line;

import java.text.DecimalFormat;

public class VolumeComponentWidget extends Pane {

    private AudioComponent audioComponent_ ;
    private AnchorPane parent_ ;

    private HBox baseLayout ;

    private String name_ ;

    private Label nameLabel_ ;

    //////////////////////////////////////
    double mouseStartDragX_,mouseStartDragY_,widgetStartDragX_,widgetStartDragY_ ;

    private Line line_ ;

    VolumeComponentWidget(AudioComponent ac, AnchorPane parent, String name)
    {

        audioComponent_ = ac ;
        parent_ =parent ;
        name_ = name ;

        baseLayout =new HBox() ;
        baseLayout.setStyle("-fx-border-color: black ; -fx-border-image-width: 5 ; -fx-background-color: white");
        // Right Side
        VBox rightSide =new VBox() ;
        Button closeButton = new Button("x") ;
        closeButton.setOnAction(e -> closeWidget() );
        Circle output =new Circle(10) ;
        output.setFill(Color.BLUE);


        rightSide.getChildren().add(closeButton) ;
        rightSide.getChildren().add(output) ;

        rightSide.setAlignment(Pos.CENTER);
        rightSide.setPadding(new Insets(5));
        rightSide.setSpacing(5);



        output.setOnMousePressed(e -> startConnection(e, output));
        output.setOnMouseDragged(e -> moveConnection(e, output));
        output.setOnMouseReleased(e -> endConnection(e, output));

        //////////////////////////////////////////////////////////////////
        //Left Portion of the Widget

        VBox leftSide =new VBox() ;
        Circle input = new Circle(10) ;
        input.setFill(Color.GREEN);

        leftSide.setAlignment(Pos.CENTER);
        leftSide.setPadding(new Insets(5));
        leftSide.setSpacing(5);


        leftSide.getChildren().add(input) ;

        input.setOnMousePressed(e -> startConnection(e, input));
        input.setOnMouseDragged(e -> moveConnection(e, input));
        input.setOnMouseReleased(e -> endConnection(e, input));

        //////////////////////////////////////////////////////////////////
        // Center Portion of Widget
        VBox center = new VBox() ;
        center.setStyle("-fx-background-color: skyblue");
        center.setAlignment(Pos.CENTER);

        nameLabel_ = new Label() ;
        nameLabel_.setMouseTransparent(true);
        nameLabel_.setText("Volume (0.5)");

        javafx.scene.control.Label title = new javafx.scene.control.Label();
        title.setText("Volume (0.5)");

        Slider VolumeSlider = new Slider(0,1,0.5) ;
        VolumeSlider.setOnMouseDragged(e -> VolumehandleSlider(e,VolumeSlider,nameLabel_));

        center.getChildren().add(nameLabel_) ;
        center.getChildren().add(VolumeSlider) ;

        center.setOnMousePressed(e -> startDrag(e));
        center.setOnMouseDragged(e -> handleDrag(e));

        //////////////////////////////////////////////////////////

        baseLayout.getChildren().add(center) ;
        baseLayout.getChildren().add(rightSide) ;
        baseLayout.getChildren().add(leftSide) ;

        this.getChildren().add(baseLayout) ;


        this.setLayoutX(50);
        this.setLayoutY(100);

        parent_.getChildren().add(this);

    }

    private void startDrag(MouseEvent e)
    {
        mouseStartDragX_ = e.getSceneX();
        mouseStartDragY_ = e.getSceneY();

        widgetStartDragX_ = this.getLayoutX() ;
        widgetStartDragY_ = this.getLayoutY() ;
    }

    private void handleDrag(MouseEvent e)
    {
        double mouseDelX = e.getSceneX() - mouseStartDragX_ ;

        double mouseDelY = e.getSceneY() - mouseStartDragY_ ;

        this.relocate(widgetStartDragX_+mouseDelX,widgetStartDragY_+ mouseDelY);
    }


    //////////////////////////////////////////////////////////////////

    private void endConnection(MouseEvent e, Circle output)
    {
        Circle speaker = SynthesizeApplication.Speaker;
        Bounds speakerBounds = speaker.localToScreen(speaker.getBoundsInLocal()) ;

        double distance = Math.sqrt(Math.pow(speakerBounds.getCenterX() - e.getScreenX(),2.0)+
                Math.pow(speakerBounds.getCenterY() - e.getScreenY(), 2.0)) ;

        if(distance < 10)
        {
            SynthesizeApplication.connectedVolumeWidgetsToSpeaker_.add(this) ;

        }
        else
        {
            parent_.getChildren().remove(line_) ;
            line_ = null ;
        }
    }

    private void moveConnection(MouseEvent e, Circle output)
    {
        Bounds parentbounds = parent_.getBoundsInParent();
        line_.setEndX(e.getSceneX() - parentbounds.getMinX());
        line_.setEndY(e.getSceneY() - parentbounds.getMinY());


    }

    private void startConnection(MouseEvent e, Circle output)
    {
        // If a line already exists we remove that line
        if(line_ != null)
        {
            parent_.getChildren().remove(line_) ;
        }

        Bounds parentBounds = parent_.getBoundsInParent() ;
        Bounds bounds = output.localToScene(output.getBoundsInLocal()) ;

        line_ = new Line() ;
        line_.setStrokeWidth(4) ;
        line_.setStartX(bounds.getCenterX() - parentBounds.getMinX() );
        line_.setStartY(bounds.getCenterY() - parentBounds.getMinY() );
        line_.setEndX(e.getSceneX());
        line_.setEndY(e.getSceneY());

        // For any widget , we have to add the line to the parent
        parent_.getChildren().add(line_) ;
    }
/////////////////////////////////////////////////////////////////////////////

    private void closeWidget()

    {
        parent_.getChildren().remove(this);
        SynthesizeApplication.widgets_.remove(this) ;
        if(line_ != null)
        {
            parent_.getChildren().remove(line_) ;
        }
    }

    public AudioComponent getAudioComponent()
    {
        return audioComponent_ ;

    }


    private void VolumehandleSlider(MouseEvent e, Slider slider, Label title)
    {

       // double value = (double) slider.getValue() ;
        double value = (double) Math.round(slider.getValue() * 10) / 10;
        title.setText("Volume(" + value + ")");
        audioComponent_ = new Filters(value) ;
    }

    private void handleMousePress(MouseEvent e)
    {
        System.out.println("Mouse was pressed");
    }

}

