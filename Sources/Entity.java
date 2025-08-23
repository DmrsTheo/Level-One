import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;

public abstract class Entity {
    protected ImageView sprite;
    protected double x, y;

    public Entity(double x, double y, String imagePath) {
        this.x = x;
        this.y = y;
        sprite = new ImageView(ImageManager.getImage(imagePath));
        if (imagePath.equals(getClass().getResource("/NPCs/NPC1_Left.png").toExternalForm()) || imagePath.equals(getClass().getResource("/NPCs/NPC2_Left.png").toExternalForm())
                || imagePath.equals(getClass().getResource("/NPCs/NPC1_Right.png").toExternalForm()) || imagePath.equals(getClass().getResource("/NPCs/NPC2_Right.png").toExternalForm())) {
            sprite.setFitWidth(30);
            sprite.setFitHeight(40);
        } else if (imagePath.equals(getClass().getResource("/Boss/Boss_Idle.png").toExternalForm()) || imagePath.equals(getClass().getResource("/Boss/Boss_Attack.png").toExternalForm())) {
            sprite.setFitWidth(80);
            sprite.setFitHeight(80);
        } else if (imagePath.equals(getClass().getResource("/Boss/Boss_Death.png").toExternalForm())) {
            sprite.setFitWidth(40);
            sprite.setFitHeight(40);
        } else if (imagePath.equals(getClass().getResource("/Potions/potion.png").toExternalForm()) || imagePath.equals(getClass().getResource("/Potions/potionAttack.png").toExternalForm())
                || imagePath.equals(getClass().getResource("/Potions/potionHealth.png").toExternalForm())) {
            sprite.setFitWidth(30);
            sprite.setFitHeight(30);
        } else if (imagePath.equals(getClass().getResource("/King/king_cage.png").toExternalForm())) {
            sprite.setFitWidth(90);
            sprite.setFitHeight(90);
        } else {
            sprite.setFitWidth(50);
            sprite.setFitHeight(50);
        }
        sprite.setTranslateX(x);
        sprite.setTranslateY(y);
    }

    public ImageView getSprite() {
        return sprite;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Bounds getCollisionBounds() {
        Bounds bounds = sprite.getBoundsInParent();
        double shrinkFactor = 0.8; // 80%
        double width = bounds.getWidth() * shrinkFactor;
        double height = bounds.getHeight() * shrinkFactor;
        double minX = bounds.getMinX() + (bounds.getWidth() - width) / 2;
        double minY = bounds.getMinY() + (bounds.getHeight() - height) / 2;
        return new BoundingBox(minX, minY, width, height);
    }
}
