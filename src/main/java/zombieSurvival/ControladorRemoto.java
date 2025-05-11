package zombieSurvival;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;

public class ControladorRemoto {
    @FXML
    private Text
            hRefugio,
            t1, t2, t3, t4,
            hR1, hR2, hR3, hR4,
            zR1, zR2, zR3, zR4,
            muertes1, muertes2, muertes3;
    @FXML
    private MenuItem but1;

    private boolean pausado = false;
    private boolean cambio = false;

    private boolean start = false;
    @FXML
    void playPause() {
        cambio = true;
        pausado = !pausado;
    }

    public boolean isPausado() {
        return pausado;
    }

    public boolean isCambio() {
        return cambio;
    }

    public void cambio(){
        cambio = false;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public void sethRefugio(String m) {
        if (m != null) {
            hRefugio.setText(m);
        }
    }
    public void setT1(String m) {
        if (m != null) {
            t1.setText(m);
        }
    }
    public void setT2(String m) {
        if (m != null) {
        t2.setText(m);
        }
    }
    public void setT3(String m) {
        if (m != null) {
        t3.setText(m);
        }
    }
    public void setT4(String m) {
        if (m != null) {
            t4.setText(m);
        }
    }

    public void sethR1(String m) {
        if (m != null) {
            hR1.setText(m);
        }
    }
    public void sethR2(String m) {
        if (m != null) {
            hR2.setText(m);
        }
    }
    public void sethR3(String m) {
        if (m != null) {
            hR3.setText(m);
        }
    }
    public void sethR4(String m) {
        if (m != null) {
            hR4.setText(m);
        }
    }

    public void setzR1(String m) {
        if (m != null) {
            zR1.setText(m);
        }
    }
    public void setzR2(String m) {
        if (m != null) {
            zR2.setText(m);
        }
    }
    public void setzR3(String m) {
        if (m != null) {
            zR3.setText(m);
        }
    }
    public void setzR4(String m) {
        if (m != null) {
            zR4.setText(m);
        }
    }

    public void setMuertes1(String m) {
        if (m != null) {
            muertes1.setText(m);
        }
    }
    public void setMuertes2(String m) {
        if (m != null) {
            muertes2.setText(m);
        }
    }
    public void setMuertes3(String m) {
        if (m != null) {
        muertes3.setText(m);
        }
    }
}
