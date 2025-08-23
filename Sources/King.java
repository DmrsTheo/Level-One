import javafx.scene.image.Image;

public class King extends Entity {
    public King(double x, double y, String imagePath) {
        super(x, y, imagePath);
    }

    public void setImageUrl(String imageUrl) {
        sprite.setImage(new Image(imageUrl));
    }
}
