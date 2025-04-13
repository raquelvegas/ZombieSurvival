package zombieSurvival;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import zombieSurvival.configuracionesAdicionales.Cancion;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ControladorNuevaInterfaz {
    @FXML
    private Text
            refugioTitle, tunelTitle, riesgoTitle, descansoTitle,
            colaComedorTitle, comedorTitle, comidaTitle, zonaComunTitle,

            descansoText, colaComedorText, comedorText, comidaCount, zonaComunText,

            tunel0IzqText, tunel0MidText, tunel0DchText,
            tunel1IzqText, tunel1MidText, tunel1DchText,
            tunel2IzqText, tunel2MidText, tunel2DchText,
            tunel3IzqText, tunel3MidText, tunel3DchText,

            riesgo0IzqText, riesgo0DchText,
            riesgo1IzqText, riesgo1DchText,
            riesgo2IzqText, riesgo2DchText,
            riesgo3IzqText, riesgo3DchText;

    @FXML
    private Button mute, pausa, comoJugar;

    @FXML
    private ToggleGroup grupoIdioma;

    @FXML
    private RadioMenuItem espanol, ingles, frances, croata;
    @FXML
    private MenuButton idioma;

    @FXML
    private Spinner<Cancion> reproductor;

    private Random random = new Random();
    private MediaPlayer mediaPlayer;
    private boolean isMuted = false;
    private boolean isPausado = false;
    private final List<Cancion> canciones = new ArrayList<>();
    private int cancionActual = 0;
    private int idiomaActual = 0;
    private Juego juego = null;

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
        } else if (seleccionado == croata) {
            //Cambiar a Croata
            idiomaActual = 3;
            actualizarTextos();
        }
    }

    @FXML
    void pausar(ActionEvent event) {
        // Solo cambiar el estado de pausa y el texto del botón, sin afectar la música
        if (juego.estaEnPausa()) {
            juego.reanudar();
            isPausado = false;
            mediaPlayer.play();
            if (idiomaActual == 0) {
                pausa.setText("⏸ Pausar");
            } else if (idiomaActual == 1) {
                pausa.setText("⏸ Pause");
            } else if (idiomaActual == 2) {
                pausa.setText("⏸ Pause");
            } else if (idiomaActual == 3) {
                pausa.setText("⏸ Pauza");
            }
        } else {
            juego.pausar();
            isPausado = true;
            mediaPlayer.pause();
            if (idiomaActual == 0) {
                pausa.setText("▶ Jugar");
            } else if (idiomaActual == 1) {
                pausa.setText("▶ Play");
            } else if (idiomaActual == 2) {
                pausa.setText("▶ Jouer");
            } else if (idiomaActual == 3) {
                pausa.setText("▶ Igraj");
            }
        }
    }

    @FXML
    public void initialize() {
        iniciarMusica();

        // Inicialización de las zonas
        ListaHilos zonaComun = new ListaHilos(zonaComunText);
        ListaHilos zonaDescanso = new ListaHilos(descansoText);
        ListaHilos comedor = new ListaHilos(comedorText);

        ListaHilos tunel0Izq = new ListaHilos(tunel0IzqText);
        ListaHilos tunel0Mid = new ListaHilos(tunel0MidText);
        ListaHilos tunel0Dch = new ListaHilos(tunel0DchText);
        ListaHilos tunel1Izq = new ListaHilos(tunel1IzqText);
        ListaHilos tunel1Mid = new ListaHilos(tunel1MidText);
        ListaHilos tunel1Dch = new ListaHilos(tunel1DchText);
        ListaHilos tunel2Izq = new ListaHilos(tunel2IzqText);
        ListaHilos tunel2Mid = new ListaHilos(tunel2MidText);
        ListaHilos tunel2Dch = new ListaHilos(tunel2DchText);
        ListaHilos tunel3Izq = new ListaHilos(tunel3IzqText);
        ListaHilos tunel3Mid = new ListaHilos(tunel3MidText);
        ListaHilos tunel3Dch = new ListaHilos(tunel3DchText);

        ArrayList<ListaHilos> esperaTuneles = new ArrayList<>();
        esperaTuneles.add(tunel0Izq);
        esperaTuneles.add(tunel1Izq);
        esperaTuneles.add(tunel2Izq);
        esperaTuneles.add(tunel3Izq);


        ListaHilos riesgo0Izq = new ListaHilos(riesgo0IzqText);
        ListaHilos riesgo1Izq = new ListaHilos(riesgo1IzqText);
        ListaHilos riesgo2Izq = new ListaHilos(riesgo2IzqText);
        ListaHilos riesgo3Izq = new ListaHilos(riesgo3IzqText);

        ZonaRiesgoH riesgoIzq0 = new ZonaRiesgoH(riesgo0Izq);
        ZonaRiesgoH riesgoIzq1 = new ZonaRiesgoH(riesgo1Izq);
        ZonaRiesgoH riesgoIzq2 = new ZonaRiesgoH(riesgo2Izq);
        ZonaRiesgoH riesgoIzq3 = new ZonaRiesgoH(riesgo3Izq);


        ArrayList<ZonaRiesgoH> riesgoIzq = new ArrayList<>();
        riesgoIzq.addAll(Arrays.asList(riesgoIzq0, riesgoIzq1, riesgoIzq2, riesgoIzq3));




        ListaHilos riesgo0Dch = new ListaHilos(riesgo0DchText);
        ListaHilos riesgo1Dch = new ListaHilos(riesgo1DchText);
        ListaHilos riesgo2Dch = new ListaHilos(riesgo2DchText);
        ListaHilos riesgo3Dch = new ListaHilos(riesgo3DchText);

        ArrayList<ListaHilos> riesgoDch = new ArrayList<>();
        riesgoDch.addAll(Arrays.asList(riesgo0Dch, riesgo1Dch, riesgo2Dch, riesgo3Dch));

        juego = new Juego(zonaComun, zonaDescanso, comedor, comidaCount, esperaTuneles, riesgoIzq, riesgoDch);
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

    public void reproducirCancion(int index) {
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

            refugioTitle.setText("REFUGIO");
            tunelTitle.setText("TÚNELES");
            riesgoTitle.setText("ZONA DE RIESGO");
            descansoTitle.setText("DESCANSO");
            comedorTitle.setText("COMEDOR");
            colaComedorTitle.setText(" COLA DE ESPERA:   ");
            comidaTitle.setText("COMIDA");
            zonaComunTitle.setText("ZONA COMÚN");

        } else if (idiomaActual == 1) {
            idioma.setText("Language");
            comoJugar.setText("How to Play");
            if (isPausado) {
                pausa.setText("▶ Play");
            } else {
                pausa.setText("⏸ Pause");
            }

            refugioTitle.setText("SHELTER");
            tunelTitle.setText("TUNNELS");
            riesgoTitle.setText("DANGER ZONE");
            descansoTitle.setText("REST AREA");
            comedorTitle.setText("DINING ROOM");
            colaComedorTitle.setText(" WAITING QUEUE:   ");
            comidaTitle.setText("FOOD");
            zonaComunTitle.setText("COMMON AREA");

        } else if (idiomaActual == 2) {
            idioma.setText("Langue");
            comoJugar.setText("Comment Jouer");
            if (isPausado) {
                pausa.setText("▶ Jouer");
            } else {
                pausa.setText("⏸ Pause");
            }

            refugioTitle.setText("REFUGE");
            tunelTitle.setText("TUNNELS");
            riesgoTitle.setText("ZONE DE DANGER");
            descansoTitle.setText("ZONE DE REPOS");
            comedorTitle.setText("SALLE À MANGER");
            colaComedorTitle.setText(" FILE D'ATTENTE:   ");
            comidaTitle.setText("NOURRITURE");
            zonaComunTitle.setText("ESPACE COMMUN");

        } else if (idiomaActual == 3) {
            idioma.setText("Jezik");
            comoJugar.setText("Kako Igrati");
            if (isPausado) {
                pausa.setText("▶ Igraj");
            } else {
                pausa.setText("⏸ Pauza");
            }

            refugioTitle.setText("SKLONIŠTE");
            tunelTitle.setText("TUNELI");
            riesgoTitle.setText("OPASNOST");
            descansoTitle.setText("ZONE ZA ODMOR");
            comedorTitle.setText("BLAGOVAONICA");
            colaComedorTitle.setText(" COLA DE ESPERA:   ");
            comidaTitle.setText("HRANA");
            zonaComunTitle.setText("ZAJEDNIČKI PROSTOR");
        }

    }

    public Juego getJuego(){
        return juego;
    }

    public int getCancionActual(){
        return cancionActual;
    }
}
