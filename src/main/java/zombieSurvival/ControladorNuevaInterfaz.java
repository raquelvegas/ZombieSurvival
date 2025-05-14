package zombieSurvival;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import zombieSurvival.configuracionesAdicionales.Cancion;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ControladorNuevaInterfaz {
    @FXML
    private Text
            refugioTitle, tunelTitle, riesgoTitle, descansoTitle,
            colaComedorTitle, comiendoTitle, comedorTitle, comidaTitle, zonaComunTitle,

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
    private Button mute, pausa, info;

    @FXML
    private ToggleGroup grupoIdioma;

    @FXML
    private RadioMenuItem espanol, ingles, frances, croata;
    @FXML
    private MenuButton idioma;

    @FXML
    private Spinner<Cancion> reproductor;

    Stage mainStage;
    Stage infoStage = new Stage();
    ControladorInfo controllerInfo = new ControladorInfo();

    private MediaPlayer mediaPlayer;
    private boolean isMuted = false, estabaPausado = false, infoWindow = false;
    private final List<Cancion> canciones = new ArrayList<>();
    private int cancionActual = 0;
    private int idiomaActual = 0;
    private Juego juego = null;

    public void setStage(Stage stage){
        mainStage = stage;
    }

    @FXML
    void abrirInfo(ActionEvent event) {
        if (infoWindow) return;

        if (idiomaActual == 0) {
            infoStage.setTitle("Información");
            controllerInfo.infoEspanol();
        } else if (idiomaActual == 1) {
            infoStage.setTitle("Information");
            controllerInfo.infoIngles();
        } else if (idiomaActual == 2) {
            infoStage.setTitle("Informations");
            controllerInfo.infoFrances();
        } else {
            infoStage.setTitle("Informacija");
            controllerInfo.infoCroata();
        }
        if (!juego.estaEnPausa()) {
            pausar(null);
            estabaPausado = false;
        } else {
            estabaPausado = true;
        }
        mainStage.getScene().getRoot().setDisable(true);
        infoWindow = true;
        infoStage.show();
    }

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
        if (juego.estaEnPausa()) {
            juego.reanudar();
            mediaPlayer.play();
        } else {
            juego.pausar();
            mediaPlayer.pause();
        }
    }

    @FXML
    public void initialize() {
        iniciarMusica();
        iniciarInfo();

        // Inicialización de las zonas
        ListaHilos zonaComun = new ListaHilos(zonaComunText);
        ListaHilos zonaDescanso = new ListaHilos(descansoText);
        ListaHilos comedor = new ListaHilos(comedorText);
        ListaHilos colaComedor = new ListaHilos(colaComedorText);

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

        ArrayList<ListaHilos> tuneles = new ArrayList<>();
        tuneles.add(tunel0Mid);
        tuneles.add(tunel1Mid);
        tuneles.add(tunel2Mid);
        tuneles.add(tunel3Mid);

        ArrayList<ListaHilos> tunelesVuelta = new ArrayList<>();
        tunelesVuelta.add(tunel0Dch);
        tunelesVuelta.add(tunel1Dch);
        tunelesVuelta.add(tunel2Dch);
        tunelesVuelta.add(tunel3Dch);


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

        juego = new Juego(zonaComun, zonaDescanso, colaComedor, comedor, comidaCount, esperaTuneles, tuneles, tunelesVuelta, riesgoIzq, riesgoDch);
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

    private void iniciarInfo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("info.fxml"));
            Parent root = loader.load();
            controllerInfo = loader.getController();
            infoStage.setScene(new Scene(root));
            infoStage.setResizable(false);
            infoStage.setAlwaysOnTop(true);

            infoWindow = false;
            infoStage.setOnCloseRequest(e -> {
                infoWindow = false;
                if (!estabaPausado) {
                    pausar(null);
                }
                mainStage.getScene().getRoot().setDisable(false);
            });


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
            info.setText("información");
            pausa.setText("▶ Jugar / ⏸ Pausar");

            refugioTitle.setText("REFUGIO");
            tunelTitle.setText("TÚNELES");
            riesgoTitle.setText("ZONA DE RIESGO");
            descansoTitle.setText("DESCANSO");
            comedorTitle.setText("COMEDOR");
            colaComedorTitle.setText(" COLA DE ESPERA:   ");
            comiendoTitle.setText(" COMIENDO...  ");
            comidaTitle.setText("COMIDA");
            zonaComunTitle.setText("ZONA COMÚN");

        } else if (idiomaActual == 1) {
            idioma.setText("Language");
            info.setText("Information");
            pausa.setText("▶ Play / ⏸ Pause");

            refugioTitle.setText("SHELTER");
            tunelTitle.setText("TUNNELS");
            riesgoTitle.setText("DANGER ZONE");
            descansoTitle.setText("REST AREA");
            comedorTitle.setText("DINING ROOM");
            colaComedorTitle.setText(" WAITING QUEUE:   ");
            comiendoTitle.setText(" EATING...  ");
            comidaTitle.setText("FOOD");
            zonaComunTitle.setText("COMMON AREA");

        } else if (idiomaActual == 2) {
            idioma.setText("Langue");
            info.setText("Informations");
            pausa.setText("▶ Jouer / ⏸ Pause");

            refugioTitle.setText("REFUGE");
            tunelTitle.setText("TUNNELS");
            riesgoTitle.setText("ZONE DE DANGER");
            descansoTitle.setText("ZONE DE REPOS");
            comedorTitle.setText("SALLE À MANGER");
            colaComedorTitle.setText(" FILE D'ATTENTE:   ");
            comiendoTitle.setText(" EN MANGEANT...  ");
            comidaTitle.setText("NOURRITURE");
            zonaComunTitle.setText("ESPACE COMMUN");

        } else if (idiomaActual == 3) {
            idioma.setText("Jezik");
            info.setText("Informacija");
            pausa.setText("▶ Igraj / ⏸ Pauza");

            refugioTitle.setText("SKLONIŠTE");
            tunelTitle.setText("TUNELI");
            riesgoTitle.setText("OPASNOST");
            descansoTitle.setText("ZONE ZA ODMOR");
            comedorTitle.setText("BLAGOVAONICA");
            colaComedorTitle.setText(" RED ČEKANJA:   ");
            comiendoTitle.setText(" JEDE...  ");
            comidaTitle.setText("HRANA");
            zonaComunTitle.setText("ZAJEDNIČKI PROSTOR");
        }

    }

    public Juego getJuego() {
        return juego;
    }

    public int getCancionActual() {
        return cancionActual;
    }
}
