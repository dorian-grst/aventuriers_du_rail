package fr.umontpellier.iut.vues;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Cette classe présente les éléments appartenant au joueur courant.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueTitre extends Pane {

    private Stage stage;

    @FXML private HBox titre;

    public VueTitre(Stage stage) {
        this.stage = stage;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/vueTitre.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initialiser();
        redimensionnement();
    }

    public HBox getTitre() {
        return titre;
    }

    // INITIALISATION
    public void initialiser(){
        // LOOKUP
        this.titre = (HBox) this.lookup("#titre");
        // TITRE
        titre = this.getTitre();
    }

    // REDIMENSIONNEMENT DES ELEMENTS EN FONCTION DE LA TAILLE DE LA FENETRE
    public void redimensionnement(){
        //REDIMENSIONNEMENT DE LA HBOX, DU TITRE
        stage.widthProperty().addListener((e, f, g) -> {
            this.titre.setPrefWidth(this.stage.getWidth());
        });
    }
}
