import javafx.geometry.Bounds;
import javafx.geometry.BoundingBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class Monster extends Entity {
    private boolean dead;
    private int health;
    private int damage;
    private String monsterType;
    private String imageUrl;
    private Inventory inventory;
    private ProgressBar healthBar;
    private Pane gamePane;
    private DungeonLevel dungeonLevel;
    private StackPane pane; // Add this field

    public static final String MONSTER1 = "monster1";
    public static final String MONSTER2 = "monster2";
    public static final String BOSS = "boss";

    public Monster(double x, double y, String imagePath, String monsterType, Pane gamePane, DungeonLevel dungeonLevel, StackPane pane) {
        super(x, y, imagePath);
        this.dead = false;
        this.imageUrl = imagePath;
        this.monsterType = monsterType;
        this.inventory = new Inventory(pane); // Pass the StackPane to Inventory
        this.gamePane = gamePane;
        this.dungeonLevel = dungeonLevel;
        this.pane = pane; // Initialize the StackPane field

        healthBar = new ProgressBar();
        healthBar.setPrefWidth(50);
        healthBar.setStyle("-fx-accent: red;");
        gamePane.getChildren().add(healthBar);

        switch (monsterType) {
            case MONSTER1:
                this.health = 150;
                this.damage = 30;
                break;
            case MONSTER2:
                this.health = 300;
                this.damage = 40;
                break;
            case BOSS:
                this.health = 600;
                this.damage = 50;
                break;
            default:
                throw new IllegalArgumentException("Unknown monster type: " + monsterType);
        }
        updateHealthBar();
        updateHealthBarPosition();

        if (!this.getImageUrl().equals(getClass().getResource("/Boss/Boss_Attack.png").toExternalForm()) ||
                !this.getImageUrl().equals(getClass().getResource("/Boss/Boss_Idle.png").toExternalForm()) ||
                !this.getImageUrl().equals(getClass().getResource("/Boss/Boss_Death.png").toExternalForm())) {
            Potion attackPotion = EntityFactory.createPotion(x, y, getClass().getResource("/Potions/potionAttack.png").toExternalForm(), "Attack");
            this.inventory.addPotion(attackPotion);
        }
    }

    public void move() {
        updateHealthBarPosition();
    }

    public int getHealth() {
        return this.health;
    }

    public int attack() {
        return this.damage;
    }

    public boolean isDead() {
        return dead;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        sprite.setImage(new Image(imageUrl));
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.health = 0;
            this.dead = true;
            healthBar.setVisible(false);
            if (!this.getImageUrl().equals(getClass().getResource("/Boss/Boss_Attack.png").toExternalForm()) ||
                    !this.getImageUrl().equals(getClass().getResource("/Boss/Boss_Idle.png").toExternalForm()) ||
                    !this.getImageUrl().equals(getClass().getResource("/Boss/Boss_Death.png").toExternalForm())) {
                dropPotion();
            }
        }
        updateHealthBar();
    }

    public void die() {
        this.dead = true;
        if (this.getImageUrl().equals(getClass().getResource("/Boss/Boss_Attack.png").toExternalForm())) {
            sprite.setImage(new Image(getClass().getResource("/Boss/Boss_Death.png").toExternalForm()));
        } else {
            sprite.setVisible(false);
        }

        healthBar.setVisible(false);
    }

    public Inventory getInventory() {
        return inventory;
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

    private void updateHealthBar() {
        healthBar.setProgress((double) health / getMaxHealth());
    }

    private int getMaxHealth() {
        switch (monsterType) {
            case MONSTER1:
                return 150;
            case MONSTER2:
                return 300;
            case BOSS:
                return 600;
            default:
                throw new IllegalArgumentException("Unknown monster type: " + monsterType);
        }
    }

    private void updateHealthBarPosition() {
        Bounds bounds = sprite.getBoundsInParent();
        healthBar.setLayoutX(bounds.getMinX() + (bounds.getWidth() - healthBar.getWidth()) / 2);
        healthBar.setLayoutY(bounds.getMinY() - 10);
    }

    private void dropPotion() {
        for (Potion potion : inventory.getPotions().values()) {
            if (!this.getImageUrl().equals(getClass().getResource("/Boss/Boss_Attack.png").toExternalForm()) ||
                    !this.getImageUrl().equals(getClass().getResource("/Boss/Boss_Idle.png").toExternalForm()) ||
                    !this.getImageUrl().equals(getClass().getResource("/Boss/Boss_Death.png").toExternalForm())) {
                potion.setPosition(this.getX(), this.getY());
                gamePane.getChildren().add(potion.getSprite());
                dungeonLevel.showPotionDialog(potion);
            }
        }
    }
}