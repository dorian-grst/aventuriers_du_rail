package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.rails.Joueur;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Cette classe présente les éléments des joueurs autres que le joueur courant,
 * en cachant ceux que le joueur courant n'a pas à connaitre.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueAutresJoueurs extends Pane {

    private IJeu jeu;

    // VBOX QUI CONTIENT LES PANES DES AUTRES JOUEURS
    @FXML private VBox containerPaneJoueur;

    // HBOX DE CHAQUE JOUEURS (sauf j1 car c'est le joueur courant et j2 car on ne le supprime jamais)
    @FXML private HBox supprimer_j3;
    @FXML private HBox supprimer_j4;
    @FXML private HBox supprimer_j5;

    // IMAGEVIEW DE CHAQUE JOUEURS (sauf j1 car c'est le joueur courant)
    @FXML private ImageView avatar_j2;
    @FXML private ImageView avatar_j3;
    @FXML private ImageView avatar_j4;
    @FXML private ImageView avatar_j5;

    // LABEL DE CHAQUE JOUEURS (sauf j1 car c'est le joueur courant)
    @FXML private Label nom_j2;
    @FXML private Label nom_j3;
    @FXML private Label nom_j4;
    @FXML private Label nom_j5;

    public VueAutresJoueurs(IJeu jeu) {
        this.jeu = jeu;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/vueAutresJoueurs.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        adapterNombreDePane();
        informationsAutresJoueurs();
    }

    //ADAPTER LE NOMBRE DE HBOX EN FONCTION DU NOMBRE DE JOUEURS
    public void adapterNombreDePane(){
        switch (this.jeu.getJoueurs().size()){
            case 2 -> {
                containerPaneJoueur.getChildren().remove(supprimer_j3);
                containerPaneJoueur.getChildren().remove(supprimer_j4);
                containerPaneJoueur.getChildren().remove(supprimer_j5);
            }
            case 3 -> {
                containerPaneJoueur.getChildren().remove(supprimer_j4);
                containerPaneJoueur.getChildren().remove(supprimer_j5);
            }
            case 4 -> {
                containerPaneJoueur.getChildren().remove(supprimer_j5);
            }
        }
    }

    //ROULEMENT DES PANES DES JOUEURS
    public void informationsAutresJoueurs(){
        //CREATION D'UNE LISTE CONTENANT TOUS LES JOUEURS
        ArrayList<Joueur> joueurs = new ArrayList<>();
        //AJOUT DE TOUS LES JOUEURS
        joueurs.addAll(this.jeu.getJoueurs());
        //LISTENER LORSQUE LE JOUEURCOURANTCHANGE
        this.jeu.joueurCourantProperty().addListener(e -> Platform.runLater(() -> {
            //CREATION D'UNE LISTE DE LABEL (DE VUEAUTRESJOUEURS)
            ArrayList<Label> listeLabel = new ArrayList<>();
            listeLabel.add(nom_j2);
            listeLabel.add(nom_j3);
            listeLabel.add(nom_j4);
            listeLabel.add(nom_j5);
            //CREATION D'UNE LISTE D'IMAGEVIEW
            ArrayList<ImageView> listeAvatar = new ArrayList<>();
            listeAvatar.add(avatar_j2);
            listeAvatar.add(avatar_j3);
            listeAvatar.add(avatar_j4);
            listeAvatar.add(avatar_j5);
            //AFFECTATION DES LABELS AU NOM DES JOUEURS QUI NE SONT PAS DES JOUEURS COURANT (TOUS SAUF LE PREMIER DE LA LISTE)
            for (int i = 1; i<joueurs.size(); i++){
                listeLabel.get(i-1).setText(joueurs.get(i).getNom());
                listeAvatar.get(i-1).setImage(new Image(joueurs.get(i).getAvatar()));
            }
            //STOCKAGE DU PREMIER JOUEUR
            Joueur j = joueurs.remove(0);
            joueurs.add(j);
        }));
    }
}
