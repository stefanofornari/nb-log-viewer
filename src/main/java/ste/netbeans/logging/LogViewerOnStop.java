/*
 * Copyright 2025 Stefano Fornari.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ste.netbeans.logging;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.openide.modules.OnStop;

/**
 *
 */
@OnStop
public class LogViewerOnStop implements Runnable{
    @Override
    public void run() {
        System.out.println("LogViewer OnStop");

        final Logger rootLogger = LogManager.getLogManager().getLogger("");

        List<Handler> toRemove = new ArrayList();
        for (Handler h: rootLogger.getHandlers()) {
            if (h instanceof LogViewerHandler) {
                toRemove.add(h);
            }
        }

        toRemove.forEach((h) -> rootLogger.removeHandler(h));
    }
}
