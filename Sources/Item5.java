import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.List;

public class Item5 extends Entity implements Usable {

    private Game game;
    private StackPane pane;
    private List<Entity> items;

    public Item5(double x, double y, String imagePath) {
        super(x, y, imagePath);
    }

    @Override
    public Bounds getCollisionBounds() {
        Bounds bounds = sprite.getBoundsInParent();
        double shrinkFactor = 0.5;
        double width = bounds.getWidth() * shrinkFactor;
        double height = bounds.getHeight() * shrinkFactor;
        double minX = bounds.getMinX() + (bounds.getWidth() - width) / 2;
        double minY = bounds.getMinY() + (bounds.getHeight() - height) / 2;
        return new BoundingBox(minX, minY, width, height);
    }

    public void showCongratulationsMessage() {
        Stage congratsStage = new Stage();
        congratsStage.initModality(Modality.APPLICATION_MODAL);
        congratsStage.initOwner(pane.getScene().getWindow());
        congratsStage.initStyle(StageStyle.TRANSPARENT);

        Label congratulationsLabel = new Label("Congratulations");
        congratulationsLabel.setFont(new Font("Arial", 64));
        congratulationsLabel.setTextFill(Color.GREEN);
        congratulationsLabel.setTranslateX(0);
        congratulationsLabel.setTranslateY(-50);

        Label messageLabel = new Label("Vous avez sauvé le roi et risqué votre vie pour lui !");
        messageLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white; -fx-padding: 20px; -fx-background-color: rgba(0, 0, 0, 0.8); -fx-background-radius: 10;");
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(400);

        Button restartButton = new Button("Rejouer");
        restartButton.setFont(new Font("Arial", 24));
        restartButton.setTranslateX(0);
        restartButton.setTranslateY(50);
        restartButton.setOnAction(e -> {
            game.restartGame();
            congratsStage.close();
        });

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-padding: 20px; -fx-background-color: rgba(0, 0, 0, 0.8); -fx-background-radius: 10;");
        vbox.setSpacing(10);
        vbox.getChildren().addAll(congratulationsLabel, messageLabel, restartButton);

        Scene scene = new Scene(vbox);
        scene.setFill(null);

        congratsStage.setScene(scene);
        congratsStage.setHeight(500);
        congratsStage.showAndWait();
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setPane(StackPane pane) {
        this.pane = pane;
    }

    @Override
    public void use(Player player) {
        showCongratulationsMessage();
    }
}
