package com.example.fxmlplayer;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller {
    @FXML
    private Slider vol;

    @FXML
    private Slider time;

    @FXML
    private Label pause;

    @FXML
    private Label play;

    @FXML
    private Label stop;


    @FXML
    private MediaView mediaView;
    private MediaPlayer player;

    @FXML
    public void initialize() {
        String video = getClass().getResource("amifaku.mp4").toExternalForm();
        Media media = new Media(video);
        player = new MediaPlayer(media);
        mediaView.setMediaPlayer(player);

        vol.setValue(player.getVolume() * 100);
        vol.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                player.setVolume(vol.getValue() / 100);
            }
        });
        player.totalDurationProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration newDuration ) {

                time.setMax(newDuration.toSeconds() );


            }
        });

        time.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean wasChanging, Boolean isChanging) {

                if(!isChanging){
                    player.seek(Duration.seconds(time.getValue()));
                }

            }
        });

        time.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                double currentTime= player.getCurrentTime().toSeconds();
                if(Math.abs(currentTime - newValue.doubleValue())> 0.5) {
                    player.seek(Duration.seconds(newValue.doubleValue()));
                };

            }
        });



    }

    @FXML
    void playVideo(MouseEvent event) {
        player.play();
    }


    @FXML
    void stopVideo(MouseEvent event) {
        player.stop();
    }
    @FXML
    void pauseVideo(MouseEvent event) {
        player.pause();
    }


}
