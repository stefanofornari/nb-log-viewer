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
 * Initializes the custom {@link LogViewerHandler} and attaches it to the root logger
 * when the NetBeans module starts.
 */
@OnStart
public class LogInitializer implements Runnable {

    /**
     * This method is called when the module is loaded and restored.
     * It retrieves the root logger and adds an instance of {@link LogViewerHandler}
     * to ensure all log records are routed to the LogViewerTopComponent.
     */
    @Override
    public void run() {
        Logger rootLogger = LogManager.getLogManager().getLogger("");
        rootLogger.addHandler(new LogViewerHandler());
    }
}