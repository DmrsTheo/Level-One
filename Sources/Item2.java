import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Map;

public class Item2 extends Entity implements Usable {

    public Item2(double x, double y, String imagePath) {
        super(x, y, imagePath);
    }

    @Override
    public void use(Player player) {
        if (!player.hasMetNPC()) {
            showMessage("Vous n'avez rencontré aucun PNJ pour utiliser l'item2.");
            return;
        }

        NPC npc = player.getLastMetNPC();
        if (npc != null) {
            if (npc.getInventory().getItems().isEmpty()) {
                player.getInventory().removeItem(this);
                showMessage("Le PNJ ne possède aucun objet. L'item2 est supprimé de votre inventaire.");
            } else {
                stealItemFromNPC(player, npc);
                npc.setHasBeenRobbed(true);
            }
        }
    }

    private void showMessage(String message) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(null);
        dialogStage.initStyle(StageStyle.TRANSPARENT);

        Label messageLabel = new Label(message);
        messageLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-padding: 20px; -fx-background-color: rgba(0, 0, 0, 0.8); -fx-background-radius: 10;");

        Button closeButton = new Button("OK");
        closeButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        closeButton.setOnAction(e -> dialogStage.close());

        VBox vbox = new VBox(messageLabel, closeButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-padding: 20px; -fx-background-color: rgba(0, 0, 0, 0.8); -fx-background-radius: 10;");
        vbox.setSpacing(10);

        Scene dialogScene = new Scene(vbox);
        dialogStage.setScene(dialogScene);
        dialogStage.showAndWait();
    }

    private void stealItemFromNPC(Player player, NPC npc) {
        Stage inventoryStage = new Stage();
        inventoryStage.initModality(Modality.WINDOW_MODAL);
        inventoryStage.initOwner(null);
        inventoryStage.initStyle(StageStyle.TRANSPARENT);

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-padding: 20px; -fx-background-color: rgba(0, 0, 0, 0.8); -fx-background-radius: 10;");
        vbox.setSpacing(10);

        Label inventoryLabel = new Label("Choisissez un objet à voler:");
        inventoryLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        VBox itemsBox = new VBox();
        itemsBox.setSpacing(5);

        for (Entity item : npc.getInventory().getItems()) {
            HBox itemBox = new HBox();
            itemBox.setSpacing(10);
            itemBox.setAlignment(Pos.CENTER);

            Label itemLabel = new Label(item.getClass().getSimpleName());
            itemLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

            Button stealButton = new Button("Voler");
            stealButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
            stealButton.setOnAction(e -> {
                if (player.getInventory().addItem(item)) {
                    npc.getInventory().removeItem(item);
                }
                inventoryStage.close();
            });

            itemBox.getChildren().addAll(itemLabel, stealButton);
            itemsBox.getChildren().add(itemBox);
        }

        Button closeButton = new Button("Fermer");
        closeButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        closeButton.setOnAction(e -> inventoryStage.close());

        vbox.getChildren().addAll(inventoryLabel, itemsBox, closeButton);

        Scene dialogScene = new Scene(vbox);
        inventoryStage.setScene(dialogScene);
        inventoryStage.showAndWait();
    }
}
