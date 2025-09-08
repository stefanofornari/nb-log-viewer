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

import java.awt.BorderLayout;
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

    public final LogViewerPanel logViewerPanel = new LogViewerPanel();

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

        add(logViewerPanel, BorderLayout.CENTER);
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
    }
}