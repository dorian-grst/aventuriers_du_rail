package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.ICouleurWagon;
import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.rails.CouleurWagon;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Cette classe représente la vue d'une carte Wagon.
 *
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueCarteWagon extends VBox {

    private IJeu jeu;

    // RELIER vueContainerDroit ET vueCarteWagon
    private VueContainerDroit vueContainerDroit;

    // RELIER vueJoueurCourant ET vueCarteWagon
    private VueJoueurCourant vueJoueurCourant;

    // COULEUR DE LA CARTE CRÉÉE
    private CouleurWagon couleurWagon;

    // CRÉATION D'UN EVENT
    private EventHandler<MouseEvent> cartecliquee;

    public VueCarteWagon(IJeu jeu, ICouleurWagon couleurWagon, VueContainerDroit vueContainerDroit, VueJoueurCourant vueJoueurCourant) {
        this.vueJoueurCourant = vueJoueurCourant;
        this.couleurWagon = couleurWagon.getCouleurWagon();
        this.jeu = jeu;
        this.vueContainerDroit = vueContainerDroit;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/vueCarteWagon.fxml"));
            // RÉCUPÉRER L'IMAGE CARTE WAGON EN FONCTION DE LA COULEUR
            String lien = "images/cartesWagons/CartesWagonsJoueurs/carte-wagon-"+this.couleurWagon+".png";
            // CRÉATION D'UNE NOUVELLE IMAGE
            ImageView image = new ImageView(new Image(lien));
            // INITIALISATION DE L'EVENEMENT
            cartecliquee = (event) -> {
                this.jeu.uneCarteWagonAEteChoisie(this.getCouleurWagon());
                this.vueContainerDroit.getLabel_wagon().setText("Wagons ("+this.jeu.getPileCartesWagon().size()+")");
            };
            // AJOUT DE L'ÉVÈNEMENT SUR L'IMAGE
            image.setOnMouseClicked(cartecliquee);
            // PARAMÈTRES DE L'IMAGE
            image.setFitHeight(82);
            image.setFitWidth(120);
            this.setAlignment(Pos.CENTER);
            // AJOUT DE L'IMAGE DANS THIS
            this.getChildren().add(image);
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ICouleurWagon getCouleurWagon() {
        return couleurWagon;
    }
}
