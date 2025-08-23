import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventory {
    private Map<String, Potion> potions;
    private List<Entity> items;
    private static final int MAX_ITEMS = 5;
    private StackPane pane;

    public Inventory(StackPane pane) {
        this.potions = new HashMap<>();
        this.items = new ArrayList<>();
        this.pane = pane;
    }

    public boolean addPotion(Potion potion) {
        if (getPotionCount() >= MAX_ITEMS) {
            showFullInventoryMessage();
            return false;
        }
        String type = potion.getType();
        if (potions.containsKey(type)) {
            potions.get(type).increaseQuantity(potion.getQuantity());
        } else {
            potions.put(type, potion);
        }
        return true;
    }

    public boolean removePotion(Potion potion) {
        String type = potion.getType();
        if (potions.containsKey(type)) {
            Potion existingPotion = potions.get(type);
            if (existingPotion.getQuantity() > potion.getQuantity()) {
                existingPotion.decreaseQuantity(potion.getQuantity());
            } else {
                potions.remove(type);
            }
            return true;
        }
        return false;
    }

    public Map<String, Potion> getPotions() {
        return potions;
    }

    public int getPotionCount() {
        return potions.values().stream().mapToInt(Potion::getQuantity).sum();
    }

    public boolean addItem(Entity item) {
        if (items.size() >= MAX_ITEMS) {
            showFullInventoryMessage();
            return false;
        }
        items.add(item);
        return true;
    }

    public int getItemCount() {
        return items.size();
    }

    public void removeItem(Entity item) {
        items.remove(item);
    }

    public <T extends Entity> T getItem(Class<T> itemType) {
        for (Entity item : items) {
            if (itemType.isInstance(item)) {
                return itemType.cast(item);
            }
        }
        return null;
    }

    public <T extends Entity> void useItem(Class<T> itemType, Player player) {
        T item = getItem(itemType);
        if (item != null) {
            if (item instanceof Item1) {
                ((Item1) item).use(player);
            } else if (item instanceof Item2) {
                ((Item2) item).use(player);
            } else if (item instanceof Item5) {
                ((Item5) item).use(player);
            }
            items.remove(item);
        }
    }

    public List<Entity> getItems() {
        return items;
    }

    private void showFullInventoryMessage() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Inventaire plein");
            alert.setHeaderText(null);
            alert.setContentText("L'inventaire est plein. Impossible d'ajouter l'item.");
            alert.showAndWait();
        });
    }
}
