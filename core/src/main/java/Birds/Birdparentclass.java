package Birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Birdparentclass {
    private String name;
    private String imagePath;
    private Texture texture;
    private int level;
    private int attackingPower;

    public Birdparentclass(String name, String imagePath, int level, int attackingPower) {
        this.name = name;
        this.imagePath = imagePath;
        this.texture = new Texture(Gdx.files.internal(imagePath));
        this.level = level;
        this.attackingPower = attackingPower;
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

    public int getAttackingPower() {
        return attackingPower;
    }

    public void levelUp() {
        this.level++;
        this.attackingPower += 50;  // Example: increase power on level-up
    }

    // Dispose method to release texture when done
    public void dispose() {
        texture.dispose();
    }
}
