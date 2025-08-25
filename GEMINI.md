- Create a NetBeans module that opens a window in the "output" window group.
- The window will show the logs produced by NetBeans.
- Logs in NetBeans are managed by `java.util.logging`.
- The application shall allows to change the logging configuration runtime. ie: the user can specify the name of a logger and which level that logger should be. The window will show all logs produced by any `java.util.logging` accordingly to such configuration.
- The `LogViewerTopComponent` should include UI elements for logging configuration:
    - An input field for the logger name.
    - A dropdown (combobox) to select the logging level.
    - A button to apply the changes.

## Build Instructions
- When invoking Maven, ensure the correct Java installation is used by setting `JAVA_HOME`.
- To build the project, use: `JAVA_HOME=/usr/lib/jvm/java-19-openjdk-amd64 mvn clean install`