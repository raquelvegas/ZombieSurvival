package zombieSurvival.configuracionesAdicionales;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogConfig {
    private static final Logger LOGGER = Logger.getLogger("ApocalipsisLogger");

    static {
        try {
            // Crea el handler para escribir en el archivo
            FileHandler fileHandler = new FileHandler("apocalipsis.txt", true); // true = append
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.ALL);

            // Limpia otros handlers por defecto y agrega el nuevo
            LOGGER.setUseParentHandlers(false);
            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.ALL);

        } catch (IOException e) {
            System.err.println("No se pudo crear el archivo de log: " + e.getMessage());
        }
    }


    public synchronized void logInfo(String mensaje) {
        LOGGER.info(mensaje);
    }

    public synchronized void logWarning(String mensaje) {
        LOGGER.warning(mensaje);
    }
}
