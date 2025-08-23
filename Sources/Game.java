import javafx.application.Platform;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Game {
    private StackPane pane;
    private Player player;
    private DungeonLevel level1;
    private DungeonLevel level2;
    private int currentLevel;
    private Rectangle healthBar;
    private Label potionCountLabel;

    public Game() {
        pane = new StackPane();
        currentLevel = 1;
        player = new Player(0, 275, pane);
        level1 = new DungeonLevel(1, player, this, pane);
        level2 = new DungeonLevel(2, player, this, pane);

        healthBar = new Rectangle(80, 20, Color.GREEN);
        healthBar.setTranslateX(-360);
        healthBar.setTranslateY(-275);

        potionCountLabel = new Label("Potions: 0");
        potionCountLabel.setTranslateX(-350);
        potionCountLabel.setTranslateY(-250);
        pane.getChildren().addAll(player.getSprite(), healthBar, potionCountLabel);
        pane.getChildren().addAll(level1.getEntityNodes());
    }

    public StackPane getPane() {
        return pane;
    }

    public void start() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (currentLevel == 1) {
                    level1.update();
                    if (level1.isCleared()) {
                        switchToLevel2();
                    }
                } else if (currentLevel == 2) {
                    level2.update();
                }
                updateUI();
            }
        };
        timer.start();
    }

    private void switchToLevel2() {
        currentLevel = 2;
        ObservableList<Node> children = pane.getChildren();
        children.removeIf(node -> node.getId() == null || !node.getId().equals("backgroundImage"));
        pane.getChildren().add(player.getSprite());
        pane.getChildren().addAll(level2.getEntityNodes());
        pane.getChildren().add(healthBar);
        pane.getChildren().add(potionCountLabel);

        player.setX(0);
        player.setY(200);
        player.getSprite().setImage(new Image(getClass().getResource("/KnightBasic/idle/UP/Knight_Idle.png").toExternalForm()));
    }

    private void updateUI() {
        healthBar.setWidth(player.getHealth());
        potionCountLabel.setText("Potions: " + player.getPotionCount());
    }

    public void handleKeyPress(KeyCode code) {
        List<Entity> obstacles = getCurrentLevel().getEntities().stream()
                .filter(entity -> entity instanceof Monster || entity instanceof NPC || entity instanceof King)
                .collect(Collectors.toList());

        switch (code) {
            case SPACE:
                attack();
                break;
            case E:
                showInventory();
                break;
            case Q:
                player.getInventory().useItem(Item1.class, player);
                break;
            case S:
                player.getInventory().useItem(Item2.class, player);
                break;
            case D:
                player.getInventory().useItem(Item5.class, player);
                break;
            default:
                player.move(code, obstacles);
                break;
        }
    }

    private void showInventory() {
        Stage inventoryStage = new Stage();
        inventoryStage.initModality(Modality.WINDOW_MODAL);
        inventoryStage.initOwner(pane.getScene().getWindow());
        inventoryStage.initStyle(StageStyle.TRANSPARENT);

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-padding: 20px; -fx-background-color: rgba(0, 0, 0, 0.8); -fx-background-radius: 10;");
        vbox.setSpacing(10);

        Label inventoryLabel = new Label("Inventaire:");
        inventoryLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        VBox itemsBox = new VBox();
        itemsBox.setSpacing(5);

        Map<String, Potion> potions = player.getInventory().getPotions();
        for (Potion potion : potions.values()) {
            HBox itemBox = new HBox();
            itemBox.setSpacing(10);
            itemBox.setAlignment(Pos.CENTER);

            Label itemLabel = new Label(potion.getType() + " x" + potion.getQuantity());
            itemLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

            Button useButton = new Button("Utiliser");
            useButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
            useButton.setOnAction(e -> {
                potion.applyTo(player);
                if (potion.isEmpty()) {
                    player.getInventory().removePotion(potion);
                }
                inventoryStage.close();
                Platform.runLater(() -> {
                    pane.requestFocus();
                    showInventory();
                });
            });

            itemBox.getChildren().addAll(itemLabel, useButton);
            itemsBox.getChildren().add(itemBox);
        }

        Button closeButton = new Button("Fermer");
        closeButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        closeButton.setOnAction(e -> {
            inventoryStage.close();
            Platform.runLater(() -> pane.requestFocus());
        });

        vbox.getChildren().addAll(inventoryLabel, itemsBox, closeButton);

        Scene dialogScene = new Scene(vbox);
        dialogScene.setFill(null); // Rendre le fond transparent

        inventoryStage.setScene(dialogScene);
        inventoryStage.showAndWait();
    }

    private void delayDie() {
        PauseTransition pause = new PauseTransition(Duration.seconds(3.5));
        ObservableList<Node> children = pane.getChildren();
        pause.setOnFinished(event -> {
            children.removeIf(node -> node.getId() == null || !node.getId().equals("backgroundImage"));
            Label gameOverLabel = new Label("Game Over");
            gameOverLabel.setFont(new Font("Arial", 64));
            gameOverLabel.setTextFill(Color.RED);
            gameOverLabel.setTranslateX(0);
            gameOverLabel.setTranslateY(0);
            Button restartButton = new Button("Rejouer");
            restartButton.setFont(new Font("Arial", 24));
            restartButton.setTranslateX(0);
            restartButton.setTranslateY(50);
            restartButton.setOnAction(event1 -> restartGame());
            pane.getChildren().addAll(gameOverLabel, restartButton);
        });
        pause.play();
    }

    public void restartGame() {
        pane.getChildren().clear();
        player = new Player(0, 300, pane);
        level1 = new DungeonLevel(1, player, this, pane);
        level2 = new DungeonLevel(2, player, this, pane);
        healthBar = new Rectangle(80, 20, Color.GREEN);
        healthBar.setTranslateX(-360);
        healthBar.setTranslateY(-275);
        potionCountLabel = new Label("Potions: 0");
        potionCountLabel.setTranslateX(-350);
        potionCountLabel.setTranslateY(-250);
        pane.getChildren().addAll(player.getSprite(), healthBar, potionCountLabel);
        pane.getChildren().addAll(level1.getEntityNodes());
        currentLevel = 1;
        start();
    }

    private void attack() {
        for (Monster monster : getCurrentLevel().getMonsters()) {
            if (player.getSprite().getBoundsInParent().intersects(monster.getSprite().getBoundsInParent())) {
                monster.takeDamage(player.attack());
                if (!monster.isDead()) {
                    player.takeDamage(monster.attack());
                }
                if (monster.isDead()) {
                    monster.die();
                    getCurrentLevel().checkAndChangeKingImage();
                }
                if (player.isDead()) {
                    player.die();
                    delayDie();
                    break;
                }
            }
        }
        for (NPC npc : getCurrentLevel().getNPCs()) {
            if (player.getSprite().getBoundsInParent().intersects(npc.getSprite().getBoundsInParent())) {
                player.meetNPC(npc);
                if (!npc.hasBeenRobbed()) {
                    if (npc.getImageUrl().equals(getClass().getResource("/NPCs/NPC1_Left.png").toExternalForm()) || npc.getImageUrl().equals(getClass().getResource("/NPCs/NPC1_Right.png").toExternalForm())) {
                        DialogUtil.showDialog(pane, "L'aventure est semée d'embûches. Il est important que je vous donne une potion de soins qui vous permet de récupérer 20 PV en cas de besoin. Bon courage !", () -> {
                            Potion potion = EntityFactory.createPotion(0, 0, getClass().getResource("/Potions/potionHealth.png").toExternalForm(), "Health");
                            player.getInventory().addPotion(potion);
                        });
                    }
                    if (npc.getImageUrl().equals(getClass().getResource("/NPCs/NPC2_Left.png").toExternalForm()) || npc.getImageUrl().equals(getClass().getResource("/NPCs/NPC2_Right.png").toExternalForm())) {
                        DialogUtil.showDialog(pane, "Si tu es arrivé jusqu'ici, jeune aventurier, c'est que tu as passé le premier niveau de ce donjon ! \n Prend ceci, c'est une potion d'attaque qui permet d'augmenter de 80 les dommages de tes attaques ! \n Je crois en toi, sauve notre roi !", () -> {
                            Potion potion = EntityFactory.createPotion(0, 0, getClass().getResource("/Potions/potionAttack.png").toExternalForm(), "Attack");
                            player.getInventory().addPotion(potion);
                        });
                    }
                } else {
                    if (npc.getImageUrl().equals(getClass().getResource("/NPCs/NPC1_Left.png").toExternalForm()) || npc.getImageUrl().equals(getClass().getResource("/NPCs/NPC1_Right.png").toExternalForm())) {
                        DialogUtil.showDialog(pane, "Un voleur est passé par là, je n'ai donc plus rien à t'offrir !", null);
                    }
                }
            }
        }
    }

    public static class DialogUtil {
        public static void showDialog(StackPane owner, String message, Runnable onClose) {
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(owner.getScene().getWindow());
            dialogStage.initStyle(StageStyle.TRANSPARENT);

            Label messageLabel = new Label(message);
            messageLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: white; -fx-padding: 10px; -fx-background-color: rgba(0, 0, 0, 0.8); -fx-background-radius: 10;");
            messageLabel.setWrapText(true);
            messageLabel.setMaxWidth(500);

            Button closeButton = new Button("Fermer");
            closeButton.setStyle("-fx-font-size: 12px; -fx-padding: 5px 10px;");
            closeButton.setOnAction(e -> {
                dialogStage.close();
                if (onClose != null) {
                    onClose.run();
                }
            });

            VBox vbox = new VBox();
            vbox.setAlignment(Pos.CENTER);
            vbox.setStyle("-fx-padding: 10px; -fx-background-color: rgba(0, 0, 0, 0.8); -fx-background-radius: 10;");
            vbox.setSpacing(5);

            vbox.getChildren().addAll(messageLabel, closeButton);

            Scene dialogScene = new Scene(vbox);
            dialogScene.setFill(null); // Rendre le fond transparent

            dialogStage.setScene(dialogScene);

            dialogStage.show();
        }
    }

    private DungeonLevel getCurrentLevel() {
        return currentLevel == 1 ? level1 : level2;
    }
}
