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

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;
import org.openide.windows.WindowManager;
import java.awt.EventQueue;
import java.util.Set;
import java.util.TreeSet;

/**
 * A custom {@link java.util.logging.Handler} that directs log records
 * to the {@link LogViewerTopComponent} for display.
 */
public class LogViewerHandler extends Handler {

    private LogViewerTopComponent logViewerTopComponent;

    private final Set<String> collectedLoggers = new TreeSet();

    /**
     * Constructs a new {@code LogViewerHandler}.
     * It attempts to find the {@link LogViewerTopComponent} and sets a default
     * {@link java.util.logging.SimpleFormatter}.
     */
    public LogViewerHandler() {
        logViewerTopComponent = (LogViewerTopComponent) WindowManager.getDefault().findTopComponent("LogViewerTopComponent");
        if (logViewerTopComponent == null) {
            // Handle case where TopComponent is not yet initialized or found
            // This might happen if the module is loaded before the UI is fully read
            // For now, we'll just set it to null and handle it in publish
        }
        setFormatter(new SimpleFormatter());
    }

    @Override
    public void publish(LogRecord record) {
        //
        // collect the logger name even if it won't be displayed
        //
        final String loggerName = record.getLoggerName();
        if (loggerName != null) {
            collectedLoggers.add(record.getLoggerName());
        }

        if (!isLoggable(record)) {
            return;
        }
        final String msg = getFormatter().format(record);

        // Ensure UI updates are done on the Event Dispatch Thread
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (logViewerTopComponent != null) {
                    logViewerTopComponent.logViewerPanel.appendLog(msg);
                } else {
                    // Try to find the TopComponent again if it was null initially
                    logViewerTopComponent = (LogViewerTopComponent) WindowManager.getDefault().findTopComponent("LogViewerTopComponent");
                    if (logViewerTopComponent != null) {
                        logViewerTopComponent.logViewerPanel.appendLog(msg);
                    }
                }
            }
        });
    }

    /**
     * Flushes any buffered output. For this handler, messages are appended immediately,
     * so this method does nothing.
     */
    @Override
    public void flush() {
        // No-op for this handler as messages are appended immediately
    }

    /**
     * Closes the handler and releases any associated resources. For this handler,
     * there are no resources to release, so this method does nothing.
     * @throws SecurityException if a security manager exists and if the caller
     *         does not have {@code LoggingPermission("control")}.
     */
    @Override
    public void close() throws SecurityException {
        // No-op for this handler
    }
}