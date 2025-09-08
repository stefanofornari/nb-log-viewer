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

import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.openide.modules.OnStart;

/**
 * Initializes and attaches the custom {@link LogViewerHandler} to the root logger
 * when the NetBeans module is started.
 * <p>
 * This class is automatically invoked by the NetBeans platform during module initialization
 * via the {@link OnStart} annotation. By registering the {@code LogViewerHandler} with the root
 * java.util.logging logger, we enable integration between the standard Java logging framework and
 * a dedicated NetBeans UI component (typically {@code LogViewerTopComponent}), making log records
 * available to the user interface for real-time viewing, filtering, or further processing.
 * </p>
 * <p>
 * We can customize log handling across the entire application by changing the handler registered
 * here. Typically, this setup is only required once per application lifecycle, and no manual
 * invocation is necessary — the NetBeans runtime handles triggering the {@code run()} method at
 * module startup.
 * </p>
 *
 * <pre>
 * Example usage scenario:
 *   - Start the NetBeans-based application.
 *   - As soon as this module is activated, the root logger will forward log records to the
 *     {@code LogViewerHandler}, which routes them to the visual log viewer component.
 * </pre>
 *
 * <strong>Threading:</strong>
 * <p>
 * This method is called by NetBeans infrastructure, so we do not need to handle threading concerns.
 * </p>
 *
 * @see LogViewerHandler
 * @see LogViewerTopComponent
 * @see java.util.logging.Logger
 * @see org.openide.modules.OnStart
 */
@OnStart
public class LogViewerOnStart implements Runnable {

    /**
     * This method is called when the module is loaded and restored.
     * It retrieves the root logger and adds an instance of {@link LogViewerHandler}
     * to ensure all log records are routed to the LogViewerTopComponent.
     */
    @Override
    public void run() {
        System.out.println("LogViewer OnStart");
        Logger rootLogger = LogManager.getLogManager().getLogger("");
        rootLogger.addHandler(new LogViewerHandler());
    }
}