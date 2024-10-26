package Defense;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;

public class DefenseStructure {
    private String type;
    private int strength;
    private String imagePath;
    private Texture texture;
    private Vector2 position;

    public DefenseStructure(String type, String imagePath, int strength, Vector2 position) {
        this.type = type;
        this.imagePath = imagePath;
        this.texture = new Texture(Gdx.files.internal(imagePath));
        this.strength = strength;
        this.position = position;
    }

    public Texture getTexture() {
        return texture;
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getStrength() {
        return strength;
    }

    public void takeDamage(int damage) {
        this.strength -= damage;
        if (strength < 0) strength = 0;
    }

    public void dispose() {
        texture.dispose();
    }
}
