# Vigilia

## Build

To build the project, you need the following:

- Java 23 or newer in your PATH or JAVA_HOME
- on Linux you need to have `dpkg` and some other tools installed. JLink and JPackage will tell you if something is
  missing.
- on Windows, you need to have WiX Toolset installed (https://wixtoolset.org/)

> Note: On Windows use WiX 3.14 to create the installer. Newer versions might not be supported by JPackage.

Then run the following command:

```bash
./mvnw package
./mvnw jpackage:jpackage -pl core,cli,fx-gui
```

if you don't need the installer, you can leave out the second command.
