package fr.umontpellier.iut.rails;

import fr.umontpellier.iut.IJoueur;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class ServiceDuJeu extends Service<Void> {
    private Jeu jeu;

    public ServiceDuJeu(String[] nomJoueurs, String[] avatarJoueurs, IJoueur.Couleur[] couleursJoueurs) {
        jeu = new Jeu(nomJoueurs, avatarJoueurs, couleursJoueurs);
    }

    @Override
    protected Task<Void> createTask() {
        return getJeu();
    }

    public Jeu getJeu() {
        return jeu;
    }

}