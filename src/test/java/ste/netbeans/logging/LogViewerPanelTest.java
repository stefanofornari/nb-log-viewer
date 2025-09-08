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

import com.github.caciocavallosilano.cacio.ctc.junit.CacioTest;
import javax.swing.JFrame;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 */
@CacioTest
public class LogViewerPanelTest {

    private FrameFixture window;
    private JFrame frame;
    private LogViewerPanel viewer;

    @BeforeClass
    public static void beforeClass() {
        FailOnThreadViolationRepaintManager.install();
    }

    @BeforeEach
    void beforeEach() {
        // Create a simple JFrame to host the components created by QueryPane
        //application(SimpleCopyApplication.class).start();
        try {
            frame = GuiActionRunner.execute(() -> new JFrame());

            frame.add(viewer = new LogViewerPanel());
            window = new FrameFixture(frame);
            window.show();
        } catch (Throwable t) {
            t.printStackTrace();
            throw t;
        }

        new LogViewerOnStart().run();

        System.out.println(window);
    }

    @AfterEach
    void afterEach() {
        new LogViewerOnStop().run();
    }

    @Test
    public void clear_deletes_all_text() throws Exception {
        viewer.logTextArea.setText("some text");

        window.button("clearButton").click();
        window.textBox("logText").requireText("");
    }

}
