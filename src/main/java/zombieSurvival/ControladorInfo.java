package zombieSurvival;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class ControladorInfo {
    @FXML
    private Text infoText,infoTitle;


    public void infoEspanol(){
        infoTitle.setText("INFORMACIÓN");
        infoText.setText(
                "2025: El Fin de la Humanidad\n" +
                        "Un juego de supervivencia en un mundo devorado por los zombis.\n\n" +
                        "La humanidad está al borde de la extinción. Un virus imparable ha desencadenado el apocalipsis: " +
                        "los zombis dominan el mundo exterior, mientras los últimos supervivientes luchan por mantener encendida " +
                        "la chispa de la vida desde un refugio seguro.\n\n" +

                        "Contexto del juego\n" +
                        "En este mundo postapocalíptico, los jugadores deberán organizar la resistencia humana, recolectar recursos, " +
                        "tomar decisiones estratégicas y sobrevivir a incursiones peligrosas en un entorno plagado de zombis.\n\n" +

                        "Zonas del Juego\n" +
                        "Refugio Seguro (zona segura):\n" +
                        "- Zona común: planificación de misiones y reagrupamiento.\n" +
                        "- Zona de descanso: recuperación de energía.\n" +
                        "- Comedor: donde se consume la comida recolectada.\n\n" +

                        "Zona de Riesgo (áreas infestadas de zombis).\n" +
                        "Túneles: conectan el refugio con la zona de riesgo. Solo una persona puede usarlos a la vez, " +
                        "y siempre se da prioridad a quienes regresan.\n\n" +

                        "Humanos:\n" +
                        "- Se generan cada 0,5 a 2 s.\n" +
                        "- Se preparan (1–2 s), forman grupos de 3 y salen por los túneles.\n" +
                        "- Recolectan comida (3–5 s) y vuelven al refugio si sobreviven.\n" +
                        "- Descansan (2–4 s), comen (3–5 s) si hay comida, se curan si fueron heridos.\n" +
                        "- Si son atacados: 2/3 de probabilidad de sobrevivir heridos. Si no, mueren y se convierten en zombis (mantienen su ID).\n\n" +

                        "Zombis:\n" +
                        "- El Paciente Cero comienza en la zona de riesgo.\n" +
                        "- Deambulan aleatoriamente, atacan si encuentran humanos (0,5–1,5 s).\n" +
                        "- Si no encuentran, esperan (2–3 s).\n" +
                        "- Cada humano eliminado se suma a su contador... y a las hordas.\n\n" +

                        "Objetivo:\n" +
                        "No se trata solo de sobrevivir...\n" +
                        "Se trata de resistir, de luchar, y de no rendirse jamás.\n" +
                        "¿Hasta dónde llegarás antes de caer?"
        );

    }

    public void infoIngles(){
        infoTitle.setText("INFORMATION");
        infoText.setText(
                "2025: The End of Humanity\n" +
                        "A survival game in a world overrun by zombies.\n\n" +
                        "Humanity is on the brink of extinction. An unstoppable virus has triggered the apocalypse: " +
                        "zombies dominate the outside world, while the last survivors fight to keep the spark of life alive from a safe shelter.\n\n" +

                        "Game Context\n" +
                        "In this post-apocalyptic world, players must organize the human resistance, gather resources, " +
                        "make strategic decisions, and survive dangerous expeditions in zombie-infested areas.\n\n" +

                        "Game Zones\n" +
                        "Safe Shelter:\n" +
                        "- Common Area: mission planning and regrouping.\n" +
                        "- Rest Area: recover energy.\n" +
                        "- Dining Room: where collected food is consumed.\n\n" +

                        "Danger Zone: zombie-infested areas.\n" +
                        "Tunnels: connect the shelter with the danger zone. Only one person can use them at a time, " +
                        "and priority is given to those returning.\n\n" +

                        "Humans:\n" +
                        "- Spawn every 0.5 to 2 seconds.\n" +
                        "- Prepare (1–2 s), group in threes and exit through tunnels.\n" +
                        "- Collect food (3–5 s) and return if they survive.\n" +
                        "- Rest (2–4 s), eat (3–5 s if food is available), and heal if injured.\n" +
                        "- If attacked: 2/3 chance to return injured. If not, they die and become zombies (same ID).\n\n" +

                        "Zombies:\n" +
                        "- Patient Zero starts in the danger zone.\n" +
                        "- Roam randomly, attack humans (0.5–1.5 s).\n" +
                        "- If no humans are nearby, wait (2–3 s).\n" +
                        "- Every kill increases their count—and the horde grows.\n\n" +

                        "Goal:\n" +
                        "It’s not just about surviving...\n" +
                        "It’s about resisting, fighting, and never giving up.\n" +
                        "How far will you go before you fall?"
        );
    }

    public void infoFrances(){
        infoTitle.setText("INFORMATIONS");
        infoText.setText(
                "2025 : La Fin de l'Humanité\n" +
                        "Un jeu de survie dans un monde envahi par les zombies.\n\n" +
                        "L'humanité est au bord de l'extinction. Un virus incontrôlable a déclenché l'apocalypse : " +
                        "les zombies dominent l'extérieur, tandis que les derniers survivants tentent de préserver l'étincelle de la vie depuis un refuge sécurisé.\n\n" +

                        "Contexte du jeu\n" +
                        "Dans ce monde post-apocalyptique, les joueurs doivent organiser la résistance humaine, collecter des ressources, " +
                        "prendre des décisions stratégiques et survivre à des expéditions périlleuses en territoire zombie.\n\n" +

                        "Zones de jeu\n" +
                        "Refuge sécurisé :\n" +
                        "- Zone commune : préparation et planification.\n" +
                        "- Zone de repos : récupération d'énergie.\n" +
                        "- Réfectoire : consommation des vivres collectés.\n\n" +

                        "Zone de danger : infestée de zombies.\n" +
                        "Tunnels : relient le refuge à la zone de danger. Un seul humain à la fois, priorité aux retours.\n\n" +

                        "Humains :\n" +
                        "- Apparaissent toutes les 0,5 à 2 secondes.\n" +
                        "- Se préparent (1–2 s), forment des groupes de 3 et sortent un par un.\n" +
                        "- Collectent de la nourriture (3–5 s), puis rentrent si vivants.\n" +
                        "- Se reposent (2–4 s), mangent (3–5 s si disponible), et se soignent si blessés.\n" +
                        "- En cas d'attaque : 2 chances sur 3 de revenir blessés. Sinon, ils meurent et deviennent des zombies (même ID).\n\n" +

                        "Zombies :\n" +
                        "- Le patient zéro commence dans la zone de danger.\n" +
                        "- Errent aléatoirement, attaquent (0,5–1,5 s).\n" +
                        "- Sans humains, attendent (2–3 s).\n" +
                        "- Chaque mort renforce leur nombre... et les rangs des morts-vivants.\n\n" +

                        "Objectif :\n" +
                        "Il ne suffit pas de survivre...\n" +
                        "Il faut résister, se battre, ne jamais abandonner.\n" +
                        "Jusqu'où irez-vous avant de tomber ?"
        );
    }

    public void infoCroata(){
        infoTitle.setText("INFORMACIJA");
        infoText.setText(
                "2025: Kraj čovječanstva\n" +
                        "Igra preživljavanja u svijetu prepunom zombija.\n\n" +
                        "Čovječanstvo je na rubu izumiranja. Nezaustavljiv virus izazvao je apokalipsu: " +
                        "zombiji vladaju vanjskim svijetom, dok posljednji preživjeli pokušavaju sačuvati iskru života u sigurnom skloništu.\n\n" +

                        "Kontekst igre\n" +
                        "U ovom postapokaliptičnom svijetu, igrači moraju organizirati ljudski otpor, prikupljati resurse, " +
                        "donositi strateške odluke i preživjeti opasne ekspedicije u područjima punim zombija.\n\n" +

                        "Zone igre\n" +
                        "Sigurno sklonište:\n" +
                        "- Zajednički prostor: planiranje i okupljanje.\n" +
                        "- Zona odmora: obnavljanje energije.\n" +
                        "- Blagovaonica: konzumacija prikupljene hrane.\n\n" +

                        "Opasna zona: područja puna zombija.\n" +
                        "Tuneli: povezuju sklonište s opasnom zonom. Samo jedna osoba može ih koristiti u isto vrijeme, " +
                        "i prednost imaju oni koji se vraćaju.\n\n" +

                        "Ljudi:\n" +
                        "- Stvaraju se svakih 0,5 do 2 sekunde.\n" +
                        "- Pripremaju se (1–2 s), formiraju grupe od 3 i izlaze kroz tunele.\n" +
                        "- Prikupljaju hranu (3–5 s) i vraćaju se ako prežive.\n" +
                        "- Odmaraju se (2–4 s), jedu (3–5 s ako ima hrane), liječe se ako su ozlijeđeni.\n" +
                        "- Ako ih zombiji napadnu: 2/3 šanse za obranu i povratak ranjeni. Inače umiru i postaju zombiji (isti ID).\n\n" +

                        "Zombiji:\n" +
                        "- Prvi zaraženi (Pacijent nula) počinje u opasnoj zoni.\n" +
                        "- Nasumično lutaju i napadaju ljude (0,5–1,5 s).\n" +
                        "- Ako nema ljudi u blizini, čekaju (2–3 s).\n" +
                        "- Svaka žrtva povećava broj zombija... i jača hordu.\n\n" +

                        "Cilj:\n" +
                        "Nije dovoljno samo preživjeti...\n" +
                        "Treba se boriti, oduprijeti i nikad ne odustati.\n" +
                        "Koliko ćeš daleko stići prije nego padneš?"
        );
    }

    @FXML
    public void initialize() {
        infoEspanol();
    }
}
