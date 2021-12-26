package com.runemonk;

import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;

import javax.inject.Inject;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import lombok.extern.slf4j.*;

@Slf4j
public class RuneMonkRecorderPanel extends PluginPanel {

    @Inject
    private RuneMonkRecorder runeMonkRecorder;

    public RuneMonkRecorderPanel() {
        setBackground(ColorScheme.DARK_GRAY_COLOR);
        setBorder(new EmptyBorder(10, 10, 10, 10));


        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JButton startButton = new JButton("Start Recording");
        JButton stopButton = new JButton("Stop Recording");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                log.info("Start pressed");
                runeMonkRecorder.startRecording();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                log.info("Stop pressed");
                runeMonkRecorder.stopRecording();
            }
        });

        add(startButton);
        add(stopButton);
        revalidate();
        repaint();
    }

}
