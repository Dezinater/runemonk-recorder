package com.runemonk;

import net.runelite.client.RuneLiteProperties;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.config.RuneLiteConfig;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;

import javax.inject.Inject;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import lombok.extern.slf4j.*;
import net.runelite.http.api.RuneLiteAPI;

@Slf4j
public class RuneMonkRecorderPanel extends PluginPanel {

    @Inject
    private RuneMonkRecorder runeMonkRecorder;

    @Inject
    private ConfigManager configManager;

    public RuneMonkRecorderPanel() {
        /*
        setBackground(ColorScheme.DARK_GRAY_COLOR);
        setBorder(new EmptyBorder(10, 10, 10, 10));


        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
*/
        super();
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        JButton selectDirectoryButton = new JButton("Select Folder");

        selectDirectoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showDialog(RuneMonkRecorderPanel.this, "");
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    configManager.setConfiguration("runemonk", "rsrecfolder", fc.getSelectedFile().getAbsolutePath());
                }
            }
        });


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


    public void actionPerformed(ActionEvent e) {
        log.info(e.getSource().toString());
    }
}
