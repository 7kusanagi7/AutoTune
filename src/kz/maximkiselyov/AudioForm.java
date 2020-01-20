package kz.maximkiselyov;

import javax.swing.*;

public class AudioForm {
    private JPanel formPanel;
    private JButton captureBtn;
    private JButton stopBtn;
    private JButton playBtn;

    public AudioForm() {

        AudioCapturer capturer = AudioCapturer.getInstance();

        captureBtn.addActionListener(actionEvent -> {

            captureBtn.setEnabled(false);
            playBtn.setEnabled(false);
            stopBtn.setEnabled(true);

            capturer.audioCapture();
        });

        stopBtn.addActionListener(actionEvent -> {

            stopBtn.setEnabled(false);
            captureBtn.setEnabled(true);
            playBtn.setEnabled(true);

            capturer.captureStop();
        });

        AudioPlayer player = AudioPlayer.getInstance();

        playBtn.addActionListener(actionEvent -> player.audioPlay(capturer.getAudioData()));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Capture/Play demo");
        frame.setContentPane(new AudioForm().formPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
