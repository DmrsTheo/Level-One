public class Potion extends Entity {
    private String type;
    private int quantity;
    private boolean handled = false;

    public Potion(double x, double y, String imagePath, String type) {
        super(x, y, imagePath);
        this.type = type;
        this.quantity = 1; // Initialiser la quantité à 1
    }

    public boolean isHandled() {
        return handled;
    }

    public void setHandled(boolean handled) {
        this.handled = handled;
    }

    public String getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity(int amount) {
        this.quantity += amount;
    }

    public void decreaseQuantity(int amount) {
        this.quantity -= amount;
        if (this.quantity < 0) this.quantity = 0;
    }

    public void applyTo(Player player) {
        if ("Health".equals(type)) {
            player.heal();
        } else if ("Attack".equals(type)) {
            player.increaseAttack();
        } else if ("Super Health".equals(type)) {
            player.superHeal();
        }
        decreaseQuantity(1);
    }

    public boolean isEmpty() {
        return quantity <= 0;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
        sprite.setTranslateX(x);
        sprite.setTranslateY(y);
    }
}
