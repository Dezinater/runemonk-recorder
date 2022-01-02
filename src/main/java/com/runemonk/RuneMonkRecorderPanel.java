package com.runemonk;

import net.runelite.client.RuneLiteProperties;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.config.RuneLiteConfig;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.DynamicGridLayout;
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

    private JTextField directoryField;
    private JLabel ticksCount, recordingStatus;

    public RuneMonkRecorderPanel() {

        setBackground(ColorScheme.DARK_GRAY_COLOR);
        setBorder(new EmptyBorder(10, 10, 10, 10));


        //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel contentPane = new JPanel(new DynamicGridLayout(0, 1, 5, 5));
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel title = new JLabel("RuneMonk Recorder");
        title.setFont(new Font("SansSerif", Font.PLAIN, 18));
        contentPane.add(title);

        JPanel directoryPanel = new JPanel(new DynamicGridLayout(0, 1, 0, 3));
        directoryPanel.setBorder(BorderFactory.createTitledBorder("Folder"));

        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        directoryField = new JTextField();
        directoryField.setEnabled(false);
        JButton selectDirectoryButton = new JButton("Select Folder");
        JButton openDirectoryButton = new JButton("Show Folder");

        directoryPanel.add(directoryField);
        directoryPanel.add(selectDirectoryButton);
        directoryPanel.add(openDirectoryButton);
        contentPane.add(directoryPanel);



        JPanel recordingPanel = new JPanel(new GridLayout(0, 1, 0, 3));
        recordingPanel.setBorder(BorderFactory.createTitledBorder("Recording"));

        ticksCount = new JLabel("Ticks: 0");
        recordingStatus = new JLabel("Recording: Stopped");
        JButton startButton = new JButton("Start Recording");
        JButton stopButton = new JButton("Stop Recording");

        recordingPanel.add(ticksCount);
        recordingPanel.add(recordingStatus);
        recordingPanel.add(startButton);
        recordingPanel.add(stopButton);
        contentPane.add(recordingPanel);


        selectDirectoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showDialog(RuneMonkRecorderPanel.this, "Select Folder");
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    configManager.setConfiguration("runemonk", "rsrecfolder", fc.getSelectedFile().getAbsolutePath());
                    setDirectoryField();
                }
            }
        });

        openDirectoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().open(new File(directoryField.getText()));
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(runeMonkRecorder.isAbleToRecord()) {
                    recordingStatus.setText("Recording: Started");
                    runeMonkRecorder.startRecording();
                }
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recordingStatus.setText("Recording: Stopped");
                runeMonkRecorder.stopRecording();
            }
        });

        add(contentPane);
        //setVisible(true);
        //revalidate();
        //repaint();
    }

    public void setTicksCount(int amount) {
        ticksCount.setText("Ticks: " + amount);
    }

    public void onActivate()
    {
        setDirectoryField();
    }

    public void setDirectoryField() {
        String fieldValue = configManager.getConfiguration("runemonk", "rsrecfolder");
        if(fieldValue == "" || fieldValue == null) {
            fieldValue = System.getProperty("user.home") + "\\Videos";
        }
        directoryField.setText(fieldValue);
    }

    public void actionPerformed(ActionEvent e) {
        log.info(e.getSource().toString());
    }
}
