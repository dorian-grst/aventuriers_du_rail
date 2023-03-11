package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJeu;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Cette classe représente la vue d'une carte Destination.
 *
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueDestination extends VBox {
    private IJeu jeu;
    private Stage stage;
    private IDestination destination;

    private VueContainerDroit vueContainerDroit;

    // ÉLÉMENT DE THIS
    @FXML
    private Button carte_destination;

    // RELIER vueDestination ET vueDestinationContainer
    private VueContainerDestinationWagon vueContainerDestinationWagon;
    @FXML
    private HBox container_carte;

    //RELIER vueDestination ET vueJoueurCourant
    private VueJoueurCourant vueJoueurCourant;

    public VueDestination(IJeu jeu, Stage stage, IDestination destination, HBox container_carte) {
        this.stage = stage;
        this.container_carte = container_carte;
        this.destination = destination;
        this.jeu = jeu;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/vueDestination.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initialiser();
    }

    // INITIALISATION
    public void initialiser(){
        // SET LE TEXTE DU BUTTON EN FONCTION DE L'INTITULÉ DE LA DESTINATION
        carte_destination.setText(destination.getNom());
        // SET L'ID DE THIS EN FONCTION DE L'INTITULÉ DE LA DESTINATION
        this.setId(destination.getNom());
    }

    // RÉCUPÉRÉ LE BOUTON DE THIS
    public Button getCarte_destination() {
        return carte_destination;
    }

    // STATUS UNE DESTINATION A ETE CHOISI
    @FXML
    public void destinationClique(){
        this.jeu.uneDestinationAEteChoisie(this.destination.getNom());
    }

    public IDestination getDestination() {
        return destination;
    }

}
