# Installation

1. Install [Java 11 +](https://jdk.java.net/).
2. Get the [JavaFX SDK](https://gluonhq.com/products/javafx/) for your platform and unzip it. Let's call the path to the unzipped folder `${JFX_SDK}`.
3. Run `java --module-path ${JFX_SDK}/lib --add-modules=javafx.controls -jar path/to/jar -g path/to/gyms.csv -t path/to/teams.csv`.
