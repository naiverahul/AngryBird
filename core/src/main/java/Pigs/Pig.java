package Pigs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Pig {
    private String name;
    private int health;
    private String imagePath;
    private Texture texture;
    private int level;

    public Pig(String name, String imagePath, int level, int health) {
        this.name = name;
        this.imagePath = imagePath;
        this.texture = new Texture(Gdx.files.internal(imagePath));
        this.level = level;
        this.health = health;
    }
    public String getName() {
        return name;
    }

    public Texture getTexture() {
        return texture;
    }

    public int getLevel() {
        return level;
    }

    public int getHealth() {
        return health;
    }

    public void levelUp() {
        this.level++;
        this.health += 50;  // Example: increase power on level-up
    }

    // Dispose method to release texture when done
    public void dispose() {
        texture.dispose();
    }
}
