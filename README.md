# Vigilia

## Build

To build the project, you need the following:

- Java 23 or newer in your PATH or JAVA_HOME
- on Linux you need to have `dpkg` and `fakeroot` installed
- on Windows, you need to have WiX Toolset installed (https://wixtoolset.org/)

> Note: On Windows use WiX 3.14 to create the installer. Newer versions might not be supported by JPackage.

Then run the following command:

```bash
./mvnw package jpackage:jpackage
```

if you don't need the installer, you can run:

```bash
./mvnw package
```
