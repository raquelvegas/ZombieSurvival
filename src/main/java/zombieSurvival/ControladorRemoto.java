package zombieSurvival;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class ControladorRemoto {
    @FXML
    private Text
            hRefugio,
            t1, t2, t3, t4,
            hR1, hR2, hR3, hR4,
            zR1, zR2, zR3, zR4,
            muertes1, muertes2, muertes3;

    private boolean start = false;

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public void sethRefugio(String m) {
        hRefugio.setText(m);
    }
    public void setT1(String m) {
        t1.setText(m);
    }
    public void setT2(String m) {
        t2.setText(m);
    }
    public void setT3(String m) {
        t3.setText(m);
    }
    public void setT4(String m) {
        t4.setText(m);
    }

    public void sethR1(String m) {
        hR1.setText(m);
    }
    public void sethR2(String m) {
        hR2.setText(m);
    }
    public void sethR3(String m) {
        hR3.setText(m);
    }
    public void sethR4(String m) {
        hR4.setText(m);
    }

    public void setzR1(String m) {
        zR1.setText(m);
    }
    public void setzR2(String m) {
        zR2.setText(m);
    }
    public void setzR3(String m) {
        zR3.setText(m);
    }
    public void setzR4(String m) {
        zR4.setText(m);
    }

    public void setMuertes1(String m) {
        muertes1.setText(m);
    }
    public void setMuertes2(String m) {
        muertes2.setText(m);
    }
    public void setMuertes3(String m) {
        muertes3.setText(m);
    }
}
