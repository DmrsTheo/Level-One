import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private double initialWidth = 800;
    private double initialHeight = 600;

    @Override
    public void start(Stage primaryStage) {
        Game game = new Game();
        StackPane root = new StackPane();

        primaryStage.setTitle("LevelOne");

        // Créer un conteneur pour l'image de fond
        ImageView backgroundImage = new ImageView(new Image(getClass().getResource("/BGs/Background1.png").toExternalForm()));
        backgroundImage.setId("backgroundImage");
        backgroundImage.setPreserveRatio(true);
        backgroundImage.setFitWidth(initialWidth);
        backgroundImage.setFitHeight(initialHeight);

        StackPane backgroundPane = new StackPane(backgroundImage);

        // Ajoutez le conteneur d'image de fond au root
        root.getChildren().add(backgroundPane);
        root.getChildren().add(game.getPane());

        Scene scene = new Scene(root, initialWidth, initialHeight);

        // Ajouter le gestionnaire d'événements pour les touches
        scene.setOnKeyPressed(e -> game.handleKeyPress(e.getCode()));

        primaryStage.setScene(scene);

        // Fixer la taille de la fenêtre et empêcher le redimensionnement
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UTILITY);

        primaryStage.show();

        // Afficher la boîte de dialogue au démarrage du jeu
        showWelcomeDialog(primaryStage);

        game.start();
    }

    private void showWelcomeDialog(Stage ownerStage) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(ownerStage);
        dialogStage.initStyle(StageStyle.TRANSPARENT);

        Label messageLabel = new Label("Bienvenue dans LevelOne ! " +
                "\nVous devez battre tous les monstres et sauver le roi enlevé par ces derniers ! " +
                "\nVoici quelques conseils pour que vous puissiez réussir votre mission au mieux : " +
                "\n\n- Appuyez sur les touches fléchées pour vous déplacer. " +
                "\n- Appuyez sur Espace pour attaquer. " +
                "\n- Appuyez sur la touche e pour ouvrir votre inventaire. " +
                "\n\nSi vous êtes en possession de l'un des items suivants, alors : " +
                "\n     - Appuyez sur la touche q pour utiliser l'item1 et afficher l'inventaire du PNJ" +
                "\n     - Appuyez sur la touche s pour utiliser l'item2 et voler l'objet d'un PNJ" +
                "\n     - Appuyez sur la touche d pour utiliser l'item5 et gagner à coup sûr" +
                "\n\nBonne chance !");
        messageLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-padding: 20px; -fx-background-color: rgba(0, 0, 0, 0.8); -fx-background-radius: 10;");

        Button closeButton = new Button("OK");
        closeButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        closeButton.setOnAction(e -> dialogStage.close());

        VBox vbox = new VBox(messageLabel, closeButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-padding: 20px; -fx-background-color: rgba(0, 0, 0, 0.8); -fx-background-radius: 10;");
        vbox.setSpacing(10);

        Scene dialogScene = new Scene(vbox);
        dialogScene.setFill(null); // Rendre le fond transparent

        dialogStage.setScene(dialogScene);
        dialogStage.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
