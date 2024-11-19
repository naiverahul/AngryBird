package Birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Birdparentclass {
    private String name;
    private String imagePath;
    private Texture texture;
    private int level;
    private int attackingPower;
    private Vector2 position;

    public Birdparentclass(String name, String imagePath, int level, int attackingPower) {
        this.name = name;
        this.imagePath = imagePath;
        this.texture = new Texture(Gdx.files.internal(imagePath));
        this.level = level;
        this.attackingPower = attackingPower;
        this.position = new Vector2(0,0);
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

    public  Vector2 getPosition(){
        return position;
    }
    public void setPosition(float x,float y){
        position.x = x;
        position.y = y;
    }

    // Dispose method to release texture when done
    public void dispose() {
        texture.dispose();
    }
}
