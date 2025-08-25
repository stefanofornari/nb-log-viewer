/*
 * Copyright (c) 2025 Stefano Fornari
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ste.netbeans.logging;

import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.openide.awt.ActionReference;

/**
 * Top component which displays log messages and allows runtime configuration
 * of logging levels for specific loggers.
 */
@ConvertAsProperties(
        dtd = "-//ste.netbeans.logging.logging//LogViewer//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "LogViewerTopComponent",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS,
        iconBase = "ste/netbeans/logging/log-16x16.png"
)
@TopComponent.Registration(mode = "output", openAtStartup = false)
@ActionID(category = "Window", id = "ste.netbeans.logging.LogViewerTopComponent")
@ActionReference(path = "Menu/Window", position = 5000)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_LogViewerAction",
        preferredID = "LogViewerTopComponent"
)
@Messages({
    "CTL_LogViewerAction=Show Log Viewer",
    "CTL_LogViewerTopComponent=Log Viewer Window",
    "HINT_LogViewerTopComponent=This is a Log Viewer window"
})
public final class LogViewerTopComponent extends TopComponent {

    /**
     * Text area to display log messages.
     */
    private JTextArea logTextArea;
    /**
     * Input field for the logger name.
     */
    private JTextField loggerNameField;
    /**
     * Dropdown (combobox) to select the logging level.
     */
    private JComboBox<String> logLevelComboBox;
    /**
     * Button to apply the logging configuration changes.
     */
    private JButton applyButton;

    /**
     * Constructs a new {@code LogViewerTopComponent}.
     * Initializes the UI components and sets the component's name and tooltip.
     */
    public LogViewerTopComponent() {
        initComponents();
        setName(Bundle.CTL_LogViewerTopComponent());
        setToolTipText(Bundle.HINT_LogViewerTopComponent());

    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Top panel for controls
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlPanel.add(new JLabel("Logger Name:"));
        loggerNameField = new JTextField(20);
        controlPanel.add(loggerNameField);

        controlPanel.add(new JLabel("Level:"));
        logLevelComboBox = new JComboBox<>(new String[]{"ALL", "FINEST", "FINER", "FINE", "CONFIG", "INFO", "WARNING", "SEVERE", "OFF"});
        controlPanel.add(logLevelComboBox);

        applyButton = new JButton("Apply");
        applyButton.addActionListener(e -> applyLoggerConfiguration());
        controlPanel.add(applyButton);

        add(controlPanel, BorderLayout.NORTH);

        // Center for log display
        logTextArea = new JTextArea();
        logTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logTextArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * This method is called whenever the TopComponent's properties are being written.
     * It stores the current version of the component.
     * @param p The properties object to write to.
     */
    void writeProperties(java.util.Properties p) {
        // better to store only visible properties
        p.setProperty("version", "1.0");
    }

    /**
     * This method is called whenever the TopComponent's properties are being read.
     * It reads the version of the component.
     * @param p The properties object to read from.
     */
    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
    }

    /**
     * Appends a new log message to the log display area.
     * The text area automatically scrolls to the end to show the latest message.
     * @param message The log message to append.
     */
    public void appendLog(String message) {
        logTextArea.append(message + "\n");
        logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
    }

    /**
     * Applies the logging configuration based on user input.
     * Retrieves the logger name and level from the UI, and sets the logger's level.
     * Provides feedback in the log area if the logger name is empty or the configuration is applied.
     */
    private void applyLoggerConfiguration() {
        String loggerName = loggerNameField.getText();
        String levelString = (String) logLevelComboBox.getSelectedItem();

        if (loggerName == null || loggerName.trim().isEmpty()) {
            appendLog("Logger name cannot be empty.");
            return;
        }

        Logger logger = LogManager.getLogManager().getLogger(loggerName);
        if (logger == null) {
            // Logger does not exist, create it
            logger = Logger.getLogger(loggerName);
        }

        Level newLevel = Level.parse(levelString);
        logger.setLevel(newLevel);

        appendLog("Configuration applied: Logger '" + loggerName + "' set to level " + newLevel.getName());
    }
}