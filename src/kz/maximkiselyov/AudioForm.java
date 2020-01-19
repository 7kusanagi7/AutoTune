package kz.maximkiselyov;

import javax.swing.*;

public class AudioForm {
    private JPanel formPanel;
    private JButton captureBtn;
    private JButton stopBtn;
    private JButton playBtn;

    private boolean isCapture = false;

    public AudioForm() {
        captureBtn.addActionListener(actionEvent -> {
            isCapture = true;

            captureBtn.setEnabled(false);
            playBtn.setEnabled(false);
            stopBtn.setEnabled(true);

            audioCapture();
        });

        stopBtn.addActionListener(actionEvent -> {
            isCapture = false;

            stopBtn.setEnabled(false);
            captureBtn.setEnabled(true);
            playBtn.setEnabled(true);
        });

        playBtn.addActionListener(actionEvent -> {
            audioPlay();
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Capture/Play demo");
        frame.setContentPane(new AudioForm().formPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
