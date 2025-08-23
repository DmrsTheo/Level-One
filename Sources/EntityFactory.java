import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class EntityFactory {
    public static Monster createMonster(double x, double y, String imagePath, String monsterType, Pane gamePane, DungeonLevel dungeonLevel, StackPane pane) {
        return new Monster(x, y, imagePath, monsterType, gamePane, dungeonLevel, pane);
    }

    public static NPC createNPC(double x, double y, String imagePath, StackPane pane) {
        return new NPC(x, y, imagePath, pane);
    }

    public static Potion createPotion(double x, double y, String imagePath, String type) {
        return new Potion(x, y, imagePath, type);
    }

    public static King createKing(double x, double y, String imagePath) {
        return new King(x, y, imagePath);
    }

    public static Item1 createItem1(double x, double y, String imagePath) {
        return new Item1(x, y, imagePath);
    }

    public static Item2 createItem2(double x, double y, String imagePath) {
        return new Item2(x, y, imagePath);
    }

    public static Item5 createItem5(double x, double y, String imagePath) {
        return new Item5(x, y, imagePath);
    }
}
