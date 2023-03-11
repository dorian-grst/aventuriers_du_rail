package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJoueur;
import fr.umontpellier.iut.RailsIHM;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe correspond à une nouvelle fenêtre permettant de choisir le nombre et les noms des joueurs de la partie.
 *
 * Sa présentation graphique peut automatiquement être actualisée chaque fois que le nombre de joueurs change.
 * Lorsque l'utilisateur a fini de saisir les noms de joueurs, il demandera à démarrer la partie.
 */
public class VueChoixJoueurs extends Pane{

    private RailsIHM railIHM;

    private Stage stage;

    // LISTE CONTENANT LES NOMS DES JOUEURS
    private ObservableList<String> nomsJoueurs;

    // LISTE CONTENANT LES AVATARS DES JOUEURS
    private ArrayList<String> avatarsJoueurs;

    // LISTE CONTENANT LES COULEURS DES JOUEURS
    private ArrayList<IJoueur.Couleur> couleursJoueurs;

    // NOMBRE DE JOUEURS DE BASE
    private IntegerProperty nombreJoueurs = new SimpleIntegerProperty(2);

    //ELEMENT A REDIMENSIONNER
    @FXML private VBox vboxPrincipale;
    @FXML Label titre;
    @FXML Label nbJoueurLabel;
    @FXML Label nbJoueurLabelIncremente;
    @FXML ImageView imageBackground;
    @FXML private HBox containerPaneJoueur;

    //DIFFÉRENTS AVATAR
    @FXML ImageView avatar1;
    @FXML ImageView avatar2;
    @FXML ImageView avatar3;
    @FXML ImageView avatar4;
    @FXML ImageView avatar5;

    //DIFFÉRENTS JOUEURS
    @FXML Pane j1;
    @FXML Pane j2;
    @FXML Pane j3;
    @FXML Pane j4;
    @FXML Pane j5;

    //TEXTFIELD DE CHAQUE JOUEURS
    @FXML private TextField nom_joueur1;
    @FXML private TextField nom_joueur2;
    @FXML private TextField nom_joueur3;
    @FXML private TextField nom_joueur4;
    @FXML private TextField nom_joueur5;

    //ACTION SUR LE BOUTON (-) : SI LE NOMBRE DE JOUEUR > 2 ALORS ON BAISSE DE 1
    @FXML
    public void moinsClique(){
        if (nombreJoueurs.get()>2)
        nombreJoueurs.set(nombreJoueurs.get()-1);
    }

    //ACTION SUR LE BOUTON (+) : SI LE NOMBRE DE JOUEUR < 5 ALORS ON AUGMENTE DE 1
    @FXML
    public void plusClique(){
        if (nombreJoueurs.get()<5)
        nombreJoueurs.set(nombreJoueurs.get()+1);
    }

    //ACTION SUR LE BOUTON (JOUER) :
    @FXML
    public void jouerClique(){
        //CHARGEMENT DES ELEMENTS
        avatar1 = (ImageView) this.lookup("#avatar1");
        avatar2 = (ImageView) this.lookup("#avatar2");
        avatar3 = (ImageView) this.lookup("#avatar3");
        avatar4 = (ImageView) this.lookup("#avatar4");
        avatar5 = (ImageView) this.lookup("#avatar5");
        //EN FONCTION DU NOMBRE DE JOUEUR STOCKER LE NOM ET L'AVATAR DES JOUEURS
        switch(nombreJoueurs.get()){
            case 2 -> {
                nomsJoueurs.clear();
                avatarsJoueurs.clear();
                couleursJoueurs.clear();
                avatarsJoueurs.add(avatar1.getImage().getUrl());
                avatarsJoueurs.add(avatar2.getImage().getUrl());
                couleursJoueurs.add(IJoueur.Couleur.VERT);
                couleursJoueurs.add(IJoueur.Couleur.BLEU);
                nomsJoueurs.add(0, nom_joueur1.getText());
                nomsJoueurs.add(1, nom_joueur2.getText());
            }
            case 3 -> {
                nomsJoueurs.clear();
                avatarsJoueurs.clear();
                couleursJoueurs.clear();
                avatarsJoueurs.add(avatar1.getImage().getUrl());
                avatarsJoueurs.add(avatar2.getImage().getUrl());
                avatarsJoueurs.add(avatar3.getImage().getUrl());
                couleursJoueurs.add(IJoueur.Couleur.VERT);
                couleursJoueurs.add(IJoueur.Couleur.BLEU);
                couleursJoueurs.add(IJoueur.Couleur.JAUNE);
                nomsJoueurs.add(0, nom_joueur1.getText());
                nomsJoueurs.add(1, nom_joueur2.getText());
                nomsJoueurs.add(2, nom_joueur3.getText());
            }
            case 4 -> {
                nomsJoueurs.clear();
                avatarsJoueurs.clear();
                couleursJoueurs.clear();
                avatarsJoueurs.add(avatar1.getImage().getUrl());
                avatarsJoueurs.add(avatar2.getImage().getUrl());
                avatarsJoueurs.add(avatar3.getImage().getUrl());
                avatarsJoueurs.add(avatar4.getImage().getUrl());
                couleursJoueurs.add(IJoueur.Couleur.VERT);
                couleursJoueurs.add(IJoueur.Couleur.BLEU);
                couleursJoueurs.add(IJoueur.Couleur.JAUNE);
                couleursJoueurs.add(IJoueur.Couleur.ROUGE);
                nomsJoueurs.add(0, nom_joueur1.getText());
                nomsJoueurs.add(1, nom_joueur2.getText());
                nomsJoueurs.add(2, nom_joueur3.getText());
                nomsJoueurs.add(3, nom_joueur4.getText());
            }
            case 5 -> {
                nomsJoueurs.clear();
                avatarsJoueurs.clear();
                couleursJoueurs.clear();
                avatarsJoueurs.add(avatar1.getImage().getUrl());
                avatarsJoueurs.add(avatar2.getImage().getUrl());
                avatarsJoueurs.add(avatar3.getImage().getUrl());
                avatarsJoueurs.add(avatar4.getImage().getUrl());
                avatarsJoueurs.add(avatar5.getImage().getUrl());
                couleursJoueurs.add(IJoueur.Couleur.VERT);
                couleursJoueurs.add(IJoueur.Couleur.BLEU);
                couleursJoueurs.add(IJoueur.Couleur.JAUNE);
                couleursJoueurs.add(IJoueur.Couleur.ROUGE);
                couleursJoueurs.add(IJoueur.Couleur.ROSE);
                nomsJoueurs.add(0, nom_joueur1.getText());
                nomsJoueurs.add(1, nom_joueur2.getText());
                nomsJoueurs.add(2, nom_joueur3.getText());
                nomsJoueurs.add(3, nom_joueur4.getText());
                nomsJoueurs.add(4, nom_joueur5.getText());
            }
        }
        //DEMARRE LA PARTIE ET FERME VUECHOIXJOUEURS
        this.railIHM.demarrerPartie();
        this.railIHM.close();
    }

    public ObservableList<String> nomsJoueursProperty() {
        return nomsJoueurs;
    }

    public List<String> getNomsJoueurs() {
        return nomsJoueurs;
    }

    public VueChoixJoueurs(RailsIHM rail, Stage stage) {
        this.stage = stage;
        this.railIHM = rail;
        this.avatarsJoueurs = new ArrayList<>();
        this.couleursJoueurs = new ArrayList<>();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/vueChoixJoueurs.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // MÉTHODES
        initialiser();
        redimensionnement();
        createBindings();
        // UPDATE LE NOMBRE DE PANE EN FONCTION DU NOMBRE DE JOUEURS
        nombreJoueurs.addListener((e, f, g) -> {
            switch(nombreJoueurs.get()){
                case 2 -> {
                    if (containerPaneJoueur.getChildren().contains(j3)) containerPaneJoueur.getChildren().remove(j3);
                    if (containerPaneJoueur.getChildren().contains(j4)) containerPaneJoueur.getChildren().remove(j4);
                    if (containerPaneJoueur.getChildren().contains(j5)) containerPaneJoueur.getChildren().remove(j5);
                }
                case 3 -> {
                    if (containerPaneJoueur.getChildren().contains(j4)) containerPaneJoueur.getChildren().remove(j4);
                    if (containerPaneJoueur.getChildren().contains(j5)) containerPaneJoueur.getChildren().remove(j5);
                    if (!containerPaneJoueur.getChildren().contains(j3)) containerPaneJoueur.getChildren().add(j3);
                }
                case 4 -> {
                    if (containerPaneJoueur.getChildren().contains(j5)) containerPaneJoueur.getChildren().remove(j5);
                    if (!containerPaneJoueur.getChildren().contains(j4)) containerPaneJoueur.getChildren().add(j4);
                }
                case 5 -> {
                    if (!containerPaneJoueur.getChildren().contains(j5)) containerPaneJoueur.getChildren().add(j5);
                }
            }
        });
        nomsJoueurs = FXCollections.observableArrayList();
    }

    // INITIALISATION
    public void initialiser(){
        // RETIRER DE BASE LES JOUEURS 3, 4 ET 5 VU QUE LE INTEGERPROPERTY EST À 2

        containerPaneJoueur.getChildren().removeAll(j3, j4, j5);
    }

    // REDIMENSIONNEMENT DES ÉLÉMENTS
    public void redimensionnement(){
        stage.widthProperty().addListener((e, f, g) -> {
            this.vboxPrincipale.setPrefWidth(this.stage.getWidth());
            this.nbJoueurLabel.setPrefWidth(this.stage.getWidth());
            this.titre.setPrefWidth(this.stage.getWidth());
            this.setPrefWidth(this.stage.getWidth());
            this.imageBackground.setFitWidth(this.stage.getWidth());
            this.containerPaneJoueur.setPrefWidth(this.stage.getWidth());
        });
        stage.heightProperty().addListener(e -> {
            this.setPrefHeight(this.stage.getHeight());
            this.imageBackground.setFitHeight(this.stage.getHeight());
        });
    }

    // BINDINGS
    public void createBindings(){
        //UPDATE LE LABEL NOMBRE DE JOUEURS EN FONCTION DES BOUTONS CLIQUÉS
        nbJoueurLabelIncremente.textProperty().bind(Bindings.concat(nombreJoueurs, " Joueurs"));
    }

    //RECUPERER UN AVATAR
    public ArrayList<String> getAvatarsJoueurs() {
        return avatarsJoueurs;
    }

    //RECUPERER UNE COULEUR
    public ArrayList<IJoueur.Couleur> getCouleursJoueurs() {
        return couleursJoueurs;
    }


    /**
     * Définit l'action à exécuter lorsque la liste des participants est correctement initialisée
     */
    public void setNomsDesJoueursDefinisListener(ListChangeListener<String> quandLesNomsDesJoueursSontDefinis) {
    }

    /**
     * Définit l'action à exécuter lorsque le nombre de participants change
     */
    protected void setChangementDuNombreDeJoueursListener(ChangeListener<Integer> quandLeNombreDeJoueursChange) {
    }

    /**
     * Vérifie que tous les noms des participants sont renseignés
     * et affecte la liste définitive des participants
     */
    protected void setListeDesNomsDeJoueurs() {
        ArrayList<String> tempNamesList = new ArrayList<>();
        for (int i = 1; i <= getNombreDeJoueurs() ; i++) {
            String name = getJoueurParNumero(i);
            if (name == null || name.equals("")) {
                tempNamesList.clear();
                break;
            }
            else
                tempNamesList.add(name);
        }
        if (!tempNamesList.isEmpty()) {
            //hide();
            nomsJoueurs.clear();
            nomsJoueurs.addAll(tempNamesList);
        }
    }

    /**
     * Retourne le nombre de participants à la partie que l'utilisateur a renseigné
     */
    protected int getNombreDeJoueurs() {
        return nomsJoueurs.size();
    }

    /**
     * Retourne le nom que l'utilisateur a renseigné pour le ième participant à la partie
     * @param playerNumber : le numéro du participant
     */
    protected String getJoueurParNumero(int playerNumber) {
        return nomsJoueurs.get(playerNumber);
    }
}
