package org.austral.ing.lab1;

import spark.Spark;

import static spark.Spark.port;
import static spark.Spark.staticFiles;

public class HCSystemService {
    private final Routes routes = new Routes();

        public void start() {
            startWebServer();
        }

        public void stop() {
            stopWebServer();
        }

        private void startWebServer() {
            staticFiles.location("public");
            port(4321);
            final HCSystem system = HCSystem.create("lab1");
            routes.create(system);
        }

        private void stopWebServer() {
            Spark.stop();
        }

}

