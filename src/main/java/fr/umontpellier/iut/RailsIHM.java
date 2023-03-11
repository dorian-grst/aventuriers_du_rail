package fr.umontpellier.iut;

import fr.umontpellier.iut.rails.ServiceDuJeu;
import fr.umontpellier.iut.vues.VueChoixJoueurs;
import fr.umontpellier.iut.vues.VueDuJeu;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RailsIHM extends Application {

    private VueDuJeu vueDuJeu;
    private VueChoixJoueurs vueChoixJoueurs;
    private Stage primaryStage;
    private ServiceDuJeu serviceDuJeu;

    private Stage stage;

    private boolean avecVueChoixJoueurs = true;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        if (avecVueChoixJoueurs) {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/vueChoixJoueurs.fxml"));
            stage = new Stage();
            vueChoixJoueurs = new VueChoixJoueurs(this, this.stage);
            vueChoixJoueurs.setNomsDesJoueursDefinisListener(quandLesNomsJoueursSontDefinis);
            stage.setScene(new Scene(vueChoixJoueurs));
            stage.show();
        } else {
            demarrerPartie();
        }
    }

    //AJOUT AVATAR JOUEURS getAvatarJoueurs
    public void demarrerPartie() {
        List<String> nomsJoueurs;
        List<String> avatarJoueurs;
        List<IJoueur.Couleur> couleursJoueurs;
        if (avecVueChoixJoueurs) {
            nomsJoueurs = vueChoixJoueurs.getNomsJoueurs();
            avatarJoueurs = vueChoixJoueurs.getAvatarsJoueurs();
            couleursJoueurs = vueChoixJoueurs.getCouleursJoueurs();
            System.out.println(nomsJoueurs);
        }
        else {
            avatarJoueurs = new ArrayList<>();
            nomsJoueurs = new ArrayList<>();
            couleursJoueurs = new ArrayList<>();
            nomsJoueurs.add("Guybrush");
            nomsJoueurs.add("Largo");
            nomsJoueurs.add("LeChuck");
            nomsJoueurs.add("Elaine");
        }

        serviceDuJeu = new ServiceDuJeu(nomsJoueurs.toArray(new String[0]), avatarJoueurs.toArray(new String[0]), couleursJoueurs.toArray(new IJoueur.Couleur[0]));
        vueDuJeu = new VueDuJeu(serviceDuJeu.getJeu(), this.primaryStage);
        Scene scene = new Scene(vueDuJeu); // la scene doit être créée avant de mettre en place les bindings
        vueDuJeu.creerBindings();
        demarrerServiceJeu(); // le service doit être démarré après que les bindings ont été mis en place

        primaryStage.setScene(scene);
        primaryStage.setTitle("Rails");
        primaryStage.centerOnScreen();
        primaryStage.setOnCloseRequest(event -> {
            this.onStopGame();
            event.consume();
        });
        primaryStage.show();
    }

    private void demarrerServiceJeu() {
        if (serviceDuJeu.getState() == Worker.State.READY) {
            serviceDuJeu.start();
        }
        primaryStage.show();
    }

    private final ListChangeListener<String> quandLesNomsJoueursSontDefinis = change -> {
        if (!vueChoixJoueurs.getNomsJoueurs().isEmpty())
            demarrerPartie();
    };

    public void onStopGame() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("On arrête de jouer ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            serviceDuJeu.getJeu().cancel();
            Platform.exit();
        }
    }

    public void close(){
        stage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }

}