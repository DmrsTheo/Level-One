import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.Map;

public class ImageManager {
    private static final Map<String, Image> images = new HashMap<>();

    public static Image getImage(String path) {
        if (!images.containsKey(path)) {
            images.put(path, new Image(path));
        }
        return images.get(path);
    }
}
