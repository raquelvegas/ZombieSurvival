package zombieSurvival;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ControladorNuevaInterfaz {

    @FXML
    private Button mute, pausa, comoJugar;

    @FXML
    private ToggleGroup grupoIdioma;

    @FXML
    private RadioMenuItem espanol, ingles, frances;
    @FXML
    private MenuButton idioma;

    @FXML
    private Spinner<Cancion> reproductor;

    private MediaPlayer mediaPlayer;
    private boolean isMuted = false;
    private boolean isPausado = false;
    private final List<Cancion> canciones = new ArrayList<>();
    private int cancionActual = 0;
    private int idiomaActual = 0;

    @FXML
    public void cambiarCancionPorFlecha(MouseEvent event) {
        double y = event.getY();
        double height = reproductor.getHeight();

        if (y < height / 2) {
            anteriorCancion(); // Flecha arriba
        } else {
            siguienteCancion(); // Flecha abajo
        }
    }

    @FXML
    void mute(ActionEvent event) {
        if (mediaPlayer != null) {
            // Cambiamos el valor de muteado
            if (isMuted) {
                isMuted = false;
                mediaPlayer.setMute(false);
                mute.setText("🔊");
            } else {
                isMuted = true;
                mediaPlayer.setMute(true);
                mute.setText("🔇");
            }
        }
    }

    @FXML
    void cambiarIdioma(ActionEvent event) {
        RadioMenuItem seleccionado = (RadioMenuItem) grupoIdioma.getSelectedToggle();

        if (seleccionado == espanol) {
            //Cambiar a Español
            idiomaActual = 0;
            actualizarTextos();
        } else if (seleccionado == ingles) {
            //Cambiar a Inglés
            idiomaActual = 1;
            actualizarTextos();
        } else if (seleccionado == frances) {
            //Cambiar a Francés
            idiomaActual = 2;
            actualizarTextos();
        }
    }

    @FXML
    void pausar(ActionEvent event) {
        // Solo cambiar el estado de pausa y el texto del botón, sin afectar la música
        if (isPausado) {
            isPausado = false;
            if (idiomaActual == 0) {
                pausa.setText("⏸ Pausar");
            } else if (idiomaActual == 1) {
                pausa.setText("⏸ Pause");
            } else if (idiomaActual == 2) {
                pausa.setText("⏸ Pause");
            }
        } else {
            isPausado = true;
            if (idiomaActual == 0) {
                pausa.setText("▶ Jugar");
            } else if (idiomaActual == 1) {
                pausa.setText("▶ Play");
            } else if (idiomaActual == 2) {
                pausa.setText("▶ Jouer");
            }
        }
    }

    @FXML
    public void initialize() {
        iniciarMusica();
        reproducirCancion(cancionActual);
    }


    private void iniciarMusica() {
        canciones.add(new Cancion("Thriller", "src/main/resources/Thriller - Michael Jackson.mp3"));
        canciones.add(new Cancion("Billie Jean", "src/main/resources/Billie Jean - Michael Jackson.mp3"));
        canciones.add(new Cancion("Bad", "src/main/resources/Bad - Michael Jackson.mp3"));
        canciones.add(new Cancion("Smooth Criminal", "src/main/resources/Smooth Criminal - Michael Jackson.mp3"));

        reproductor.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(
                javafx.collections.FXCollections.observableArrayList(canciones)
        ));
        reproductor.getValueFactory().setValue(canciones.get(cancionActual));
    }

    private void reproducirCancion(int index) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        Cancion cancion = canciones.get(index);
        Media media = new Media(new File(cancion.getRuta()).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnEndOfMedia(this::siguienteCancion);
        mediaPlayer.setMute(isMuted);
        mediaPlayer.play();
    }

    private void siguienteCancion() {
        if ((cancionActual + 1) == canciones.size()) {
            cancionActual = 0;
        } else {
            cancionActual = cancionActual + 1;
        }
        actualizarCancion();
    }

    private void anteriorCancion() {
        if (cancionActual == 0) {
            cancionActual = canciones.size() - 1;
        } else {
            cancionActual = cancionActual - 1;
        }
        actualizarCancion();
    }

    private void actualizarCancion() {
        Cancion cancion = canciones.get(cancionActual);
        reproductor.getValueFactory().setValue(cancion);
        reproducirCancion(cancionActual);
    }

    private void actualizarTextos() {
        //Menú

        if (idiomaActual == 0) {
            idioma.setText("Idioma");
            comoJugar.setText("Cómo Jugar");
            if (isPausado) {
                pausa.setText("▶ Jugar");
            } else {
                pausa.setText("⏸ Pausar");
            }
        } else if (idiomaActual == 1) {
            idioma.setText("Language");
            comoJugar.setText("How to Play");
            if (isPausado) {
                pausa.setText("▶ Play");
            } else {
                pausa.setText("⏸ Pause");
            }
        } else if (idiomaActual == 2) {
            idioma.setText("Langue");
            comoJugar.setText("Comment Jouer");
            if (isPausado) {
                pausa.setText("▶ Jouer");
            } else {
                pausa.setText("⏸ Pause");
            }
        }

    }
}
