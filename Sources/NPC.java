import javafx.scene.layout.StackPane;

public class NPC extends Entity {
    private String imageUrl;
    private Inventory inventory;
    private boolean hasBeenRobbed;

    public NPC(double x, double y, String imagePath, StackPane pane) {
        super(x, y, imagePath);
        this.imageUrl = imagePath;
        this.inventory = new Inventory(pane);
        this.hasBeenRobbed = false;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public boolean hasBeenRobbed() {
        return hasBeenRobbed;
    }

    public void setHasBeenRobbed(boolean hasBeenRobbed) {
        this.hasBeenRobbed = hasBeenRobbed;
    }
}
