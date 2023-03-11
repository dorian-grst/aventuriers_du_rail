package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.ICouleurWagon;
import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IJoueur;
import fr.umontpellier.iut.rails.CouleurWagon;
import fr.umontpellier.iut.rails.Destination;
import fr.umontpellier.iut.rails.Joueur;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe présente les éléments appartenant au joueur courant.
 * <p>
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueJoueurCourant extends AnchorPane {

    private Stage stage;
    private IJeu jeu;
    private List<CouleurWagon> cartesJoueur;

    // LIER vueContainerDroit et VueJoueurCourant
    private VueContainerDroit vueContainerDroit;

    // LIER VueContainerDestinationWagon et VueJoueurCourant
    private VueContainerDestinationWagon vueContainerDestinationWagon;
    @FXML
    private Label log;
    @FXML
    private HBox container_carte;
    @FXML
    private AnchorPane container_destination;
    @FXML
    private VBox liste_destination_jc;

    // IDENTITÉ JOUEUR COURANT
    @FXML
    private Label nom_jc;
    @FXML
    private ImageView avatar_jc;

    // IMAGEVIEW ET LABEL DU NOMBRE DE GARE ET WAGON DU JOUEUR COURANT
    @FXML
    private ImageView img_gare_jc;
    @FXML
    private ImageView img_wagon_jc;
    @FXML
    private Label nb_gare_jc;
    private IntegerProperty nb_gare = new SimpleIntegerProperty(3);
    @FXML
    private Label nb_wagon_jc;
    private IntegerProperty nb_wagon = new SimpleIntegerProperty(45);

    // LABEL DU NOMBRE DE CARTES WAGON DU JOUEUR COURANT
    @FXML
    private Button nb_rose_jc;
    private ImageView carte_rose;
    @FXML
    private Button nb_rouge_jc;
    private ImageView carte_rouge;
    @FXML
    private Button nb_orange_jc;
    private ImageView carte_orange;
    @FXML
    private Button nb_vert_jc;
    private ImageView carte_vert;
    @FXML
    private Button nb_bleu_jc;
    private ImageView carte_bleu;
    @FXML
    private Button nb_jaune_jc;
    private ImageView carte_jaune;
    @FXML
    private Button nb_noir_jc;
    private ImageView carte_noir;
    @FXML
    private Button nb_blanc_jc;
    private ImageView carte_blanc;
    @FXML
    private Button nb_loco_jc;
    private ImageView carte_loco;


    public VueJoueurCourant(IJeu jeu, Stage stage, VueContainerDroit vueContainerDroit) {
        this.stage = stage;
        this.jeu = jeu;
        this.cartesJoueur = new ArrayList<>();
        this.vueContainerDestinationWagon = new VueContainerDestinationWagon(this.jeu, this.stage);
        this.vueContainerDroit = new VueContainerDroit(this.jeu, this.stage, this.vueContainerDestinationWagon);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/vueJoueurCourant.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initialiser();
        informationsJoueursCourant();
        mettreAJourDestinationJC();
        modification();
    }

    // REDIMENSION DU ANCHOR PANE
    public void modification() {
        Platform.runLater(() -> {
            this.liste_destination_jc.setPrefHeight(350);
            this.container_destination.setPrefHeight(350);
            this.container_destination.setStyle("-fx-background-color: #B7B1C2");
        });
    }

    public void initialiser() {
        // SET DU JOUEUR COURANT
        this.jeu.setJoueurCourant(this.jeu.getJoueurs().get(0));
        // SET DU NOM DU JOUEUR COURANT
        nom_jc.setText(this.jeu.joueurCourantProperty().getValue().getNom());
    }

    // LORSQUE LES CARTES DU JOUEURS COURANT
    @FXML
    public void carteJCClique(MouseEvent e) {
        // FILTRER L'ID DU FXML -> Button[id=nb_rose_jc, styleClass=button]'1'
        String res = "";
        int z = 0;
        for (int i = 0; i<e.getSource().toString().length();i++){
            if (e.getSource().toString().charAt(i)=='='){
                z = i+1 ;
                break;
            }
        }
        while (e.getSource().toString().charAt(z)!=','){
            res+=e.getSource().toString().charAt(z);
            z++;
        }
        // ON OBTIENT -> nb_rose_jc
        // SWITCH DE RES
        CouleurWagon c = switch (res){
            case "nb_rose_jc" -> CouleurWagon.ROSE;
            case "nb_rouge_jc" -> CouleurWagon.ROUGE;
            case "nb_orange_jc" -> CouleurWagon.ORANGE;
            case "nb_vert_jc" -> CouleurWagon.VERT;
            case "nb_bleu_jc" -> CouleurWagon.BLEU;
            case "nb_jaune_jc" -> CouleurWagon.JAUNE;
            case "nb_noir_jc" -> CouleurWagon.NOIR;
            case "nb_blanc_jc" -> CouleurWagon.BLANC;
            case "nb_loco_jc" -> CouleurWagon.LOCOMOTIVE;
            default -> null;
        };
        this.jeu.uneCarteWagonAEteChoisie(c);
        this.vueContainerDroit.getLabel_defausse().setText("Défausse ("+this.jeu.getDefausseCartesWagon().size()+")");
    }

    public void informationsJoueursCourant() {
        //QUAND LE JOUEUR COURANT CHANGE
        this.jeu.joueurCourantProperty().addListener(e -> Platform.runLater(() -> {
            //AFFICHER LE JOUEUR COURANT QUAND 'PASSER' EST CLIQUÉ ------> System.out.println(this.jeu.getJoueurCourant().getNom());
            //UPDATE SON NOM
            nom_jc.setText(this.jeu.joueurCourantProperty().getValue().getNom());
            //UPDATE SON AVATAR
            avatar_jc.setImage(new Image(((Joueur) this.jeu.joueurCourantProperty().get()).getAvatar()));
            //UPDATE SA COULEUR
            img_gare_jc.setImage(new Image("images/couleur/gare/gare-" + this.jeu.joueurCourantProperty().get().getCouleur() + ".png"));
            img_wagon_jc.setImage(new Image("images/couleur/wagon/image-wagon-" + this.jeu.joueurCourantProperty().get().getCouleur() + ".png"));
            //UPDATE SES CARTES
            createBindings();
        }));
        ChangeListener<IJoueur> nbCarteChangeListener = (current, old, nouveau) -> Platform.runLater(() -> {
            createBindings();
            nouveau.cartesWagonProperty().addListener(changement);
        });
        this.jeu.joueurCourantProperty().addListener(nbCarteChangeListener);
    }

    // LISTCHANGELISTENER SUR LES COULEURS DES CARTES DU JOUEURS COURANTS
    private final ListChangeListener<ICouleurWagon> changement = a -> Platform.runLater(() -> {
        createBindings();
    });

    // BINDINGS
    public void createBindings() {
        // BINDINGS POUR ATTRIBUER LE NOMBRE DE CARTES AU LABEL nb_cartes_jc
        nb_rose_jc.setText(ICouleurWagon.compteur(this.jeu.getJoueurCourant().getCartesWagon()).get(CouleurWagon.ROSE) + "");
        nb_rouge_jc.setText(ICouleurWagon.compteur(this.jeu.getJoueurCourant().getCartesWagon()).get(CouleurWagon.ROUGE) + "");
        nb_orange_jc.setText(ICouleurWagon.compteur(this.jeu.getJoueurCourant().getCartesWagon()).get(CouleurWagon.ORANGE) + "");
        nb_vert_jc.setText(ICouleurWagon.compteur(this.jeu.getJoueurCourant().getCartesWagon()).get(CouleurWagon.VERT) + "");
        nb_bleu_jc.setText(ICouleurWagon.compteur(this.jeu.getJoueurCourant().getCartesWagon()).get(CouleurWagon.BLEU) + "");
        nb_jaune_jc.setText(ICouleurWagon.compteur(this.jeu.getJoueurCourant().getCartesWagon()).get(CouleurWagon.JAUNE) + "");
        nb_noir_jc.setText(ICouleurWagon.compteur(this.jeu.getJoueurCourant().getCartesWagon()).get(CouleurWagon.NOIR) + "");
        nb_blanc_jc.setText(ICouleurWagon.compteur(this.jeu.getJoueurCourant().getCartesWagon()).get(CouleurWagon.BLANC) + "");
        nb_loco_jc.setText(ICouleurWagon.compteur(this.jeu.getJoueurCourant().getCartesWagon()).get(CouleurWagon.LOCOMOTIVE) + "");
        nb_gare_jc.setText(this.jeu.getJoueurCourant().getNbGares()+"");
        nb_wagon_jc.setText(this.jeu.getJoueurCourant().getNbWagons()+"");
    }


    // MISE À JOUR DES DESTINATIONS DU JOUEUR COURANT
    public void mettreAJourDestinationJC() {
        this.jeu.joueurCourantProperty().addListener((a, b, c) -> Platform.runLater(() -> {
            liste_destination_jc.getChildren().clear();
            for (Destination d : this.jeu.joueurCourantProperty().get().getDestinations()) {
                liste_destination_jc.getChildren().add(new Label(d.getNom()));
            }
        }));
    }
}
