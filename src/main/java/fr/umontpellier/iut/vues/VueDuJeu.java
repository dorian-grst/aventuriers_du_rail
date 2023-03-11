package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.*;
import fr.umontpellier.iut.rails.CouleurWagon;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Cette classe correspond à la fenêtre principale de l'application.
 *
 * Elle est initialisée avec une référence sur la partie en cours (Jeu).
 *
 * On y définit les bindings sur les éléments internes qui peuvent changer
 * (le joueur courant, les 5 cartes Wagons visibles, les destinations lors de l'étape d'initialisation de la partie, ...)
 * ainsi que les listeners à exécuter lorsque ces éléments changent
 */
public class VueDuJeu extends BorderPane {

    private IJeu jeu;
    private Stage stage;

    // ÉLÉMENTS COMPOSANT LE BORDERPANE
    private VuePlateau plateau; //CENTRE
    private VBox containerLeft; //GAUCHE
    private VueJoueurCourant vueJoueurCourant; //GAUCHE
    private VueAutresJoueurs vueAutresJoueurs; // GAUCHE
    private VueTitre vueTitre; // HAUT
    private VueContainerDroit vueContainerDroit; // DROITE
    private VueContainerDestinationWagon vueContainerDestinationWagon; //BAS

    @FXML
    private HBox container_carte;

    public VueDuJeu(IJeu jeu, Stage stage) {
        this.jeu = jeu;
        this.stage = stage;
        this.setBackground(new Background(new BackgroundImage(new Image("images/background/inGameBackground.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1600.0, 900.0, false, false, false, false))));
        initialise();
        redimensionnement();
        parametres();
        placerGare();
        placerRoute();
    }

    //REDIMENSIONNEMENT DES ELEMENTS EN FONCTION DE LA TAILLE DE LA FENETRE
    public void redimensionnement(){
        //REDIMENSIONNEMENT DE LA FENETRE, HBOX
        stage.widthProperty().addListener((e, f, g) -> {
            this.setBackground(new Background(new BackgroundImage(new Image("images/background/inGameBackground.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(this.stage.getWidth(), this.stage.getHeight(), false, false, false, false))));
        });
        stage.heightProperty().addListener(e -> {
            this.setBackground(new Background(new BackgroundImage(new Image("images/background/inGameBackground.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(this.stage.getWidth(), this.stage.getHeight(), false, false, false, false))));
        });
    }

    //CHARGEMENT DES ELEMENTS
    public void initialise(){
        plateau = new VuePlateau(this.jeu);
        plateau.setPrefWidth(935);
        plateau.setPrefHeight(603);
        plateau.setMaxWidth(935);
        plateau.setMaxHeight(603);
        vueAutresJoueurs = new VueAutresJoueurs(this.jeu);
        vueJoueurCourant = new VueJoueurCourant(this.jeu, this.stage, this.vueContainerDroit);
        vueTitre = new VueTitre(this.stage);
        containerLeft = new VBox();
        vueContainerDestinationWagon = new VueContainerDestinationWagon(this.jeu, this.stage);
        vueContainerDroit = new VueContainerDroit(this.jeu, this.stage, this.vueContainerDestinationWagon);
        this.jeu.destinationsInitialesProperty().addListener(afficherDestinationInitiales);
        this.jeu.cartesWagonVisiblesProperty().addListener(afficherCarteWagon);
    }

    //PLACEMENT DES ELEMENTS DANS LE BORDERPANE
    public void parametres(){
        this.setPrefWidth(1600);
        this.setPrefHeight(900);
        this.setLeft(containerLeft);
        // HBOX TRANSPARENTE QUI COMBLE OU PAS L'ESPACE MANQUANT EN FONCTION DU NOMBRE DE JOUEURS
        HBox comble = new HBox();
        comble.setBackground(new Background(new BackgroundFill(Color.web("#817b8a33"), new CornerRadii(15), Insets.EMPTY)));
        comble.setMaxWidth(270);
        comble.setPrefHeight(293-(75*(this.jeu.getJoueurs().size()-1)));
        if (this.jeu.getJoueurs().size()<5) ((VBox) vueAutresJoueurs.getChildren().get(0)).getChildren().add(comble);
        containerLeft.getChildren().addAll(vueJoueurCourant, vueAutresJoueurs);
        this.setCenter(plateau);
        this.setTop(vueTitre);
        this.setRight(vueContainerDroit);
        this.setBottom(vueContainerDestinationWagon);
    }

    // CHARGEMENT DES DESTINATIONS INITIALES
    private final ListChangeListener<IDestination> afficherDestinationInitiales = change -> Platform.runLater(() -> {
        // MISE A JOUR DU LABEL DESTINATION
        vueContainerDroit.getLabel_destination().setText("Destinations ("+this.jeu.getPileDestinations().size()+")");
        // ON PARCOURT LES CHANGEMENTS
        while(change.next()){
            // SI UN AJOUT EST DÉTECTÉ, ADD LES ÉLÉMENTS
            if (change.wasAdded()){
                for(IDestination d : change.getAddedSubList()){
                    VueDestination vueDestination = new VueDestination(this.jeu, this.stage, d, this.container_carte);
                    // CONTAINER_WAGON
                    ((HBox)((VBox) vueContainerDestinationWagon.getChildren().get(0)).getChildren().get(1)).getChildren().add(vueDestination);
                }
            }
            // SI UNE SUPPRESSION EST DETÉCTÉ, REMOVE LES ÉLÉMENTS
            else if (change.wasRemoved()){
                for(IDestination d : change.getRemoved()){
                    ((HBox)((VBox) vueContainerDestinationWagon.getChildren().get(0)).getChildren().get(1)).getChildren().remove(trouverDestination(d));
                }
            }

            // SI LA LISTE DE DESTINATIONS EST VIDE, REMETTRE LES CARTES WAGONS VISIBLES
            if (change.getList().isEmpty() && this.jeu.cartesWagonVisiblesProperty().size()==5){
                for (ICouleurWagon c : this.jeu.cartesWagonVisiblesProperty()){
                    ((HBox)((VBox) vueContainerDestinationWagon.getChildren().get(0)).getChildren().get(1)).getChildren().add(new VueCarteWagon(this.jeu, c, this.vueContainerDroit, this.vueJoueurCourant));
                }
            }
        }
    });

    // CHARGEMENET DES CARTES WAGONS
    private final ListChangeListener<ICouleurWagon> afficherCarteWagon = change -> Platform.runLater(() -> {
        // MISE A JOUR DU LABEL CARTE WAGON
        vueContainerDroit.getLabel_wagon().setText("Wagons (" + this.jeu.getPileCartesWagon().size() + ")");
        // ON PARCOURT LES CHANGEMENTS
        while(change.next()){
            // SI UN AJOUT EST DÉTECTÉ, ADD LES ÉLÉMENTS
            if (change.wasAdded()){
                for(ICouleurWagon c : change.getAddedSubList()){
                    VueCarteWagon vueCarteWagon = new VueCarteWagon(this.jeu, c, this.vueContainerDroit, this.vueJoueurCourant);
                    ((HBox)((VBox) vueContainerDestinationWagon.getChildren().get(0)).getChildren().get(1)).getChildren().add(vueCarteWagon);
                }
            }
            // SI UNE SUPPRESSION EST DETÉCTÉ, REMOVE LES ÉLÉMENTS
            else if (change.wasRemoved()){
                for(ICouleurWagon c : change.getRemoved()){
                    // SUPPRIMER DU CONTAINER LA CARTE WAGON
                    ((HBox)((VBox) vueContainerDestinationWagon.getChildren().get(0)).getChildren().get(1)).getChildren().remove(trouverCarteWagon(c));
                }
            }
            // SI LA TAILLE DE CARTE_WAGON > 5
            if (((HBox)((VBox) vueContainerDestinationWagon.getChildren().get(0)).getChildren().get(1)).getChildren().size()>5){
                ((HBox)((VBox) vueContainerDestinationWagon.getChildren().get(0)).getChildren().get(1)).getChildren().clear();
                for(ICouleurWagon c : this.jeu.cartesWagonVisiblesProperty()){
                    VueCarteWagon vueCarteWagon = new VueCarteWagon(this.jeu, c, this.vueContainerDroit, this.vueJoueurCourant);
                    ((HBox)((VBox) vueContainerDestinationWagon.getChildren().get(0)).getChildren().get(1)).getChildren().add(vueCarteWagon);
                }
            }
        }
    });

    public VueDestination trouverDestination(IDestination d){
        for (Node n : ((HBox)((VBox) vueContainerDestinationWagon.getChildren().get(0)).getChildren().get(1)).getChildren()){
            VueDestination vueDestination = (VueDestination) n;
            if (vueDestination.getDestination().getNom().equals(d.getNom())){
                return vueDestination;
            }
        }
        return null;
    }

    public VueCarteWagon trouverCarteWagon(ICouleurWagon d){
        for (Node n : ((HBox)((VBox) vueContainerDestinationWagon.getChildren().get(0)).getChildren().get(1)).getChildren()){
            VueCarteWagon vueCarteWagon = (VueCarteWagon) n;
            if (vueCarteWagon.getCouleurWagon().toString().equals(d.getCouleurWagon().toString())){
                return vueCarteWagon;
            }
        }
        return null;
    }

    // PLACER UNE GARE
    public void placerGare(){
        // PARCOURIR TOUTES LES CERCLES CORRESPONDANT AUX VILLES DU PLATEAU
        for (Node ville : plateau.getVilles().getChildren()){
            // CREATION D'UN CERCLE
            Circle cVille = (Circle) ville;
            // ONMOUSECLICKED SUR LE CERCLE ASSOCIÉ À LA VILLE CLICKÉE
            cVille.setOnMouseClicked(action -> jeu.uneVilleOuUneRouteAEteChoisie(cVille.getId()));
            // PARCOURIR TOUTES LES VILLES DU PLATEAU
            for (Object iVille : jeu.getVilles()){
                // SI LE JEU POSSÈDE LA VILLE QUI EST CLIQUÉE
                if (((IVille) iVille).getNom().equals(cVille.getId())){
                    // ATTEND QUE LE JOUEUR UTILISE SES CARTES ET QUAND IL A FINIS LE PROPRIÉTAIRE CHANGE ET ON SET LA COULEUR
                    ((IVille) iVille).proprietaireProperty().addListener(action -> {
                        cVille.setFill(Color.web(switch(this.jeu.getJoueurCourant().getCouleur()){
                            case BLEU -> "#102acc";
                            case VERT ->  "#22792b";
                            case JAUNE -> "#fdd400";
                            case ROUGE -> "#c82c25";
                            case ROSE -> "#a74ec2";
                        }));
                        Platform.runLater(()-> {
                            this.vueContainerDroit.getLabel_defausse().setText("Défausse (" + jeu.getDefausseCartesWagon().size() + ")");
                        });
                    });
                }
            }
        }
    }

    // PLACER UNE ROUTE
    public void placerRoute(){
        // PARCOURIR TOUTES LES NODES CORRESPONDANT AUX ROUTES DU PLATEAU
        for (Node route : plateau.getRoutes().getChildren()) {
            Platform.runLater(()-> {
                this.vueContainerDroit.getLabel_defausse().setText("Défausse (" + jeu.getDefausseCartesWagon().size() + ")");
            });
            // CREATION D'UN GROUPE (ROUTE)
            Group gRoute = (Group) route;
            // ONMOUSECLICKED SUR LE GROUPE ASSOCIÉ À LA ROUTE CLIQUÉE
            gRoute.setOnMouseClicked(action -> jeu.uneVilleOuUneRouteAEteChoisie(gRoute.getId()));
            // PARCOURIR TOUTES LES ROUTES DU PLATEAU
            for (Object iRoute : jeu.getRoutes()){
                // SI LE JEU POSSÈDE LA ROUTE QUI EST CLIQUÉE
                if (((IRoute) iRoute).getNom().equals(gRoute.getId())) {
                    // ATTEND QUE LE JOUEUR UTILISE SES CARTES ET QUAND IL A FINIS LE PROPRIÉTAIRE CHANGE ET ON SET LES IMAGES DE WAGON
                    ((IRoute) iRoute).proprietaireProperty().addListener(action -> {
                        for (Node nRect : gRoute.getChildren()) {
                            Rectangle rect = (Rectangle) nRect;
                            rect.setFill(new ImagePattern(new Image("images/wagons/image-wagon-" + jeu.joueurCourantProperty().get().getCouleur().toString().toUpperCase() + ".png")));
                            Platform.runLater(()-> {
                                this.vueContainerDroit.getLabel_defausse().setText("Défausse (" + jeu.getDefausseCartesWagon().size() + ")");
                            });
                        }
                    });
                }
            }
        }
    }

    public IJeu getJeu() {
        return jeu;
    }

    public void creerBindings() {
        plateau.creerBindings();
    }
}