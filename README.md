# NetBeans Log Manager

A NetBeans module designed to provide a centralized view and management interface for `java.util.logging` output within the NetBeans IDE.

## Features

*   **Integrated Log Viewer:** Displays all `java.util.logging` output from the NetBeans IDE in a dedicated output window.
*   **Runtime Configuration:** Allows users to dynamically change the logging level for any specific logger at runtime.
*   **Keyboard Shortcut:** Quickly open the Log Viewer window using `Ctrl+Shift+L`.

## Build Instructions

This project requires **Java 19** to build.

1.  Navigate to the `nb-log-manager` directory:
    ```bash
    cd nb-log-manager
    ```
2.  Set your `JAVA_HOME` environment variable to your Java 19 installation.
    ```bash
    export JAVA_HOME=/usr/lib/jvm/java-19-openjdk-amd64 # Adjust this path to your Java 19 installation
    ```
3.  Build the module using Maven:
    ```bash
    mvn clean install
    ```

## Usage

1.  **Run NetBeans with the module:**
    After building, you can run a NetBeans instance with your module installed:
    ```bash
    JAVA_HOME=/usr/lib/jvm/java-19-openjdk-amd64 mvn netbeans:run
    ```
2.  **Open the Log Viewer:**
    *   From the NetBeans menu bar, navigate to **Window > LogViewer**.
    *   Alternatively, use the keyboard shortcut: `Ctrl+Shift+L`.

3.  **Configure Logging Levels:**
    Inside the Log Viewer window, you will find:
    *   An input field for **Logger Name**: Enter the full name of the logger you wish to configure (e.g., `org.netbeans.modules.options`).
    *   A **Level** dropdown: Select the desired logging level (e.g., `INFO`, `FINE`, `ALL`, `OFF`).
    *   An **Apply** button: Click this to apply the changes to the specified logger.

## License

This project is licensed under the Apache License, Version 2.0. See the source code files for individual copyright notices.
