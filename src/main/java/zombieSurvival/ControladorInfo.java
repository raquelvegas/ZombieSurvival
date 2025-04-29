package zombieSurvival;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class ControladorInfo {
    @FXML
    private Text infoText,infoTitle;


    public void infoEspanol(){
        infoTitle.setText("INFORMACIÓN");
        infoText.setText(
                "Contexto:\n" +
                    "Un apocalipsis zombi ha ocurrido en 2025. Los humanos deben sobrevivir en un refugio\n" +
                    "mientras zombis merodean las zonas inseguras.\n\n" +

                    "Espacios del juego:\n" +
                    "- Refugio seguro: zona de descanso, comedor y zona común.\n" +
                    "- Zona de riesgo: áreas inseguras con zombis.\n" +
                    "- Túneles: conectan el refugio con la zona de riesgo.\n" +
                    "  (solo un humano puede atravesarlo a la vez, prioridad para volver al refugio).\n\n" +

                    "Humanos:\n" +
                    "- Creación escalonada (cada 0,5 a 2 segundos).\n" +
                    "- Comportamiento:\n" +
                    "  1. Prepararse en la zona común (1-2 s).\n" +
                    "  2. Formar grupos de 3 para salir por un túnel.\n" +
                    "  3. Recolectar comida (3-5 s) en el exterior.\n" +
                    "  4. Volver solos al refugio.\n" +
                    "  5. Descansar (2-4 s).\n" +
                    "  6. Comer en el comedor (3-5 s) o esperar si no hay comida.\n" +
                    "  7. Recuperarse si fueron heridos.\n" +
                    "  8. Repetir el ciclo.\n" +
                    "- Si son atacados:\n" +
                    "  - 2/3 probabilidad de defenderse.\n" +
                    "  - Si mueren, se convierten en zombis (con el mismo ID).\n\n" +

                    "Zombis:\n" +
                    "- El paciente cero inicia la infección.\n" +
                    "- Se mueven aleatoriamente por áreas inseguras.\n" +
                    "- Atacan humanos (0,5-1,5 s por ataque).\n" +
                    "- Si matan a un humano, aumentan su contador de muertes.\n" +
                    "- Si no encuentran humanos, esperan (2-3 s).\n\n" +

                    "Detalles importantes:\n" +
                    "- El sistema nunca se detiene.\n"
        );
    }

    public void infoIngles(){
        infoTitle.setText("INFORMATION");
        infoText.setText("lksdnfgldshgiubdsvbfuiiiiiiihkdfbgoñasdbgiobdsafogilsbadniuv" +
                "lkfnsdofnosdnoi");
    }

    public void infoFrances(){
        infoTitle.setText("INFORMATIONS");
        infoText.setText("lksdnfgldshgiubdsvbfuiiiiiiihkdfbgoñasdbgiobdsafogilsbadniuv" +
                "lkfnsdofnosdnoi");
    }

    public void infoCroata(){
        infoTitle.setText("INFORMACIJA");
        infoText.setText("lksdnfgldshgiubdsvbfuiiiiiiihkdfbgoñasdbgiobdsafogilsbadniuv" +
                "lkfnsdofnosdnoi");
    }

    @FXML
    public void initialize() {
        infoEspanol();
    }
}
