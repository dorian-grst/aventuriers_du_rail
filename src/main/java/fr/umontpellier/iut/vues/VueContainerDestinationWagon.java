package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IJoueur;
import fr.umontpellier.iut.rails.CouleurWagon;
import fr.umontpellier.iut.rails.Destination;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Cette classe représente la vue d'une carte Destination.
 *
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueContainerDestinationWagon extends Pane {
    private IJeu jeu;

    private Stage stage;

    // VBOX QUI CONTIENT LA HBOX DE L'INSTRUCTION ET LA HBOX DES CARTES WAGONS/DESTINATIONS
    @FXML
    private VBox centerContainer;

    // LABEL D'INSTRUCTION
    @FXML
    private Label log;

    // HBOX QUI CONTIENT LES CARTES WAGONS/DESTINATIONS
    @FXML
    private HBox container_carte;

    // BOOLEAN QUI PERMET D'INITIALISER LES CARTES WAGONS OU PAS
    private boolean debutDuTour;

    public VueContainerDestinationWagon(IJeu jeu, Stage stage) {
        this.stage = stage;
        this.jeu = jeu;
        debutDuTour = true;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/vueContainerDestinationWagon.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        redimensionnement();
        createBindings();
    }

    // REDIMENSIONNEMENT DES ÉLÉMENTS
    public void redimensionnement(){
        stage.widthProperty().addListener((e, f, g) -> {
            centerContainer.setPrefWidth(this.stage.getWidth());
           this.setPrefWidth(this.stage.getWidth());
        });
    }

    // BINDINGS
    public void createBindings(){
        Platform.runLater(() -> {
            // CHANGEMENT DE L'INTITULÉ DE L'INSTRUCTION
            log.textProperty().bind(this.jeu.instructionProperty());
        });
    }

}
