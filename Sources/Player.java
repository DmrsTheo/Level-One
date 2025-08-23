import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Player {
    private ImageView sprite;
    private double x, y;
    private int health;
    private int damage;
    private String direction;
    private Inventory inventory;
    private int attackPower;
    private final int maxAttackPower = 300;
    private Set<NPC> metNPCs;
    private NPC lastMetNPC;

    public Player(double x, double y, StackPane pane) {
        this.x = x;
        this.y = y;
        this.health = 200;
        this.damage = 200;
        this.attackPower = 150;
        this.inventory = new Inventory(pane);
        this.metNPCs = new HashSet<>();
        sprite = new ImageView(new Image(getClass().getResource("/KnightBasic/Idle/UP/Knight_Idle.png").toExternalForm()));
        sprite.setTranslateX(x);
        sprite.setTranslateY(y);
        sprite.setPreserveRatio(true);

        // On ajoute les items 1,2 et 5 initialement dans l'inventaire du joueur
        inventory.addItem(new Item1(0, 0, getClass().getResource("/Items/item1.png").toExternalForm()));
        inventory.addItem(new Item2(0, 0, getClass().getResource("/Items/item2.png").toExternalForm()));
        inventory.addItem(new Item5(0, 0, getClass().getResource("/Items/item5.png").toExternalForm()));
    }

    public ImageView getSprite() {
        return sprite;
    }

    public void move(KeyCode key, List<Entity> obstacles) {
        double newX = x;
        double newY = y;

        switch (key) {
            case UP:
                newY -= 10;
                sprite.setImage(new Image(getClass().getResource("/KnightBasic/Idle/UP/Knight_Idle.png").toExternalForm()));
                this.direction = "UP";
                break;
            case DOWN:
                newY += 10;
                sprite.setImage(new Image(getClass().getResource("/KnightBasic/Idle/DOWN/Knight_Idle.png").toExternalForm()));
                this.direction = "DOWN";
                break;
            case LEFT:
                newX -= 10;
                sprite.setImage(new Image(getClass().getResource("/KnightBasic/Idle/LEFT/Knight_Idle.png").toExternalForm()));
                this.direction = "LEFT";
                break;
            case RIGHT:
                newX += 10;
                sprite.setImage(new Image(getClass().getResource("/KnightBasic/Idle/RIGHT/Knight_Idle.png").toExternalForm()));
                this.direction = "RIGHT";
                break;
            default:
                break;
        }

        if (!isCollision(newX, newY, obstacles) && isWithinBounds(newX, newY)) {
            x = newX;
            y = newY;
            sprite.setTranslateX(x);
            sprite.setTranslateY(y);
        }
    }

    private boolean isCollision(double newX, double newY, List<Entity> obstacles) {
        sprite.setTranslateX(newX);
        sprite.setTranslateY(newY);
        for (Entity obstacle : obstacles) {
            if (sprite.getBoundsInParent().intersects(obstacle.getCollisionBounds())) {
                sprite.setTranslateX(x);
                sprite.setTranslateY(y);
                if (obstacle instanceof NPC) {
                    metNPCs.add((NPC) obstacle);
                    lastMetNPC = (NPC) obstacle;
                }
                return true;
            }
        }
        sprite.setTranslateX(x);
        sprite.setTranslateY(y);
        return false;
    }

    private boolean isWithinBounds(double newX, double newY) {
        Bounds bounds = sprite.getBoundsInParent();
        double minX = -245;
        double minY = -290;
        double maxX = sprite.getScene().getWidth() - bounds.getWidth();
        double maxY = sprite.getScene().getHeight() - bounds.getHeight();
        return newX >= minX && newX <= maxX && newY >= minY && newY <= maxY;
    }

    public void heal() {
        this.health = Math.min(this.health + 20, 200);
    }

    public void superHeal() {
        this.health = Math.min(this.health + 80, 200);
    }

    public void increaseAttack() {
        this.damage = Math.min(this.damage + 50, 250);
    }

    public int getHealth() {
        return health;
    }

    public int attack() {
        return this.damage;
    }

    public int getPotionCount() {
        return inventory.getPotionCount();
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.health = 0;
        }
    }

    public boolean isDead() {
        return this.health <= 0;
    }

    public void die() {
        switch (this.direction) {
            case "UP":
                sprite.setFitWidth(152);
                sprite.setFitHeight(162);
                sprite.setImage(new Image(getClass().getResource("/KnightBasic/Die/UP/Knight_Die.gif").toExternalForm()));
                break;
            case "DOWN":
                sprite.setFitWidth(152);
                sprite.setFitHeight(162);
                sprite.setImage(new Image(getClass().getResource("/KnightBasic/Die/DOWN/Knight_Die.gif").toExternalForm()));
                break;
            case "LEFT":
                sprite.setFitWidth(152);
                sprite.setFitHeight(162);
                sprite.setImage(new Image(getClass().getResource("/KnightBasic/Die/LEFT/Knight_Die.gif").toExternalForm()));
                break;
            case "RIGHT":
                sprite.setFitWidth(152);
                sprite.setFitHeight(162);
                sprite.setImage(new Image(getClass().getResource("/KnightBasic/Die/RIGHT/Knight_Die.gif").toExternalForm()));
                break;
            default:
                break;
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setX(double x) {
        this.x = x;
        sprite.setTranslateX(x);
    }

    public void setY(double y) {
        this.y = y;
        sprite.setTranslateY(y);
    }

    public String getDirection() {
        return this.direction;
    }

    public boolean hasMetNPC() {
        return !metNPCs.isEmpty();
    }

    public void meetNPC(NPC npc) {
        if (!metNPCs.contains(npc)) {
            metNPCs.add(npc);
        }
    }

    public NPC getLastMetNPC() {
        return lastMetNPC;
    }
}
