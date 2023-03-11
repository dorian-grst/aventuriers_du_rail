package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.RailsIHM;
import fr.umontpellier.iut.rails.CouleurWagon;
import fr.umontpellier.iut.rails.Destination;
import fr.umontpellier.iut.rails.Jeu;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static java.awt.SystemColor.desktop;

/**
 * Cette classe présente les éléments appartenant au container situé à droite
 * <p>
 * On y définit les bindings sur le container droit, ainsi que le listener à exécuter lorsque
 */
public class VueContainerDroit extends Pane {

    private IJeu jeu;

    private VueContainerDestinationWagon vueContainerDestinationWagon;

    private HBox container_carte;

    private Stage stage;

    @FXML
    private Label label_wagon;
    @FXML
    private Label label_destination;

    @FXML
    private Label label_defausse;
    @FXML
    private Button bouton_regle;
    @FXML
    private Button bouton_passer;

    public VueContainerDroit(IJeu jeu, Stage stage, VueContainerDestinationWagon vueContainerDestinationWagon) {
        this.stage = stage;
        this.jeu = jeu;
        this.vueContainerDestinationWagon = vueContainerDestinationWagon;
        this.container_carte = ((HBox) ((VBox) vueContainerDestinationWagon.getChildren().get(0)).getChildren().get(1));
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/vueContainerDroit.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // EXECUTE L'ACTION A FAIRE LORQU'ON PASSE
    @FXML
    public void passerClique() {
        bouton_passer.setOnMouseClicked(action -> {
            jeu.passerAEteChoisi();
        });
    }

    // OUVRE UN PDF QUAND ON CLIQUE SUR LES REGLES
    @FXML
    public void regleClique() {
        String path = "/Users/doriangrasset/Documents/Études/IUT/Semestre2/SAE/IHM/railsihm/src/main/resources/règles/Règles.pdf";
        String[] params = {"/bin/zsh", "open ", path};
        Platform.runLater(() -> {
            try {
                Runtime.getRuntime().exec(params);
            } catch (IOException e) {
                System.out.println("Erreur PDF");
            }
        });
    }

    // PIOCHE UNE CARTE WAGON ET ACTUALISE LES LABELS DES NOMBRES DE CARTES
    @FXML
    public void piocheWagonClique() {
        Platform.runLater(() -> {
            this.jeu.uneCarteWagonAEtePiochee();
            label_wagon.setText("Wagons (" + this.jeu.getPileCartesWagon().size() + ")");
        });
    }

    // AFFICHE 3 CARTES DESTINATION ET ACTUALISE LES LABELS DES NOMBRES DE CARTES
    @FXML
    public void piocheDestinationClique() {
        Platform.runLater(() -> {
            this.jeu.uneDestinationAEtePiochee();
            container_carte.getChildren().clear();
            label_destination.setText("Destinations (" + this.jeu.getPileDestinations().size() + ")");
        });
    }

    // RÉCUPÉRER LE LABEL DESTINATION
    public Label getLabel_destination() {
        return label_destination;
    }

    // RÉCUPÉRER LE LABEL CARTE WAGON
    public Label getLabel_wagon() {
        return label_wagon;
    }

    // RÉCUPÉRER LE LABEL DEFAUSSE
    public Label getLabel_defausse() {
        return label_defausse;
    }
}
