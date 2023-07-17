package c4_media_view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class MediaController implements Initializable {

	@FXML private Button btnPlay, btnPause, btnStop;
	@FXML private ProgressBar progressBar;
	@FXML private ProgressIndicator progressIndicator;
	@FXML private Slider sliderVolume, sliderPlay;
	@FXML private Label lblTime;
	
	// Media 재생할 공간
	@FXML private MediaView mediaView;
	
	// MediaView를 통해 재생되는 resource를 제어하는 객체
	private MediaPlayer mediaPlayer;
	// 재생해야할 resource 정보를 저장하는 객체
	private Media media;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// slider min = 0 , max = 100;
		// media에서 volume을 0 ~ 1.0
		sliderVolume.setValue(0.5);
		// progress == 0 ~ 1.0으로 수치 표현
		progressBar.setProgress(0.5);
		progressIndicator.setProgress(0.5);
		
		media = new Media(
				getClass().getResource("/media/video.m4v").toString()
		);
		
		init();
	} // end initialize
	
	// 재생할 resource가 등록이 되면
	// mediaPlayer를 초기화 하는 method
	public void init() {
		
		if(mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer = null;
		}
		
		mediaPlayer = new MediaPlayer(media);
		mediaView.setMediaPlayer(mediaPlayer);
		// 새로운 미디어가 추가 되면 프로그래스 바, 인디케이터, 레이블을 초기화
		setProgress(0.0,"0/0 sec");
		// 미디어 플레이어 상태에 따른 부가 기능 초기화
		setMediaPlayer();
		
		// sliderPlay를 이용하여 미디어 재생 위치 제어
		sliderPlay.valueProperty().addListener((t,o,n)->{
			Duration totalDuration = mediaPlayer.getTotalDuration();
			// sliderPlay 지정된 현재 수치
			// 0 ~ 100
			double value = sliderPlay.getValue() / 100.0;
			// value = newValue
			// 전체 재생시간을 1/1000 단위로 반환
			double totalValue = totalDuration.toMillis();
			// 재생위치를 slider의 현재 조각 값으로 계산
			double now = totalValue * value;
			// 계산된 재생 시간 위치 정보로 Duration 객체 생성
			Duration duration = new Duration(now);
			// 미디어 플레이어에 재생 위치를
			// 전달 받은 duration 객체에 시간으로 변경
			mediaPlayer.seek(duration);
			mediaPlayer.play();
		});
		
		// medidaPlayer volume 조절
		sliderVolume.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(
					ObservableValue<? extends Number> observable, 
					Number oldValue, 
					Number newValue) {
				// 0 ~ 1.0
				double volume = newValue.doubleValue();
				System.out.println("volume : " + volume);
				// 미디어 플레이어 볼륨 정보 변경
				// 0 ~ 1.0 까지의 실수값으로 조정
				mediaPlayer.setVolume(volume);
			}
		});

		// 재생 일시정지 멈춤 버튼 이벤트 초기화
		btnPlay.setOnAction((e)->{
			// 미디어 재생
			mediaPlayer.play();
			// 미디어 플레이어 의 재생 시간 변경을 감지하여 변경 시간정보로
			// progress를 변경
			// 재생 하고 있는 현재 시간 속성
			mediaPlayer.currentTimeProperty()
			// javafx.util.Duration == 시간 간격을 나타내는 클래스
			// 특정 시간 단위를 기반으로 지속시간(duration)을 나타내는데 사용
			.addListener(new ChangeListener<Duration>() {
				@Override
				public void changed(ObservableValue<? extends Duration> observable, 
						Duration oldValue,
						Duration newValue) {
					System.out.println(newValue);
					// 현재 재생 중인 파일의 전체 재생시간을 초단위로 읽어옴.
					Duration duration = mediaPlayer.getTotalDuration();
					double totalTime = duration.toSeconds();
					System.out.println(duration);
					System.out.println(totalTime);
					double currentTime = newValue.toSeconds();
					String lblTxt = (int) currentTime+"/"+ (int) totalTime+" sec";
					double progress = currentTime / totalTime;
					// lblTime.setText(lblTxt);
					setProgress(progress, lblTxt);
				}
			});
		});
		
		btnPause.setOnAction((e)->{
			// 일시정지
			mediaPlayer.pause();
		});
		
		btnStop.setOnAction((e)->{
			// 재생 중지
			mediaPlayer.stop();
		});
	} // end init
	
	// progress , Label 작성 초기화
	public void setProgress(double p , String lblText) {
		// 프로그래스 바 of 이디케이터는 0 ~ 1.0 으로 진행 상황을 표현
		progressBar.setProgress(p);
		progressIndicator.setProgress(p);
		lblTime.setText(lblText);
	}
	
	// 미디어 플레이어 상태에 따라 호출되는 method
	public void setMediaPlayer() {
		// 재생 준비가 완료되었을 때
		// 각 method들은 미디어 제어와 독립적으로 수행되어야 될 부가적인 기능을
		// 전달 받기 때문에 독립적인 작업을 수행 할 수 있도록 Runnable interface
		// 구현 객체로 작업을 전달 받음.
		mediaPlayer.setOnReady(new Runnable() {
			
			@Override
			public void run() {
				// disable == 비활성화 - 사용자와 상호작용 불가능
				btnPlay.setDisable(false);
				btnPause.setDisable(true);
				btnStop.setDisable(true);
			}
		});
		
		// play 상태 일 경우
		mediaPlayer.setOnPlaying(()->{
			btnPlay.setDisable(true);
			btnPause.setDisable(false);
			btnStop.setDisable(false);
		});
		
		// 일시 정지 상태
		mediaPlayer.setOnPaused(()->{
			btnPlay.setDisable(false);
			btnPause.setDisable(true);
			btnStop.setDisable(false);
		});
		
		
		// stop - 중지 상태
		mediaPlayer.setOnStopped(()->{
			// mediaPlayer에 등록된 Media의 재생 시작 시간을 가져옴
			Duration duration = mediaPlayer.getStartTime();
			mediaPlayer.seek(duration);
			
			btnPlay.setDisable(false);
			btnPause.setDisable(true);
			btnStop.setDisable(true);
		});
		
		mediaPlayer.setOnEndOfMedia(()->{
			btnPlay.setDisable(false);
			btnPause.setDisable(true);
			btnStop.setDisable(true);
			mediaPlayer.stop();
		});
		
	}	// end setMediaPlayer
	
	
	// Media 교체 이벤트 처리 - 재생 파일 변경
	public void changeResource(ActionEvent e) {
		Button btn = (Button) e.getTarget();
		String text = btn.getText();
		System.out.println(text);
		
		String path = "";
		switch (text) {
		
		case "영상1":
			path = "/media/video.m4v";
			break;

		case "영상2":
			path = "/media/video.mp4";
			break;
			
		case "음악1":
			path = "/media/audio.mp3";
			break;
			
		case "음악2" :
			path = "/media/audio.wav";
			break;
		} // end switch
		URL url = getClass().getResource(path);
		media = new Media(url.toString());
		init();
	}

}
