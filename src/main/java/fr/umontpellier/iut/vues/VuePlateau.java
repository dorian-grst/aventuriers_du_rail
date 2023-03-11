package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Cette classe présente les routes et les villes sur le plateau.
 * <p>
 * On y définit le listener à exécuter lorsque qu'un élément du plateau a été choisi par l'utilisateur
 * ainsi que les bindings qui mettront ?à jour le plateau après la prise d'une route ou d'une ville par un joueur
 */
public class VuePlateau extends Pane {

    private IJeu jeu;

    // BOUTON POUR VOIR LES
    private Circle boutonToggle;

    private ColorAdjust lighting;

    private boolean clique = false;

    @FXML
    ImageView image;
    @FXML
    private Group villes;
    @FXML
    private Group routes;

    public VuePlateau(IJeu jeu) {
        this.jeu = jeu;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/vuePlateau.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        elementMisEnAvant();
    }

    @FXML
    public void choixRouteOuVille() {
    }

    public Group getVilles() {
        return villes;
    }

    public Group getRoutes() {
        return routes;
    }

    public void elementMisEnAvant() {
        lighting = new ColorAdjust();
        lighting.setBrightness(0.7);
        //Bouton toggle
        boutonToggle = new Circle(10);
        boutonToggle.setLayoutX(13);
        boutonToggle.setLayoutY(13);
        try {
            boutonToggle.setFill(new ImagePattern(new Image(new FileInputStream("ressources/images/images/toggle-button.png"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        boutonToggle.setOnMouseClicked(e -> {
            if (!clique) {
                image.setEffect(lighting);
                clique = true;
            } else {
                clique = false;
                image.setEffect(null);
            }
        });
        this.getChildren().add(boutonToggle);
    }

    // BINDINGS
    public void creerBindings() {
        bindRedimensionPlateau();
    }

    private void bindRedimensionPlateau() {
        bindRoutes();
        bindVilles();
        // LES DIMENSIONS DE L'IMAGE VARIENT AVEC CELLE DE LA SCÈNE
        image.fitWidthProperty().bind(getScene().widthProperty().divide(1.5));
        image.fitHeightProperty().bind(getScene().heightProperty().divide(1.5));
    }

    private void bindRectangle(Rectangle rect, double layoutX, double layoutY) {
        rect.layoutXProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }

            @Override
            protected double computeValue() {
                return layoutX * image.getLayoutBounds().getWidth() / DonneesPlateau.largeurInitialePlateau;
            }
        });
        rect.layoutYProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }

            @Override
            protected double computeValue() {
                return layoutY * image.getLayoutBounds().getHeight() / DonneesPlateau.hauteurInitialePlateau;
            }
        });
        rect.widthProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }

            @Override
            protected double computeValue() {
                return DonneesPlateau.largeurRectangle * image.getLayoutBounds().getWidth() / DonneesPlateau.largeurInitialePlateau;
            }
        });
        rect.heightProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }

            @Override
            protected double computeValue() {
                return DonneesPlateau.hauteurRectangle * image.getLayoutBounds().getHeight() / DonneesPlateau.hauteurInitialePlateau;
            }
        });
        rect.xProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }

            @Override
            protected double computeValue() {
                return DonneesPlateau.xInitial * image.getLayoutBounds().getWidth() / DonneesPlateau.largeurInitialePlateau;
            }
        });
        rect.yProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }

            @Override
            protected double computeValue() {
                return DonneesPlateau.yInitial * image.getLayoutBounds().getHeight() / DonneesPlateau.hauteurInitialePlateau;
            }
        });
    }

    private void bindRoutes() {
        for (Node nRoute : routes.getChildren()) {
            Group gRoute = (Group) nRoute;
            int numRect = 0;
            for (Node nRect : gRoute.getChildren()) {
                Rectangle rect = (Rectangle) nRect;
                bindRectangle(rect, DonneesPlateau.getRoute(nRoute.getId()).get(numRect).getLayoutX(), DonneesPlateau.getRoute(nRoute.getId()).get(numRect).getLayoutY());
                numRect++;
            }
        }
    }

    private void bindVilles() {
        for (Node nVille : villes.getChildren()) {
            Circle ville = (Circle) nVille;
            bindVille(ville, DonneesPlateau.getVille(ville.getId()).getLayoutX(), DonneesPlateau.getVille(ville.getId()).getLayoutY());
        }
    }

    private void bindVille(Circle ville, double layoutX, double layoutY) {
        ville.layoutXProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }

            @Override
            protected double computeValue() {
                return layoutX * image.getLayoutBounds().getWidth() / DonneesPlateau.largeurInitialePlateau;
            }
        });
        ville.layoutYProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }

            @Override
            protected double computeValue() {
                return layoutY * image.getLayoutBounds().getHeight() / DonneesPlateau.hauteurInitialePlateau;
            }
        });
        ville.radiusProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }

            @Override
            protected double computeValue() {
                return DonneesPlateau.rayonInitial * image.getLayoutBounds().getWidth() / DonneesPlateau.largeurInitialePlateau;
            }
        });
    }
}

