import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class DungeonLevel {
    private int levelNumber;
    private Player player;
    private King king;
    private Game game;
    private List<Entity> entities;
    private List<Monster> monsters;
    private List<NPC> npcs;
    private List<Item5> items5;
    private List<Item2> items2;
    private List<Item1> items1;
    private List<Potion> potions;
    private List<King> kings;
    private StackPane pane;
    private Potion currentPotion;
    private boolean clicked;

    public DungeonLevel(int levelNumber, Player player, Game game, StackPane pane) {
        this.levelNumber = levelNumber;
        this.player = player;
        this.game = game;
        this.pane = pane;
        this.entities = new ArrayList<>();
        this.monsters = new ArrayList<>();
        this.npcs = new ArrayList<>();
        this.items5 = new ArrayList<>();
        this.items1 = new ArrayList<>();
        this.items2 = new ArrayList<>();
        this.potions = new ArrayList<>();
        this.kings = new ArrayList<>();
        this.currentPotion = null;
        this.clicked = false;

        if (levelNumber == 1) {
            initLevel1();
        } else if (levelNumber == 2) {
            initLevel2();
        }
    }

    private void initLevel1() {
        Monster monster1 = EntityFactory.createMonster(100, -150, getClass().getResource("/Goblin/Goblin_Left_Idle.png").toExternalForm(), Monster.MONSTER1, pane, this, pane);
        Monster monster2 = EntityFactory.createMonster(-80, 0, getClass().getResource("/Arraignee/Arraignee_Idle.png").toExternalForm(), Monster.MONSTER2, pane, this, pane);

        NPC npc1 = EntityFactory.createNPC(55, 125, getClass().getResource("/NPCs/NPC1_Left.png").toExternalForm(), pane);

        Potion potion1 = EntityFactory.createPotion(0, 0, getClass().getResource("/Potions/potionHealth.png").toExternalForm(), "Health");
        Potion potion2 = EntityFactory.createPotion(0, 0, getClass().getResource("/Potions/potionAttack.png").toExternalForm(), "Attack");

        //Item1 item1 = EntityFactory.createItem1(0, 0, getClass().getResource("/Items/item1.png").toExternalForm());
        //Item2 item2 = EntityFactory.createItem2(0, 0, getClass().getResource("/Items/item2.png").toExternalForm());
        //Item5 item5 = EntityFactory.createItem5(0, 0, getClass().getResource("/Items/item5.png").toExternalForm());

        // Ajout des nouveaux PNJ avec des items dans leur inventaire
        NPC npc2 = EntityFactory.createNPC(75, 100, getClass().getResource("/NPCs/NPC2_Left.png").toExternalForm(), pane);
        NPC npc3 = EntityFactory.createNPC(120, 150, getClass().getResource("/NPCs/NPC2_Left.png").toExternalForm(), pane);

        // Ajout des items aux inventaires des nouveaux PNJ
        //npc2.getInventory().addItem(EntityFactory.createItem1(0, 0, getClass().getResource("/Items/item1.png").toExternalForm()));
        //npc2.getInventory().addItem(EntityFactory.createItem2(0, 0, getClass().getResource("/Items/item2.png").toExternalForm()));
        //npc2.getInventory().addItem(EntityFactory.createItem5(0, 0, getClass().getResource("/Items/item5.png").toExternalForm()));


        //npc3.getInventory().addItem(EntityFactory.createItem1(0, 0, getClass().getResource("/Items/item1.png").toExternalForm()));
       // npc3.getInventory().addItem(EntityFactory.createItem2(0, 0, getClass().getResource("/Items/item2.png").toExternalForm()));
        //npc3.getInventory().addItem(EntityFactory.createItem5(0, 0, getClass().getResource("/Items/item5.png").toExternalForm()));


        //item5.setGame(game);
        //item5.setPane(pane);

        //player.getInventory().addItem(item1);
        //player.getInventory().addItem(item2);
        //player.getInventory().addItem(item5);

        npc1.getInventory().addItem(potion1);
        monster1.getInventory().addItem(potion2);

        monsters.add(monster1);
        monsters.add(monster2);
        npcs.add(npc1);
        npcs.add(npc2);
        npcs.add(npc3);
        //items5.add(item5);
        //items2.add(item2);
        //items1.add(item1);

        entities.addAll(monsters);
        entities.addAll(npcs);
        entities.addAll(items5);
        entities.addAll(items2);
        entities.addAll(items1);
    }

    private void initLevel2() {
        Monster monster1 = EntityFactory.createMonster(100, 100, getClass().getResource("/Goblin/Goblin_Left_Idle.png").toExternalForm(), Monster.MONSTER2, pane, this, pane);
        Monster monster2 = EntityFactory.createMonster(150, 50, getClass().getResource("/Arraignee/Arraignee_Idle.png").toExternalForm(), Monster.MONSTER2, pane, this, pane);
        Monster monster3 = EntityFactory.createMonster(0, -50, getClass().getResource("/Boss/Boss_Idle.png").toExternalForm(), Monster.BOSS, pane, this, pane);

        NPC npc1 = EntityFactory.createNPC(-100, 0, getClass().getResource("/NPCs/NPC2_Right.png").toExternalForm(), pane);

        Potion potion = EntityFactory.createPotion(0, 0, getClass().getResource("/Potions/potionHealth.png").toExternalForm(), "Health");
        Potion superPotion = EntityFactory.createPotion(0, 0, getClass().getResource("/Potions/potion.png").toExternalForm(), "Super Health");

        king = EntityFactory.createKing(0, -150, getClass().getResource("/King/king_cage.png").toExternalForm());

        npc1.getInventory().addItem(potion);

        monsters.add(monster1);
        monsters.add(monster2);
        monsters.add(monster3);
        npcs.add(npc1);
        potions.add(superPotion);
        kings.add(king);

        entities.addAll(monsters);
        entities.addAll(npcs);
        entities.addAll(potions);
        entities.addAll(kings);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Node> getEntityNodes() {
        List<Node> nodes = new ArrayList<>();
        for (Entity entity : entities) {
            nodes.add(entity.getSprite());
        }
        return nodes;
    }

    public void update() {

        if (currentPotion != null) {
            return;
        }

        for (Monster monster : monsters) {
            monster.move();
            if (player.getSprite().getBoundsInParent().intersects(monster.getSprite().getBoundsInParent())) {
                switch (monster.getImageUrl()) {
                    case "/Goblin/Goblin_Left_Idle.png":
                        monster.setImageUrl(getClass().getResource("/Goblin/Goblin_Left_Attack.png").toExternalForm());
                        break;
                    case "/Goblin/Goblin_Right_Idle.png":
                        monster.setImageUrl(getClass().getResource("/Goblin/Goblin_Right_Attack.png").toExternalForm());
                        break;
                    case "/Arraignee/Arraignee_Idle.png":
                        monster.setImageUrl(getClass().getResource("/Arraignee/Arraignee_Attack.png").toExternalForm());
                        break;
                    case "/Boss/Boss_Idle.png":
                        monster.setImageUrl(getClass().getResource("/Boss/Boss_Attack.png").toExternalForm());
                        break;
                }
            }
        }

        List<Potion> potionsToRemove = new ArrayList<>();
        for (Potion potion : potions) {
            if (player.getSprite().getBoundsInParent().intersects(potion.getSprite().getBoundsInParent())) {
                if (!clicked) {
                    currentPotion = potion;
                } else {
                    currentPotion = null;
                }

                if (currentPotion != null) {
                    Stage UseOfPotionStage = new Stage();
                    UseOfPotionStage.initModality(Modality.WINDOW_MODAL);
                    UseOfPotionStage.initOwner(pane.getScene().getWindow());
                    UseOfPotionStage.initStyle(StageStyle.TRANSPARENT);

                    Label messageLabel = new Label("Voulez-vous utiliser ou stocker la super potion ?");
                    messageLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-padding: 20px; -fx-background-color: rgba(0, 0, 0, 0.8); -fx-background-radius: 10;");
                    messageLabel.setWrapText(true);
                    messageLabel.setMaxWidth(300);

                    Button useButton = new Button("Utiliser");
                    useButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
                    useButton.setOnAction(e -> {
                        potion.applyTo(player);
                        potionsToRemove.add(potion);
                        removePotionFromEntitiesAndUI(potion);
                        currentPotion = null;
                        clicked = true;
                        UseOfPotionStage.close();
                    });

                    Button stockButton = new Button("Stocker");
                    stockButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
                    stockButton.setOnAction(e -> {
                        player.getInventory().addItem(potion);
                        potionsToRemove.add(potion);
                        removePotionFromEntitiesAndUI(potion);
                        currentPotion = null;
                        clicked = true;
                        UseOfPotionStage.close();
                    });

                    VBox vbox = new VBox();
                    vbox.setAlignment(Pos.CENTER);
                    vbox.setStyle("-fx-padding: 20px; -fx-background-color: rgba(0, 0, 0, 0.8); -fx-background-radius: 10;");
                    vbox.setSpacing(10);
                    vbox.getChildren().addAll(messageLabel, useButton, stockButton);

                    Scene dialogScene = new Scene(vbox);
                    dialogScene.setFill(null);
                    UseOfPotionStage.setScene(dialogScene);
                    UseOfPotionStage.show();
                    break;
                }
            }
        }

        potions.removeAll(potionsToRemove);
    }

    private void removePotionFromEntitiesAndUI(Potion potion) {
        entities.remove(potion);
        pane.getChildren().removeAll(potion.getSprite());
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public List<NPC> getNPCs() {
        return npcs;
    }

    public void checkAndChangeKingImage() {
        if (monsters.stream().allMatch(Monster::isDead)) {
            king.setImageUrl(getClass().getResource("/King/king_happy.png").toExternalForm());
            showCongratulationsMessage();
        }
    }

    private void showItemDialog(Entity item) {
        Stage itemStage = new Stage();
        itemStage.initModality(Modality.WINDOW_MODAL);
        itemStage.initOwner(pane.getScene().getWindow());
        itemStage.initStyle(StageStyle.TRANSPARENT);

        Label messageLabel = new Label("Voulez-vous utiliser ou stocker cet objet ?");
        messageLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-padding: 20px; -fx-background-color: rgba(0, 0, 0, 0.8); -fx-background-radius: 10;");
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(300);

        Button useButton = new Button("Utiliser");
        useButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        useButton.setOnAction(e -> {
            if (item instanceof Item1) {
                ((Item1) item).use(player);
            } else if (item instanceof Item2) {
                ((Item2) item).use(player);
            } else if (item instanceof Item5) {
                ((Item5) item).use(player);
            }
            pane.getChildren().remove(item.getSprite());
            itemStage.close();
        });

        Button stockButton = new Button("Stocker");
        stockButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        stockButton.setOnAction(e -> {
            boolean added = player.getInventory().addItem(item);
            if (added) {
                pane.getChildren().remove(item.getSprite());
            } else {
                Label fullInventoryLabel = new Label("L'inventaire est plein.");
                fullInventoryLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: red;");
                VBox dialogBox = new VBox(messageLabel, fullInventoryLabel, useButton, stockButton);
                dialogBox.setAlignment(Pos.CENTER);
                dialogBox.setSpacing(10);
                itemStage.getScene().setRoot(dialogBox);
            }
            itemStage.close();
        });

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-padding: 20px; -fx-background-color: rgba(0, 0, 0, 0.8); -fx-background-radius: 10;");
        vbox.setSpacing(10);
        vbox.getChildren().addAll(messageLabel, useButton, stockButton);

        Scene dialogScene = new Scene(vbox);
        dialogScene.setFill(null);
        itemStage.setScene(dialogScene);
        itemStage.show();
    }

    private void showCongratulationsMessage() {
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

    public void showPotionDialog(Potion potion) {
        Stage potionStage = new Stage();
        potionStage.initModality(Modality.APPLICATION_MODAL);
        potionStage.initOwner(pane.getScene().getWindow());
        potionStage.initStyle(StageStyle.TRANSPARENT);

        Label messageLabel = new Label("Voulez-vous utiliser ou stocker cette potion " + potion.getType() + " ?");
        messageLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-padding: 20px; -fx-background-color: rgba(0, 0, 0, 0.8); -fx-background-radius: 10;");
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(300);

        Button useButton = new Button("Utiliser");
        useButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        useButton.setOnAction(e -> {
            potion.applyTo(player);
            removePotionFromEntitiesAndUI(potion);
            potionStage.close();
        });

        Button stockButton = new Button("Stocker");
        stockButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        stockButton.setOnAction(e -> {
            boolean added = player.getInventory().addPotion(potion);
            if (added) {
                removePotionFromEntitiesAndUI(potion);
            }
            potionStage.close();
        });

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-padding: 20px; -fx-background-color: rgba(0, 0, 0, 0.8); -fx-background-radius: 10;");
        vbox.setSpacing(10);
        vbox.getChildren().addAll(messageLabel, useButton, stockButton);

        Scene dialogScene = new Scene(vbox);
        dialogScene.setFill(null);

        potionStage.setScene(dialogScene);
        potionStage.showAndWait();
    }


    public boolean isCleared() {
        return monsters.stream().allMatch(Monster::isDead);
    }
}
