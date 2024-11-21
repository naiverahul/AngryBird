package Pigs;

// import com.badlogic.gdx.Gdx;
// import com.badlogic.gdx.graphics.Texture;

// public class Pig {
//     private String name;
//     private int health;
//     private String imagePath;
//     private Texture texture;
//     private int level;

//     public Pig(String name, String imagePath, int level, int health) {
//         this.name = name;
//         this.imagePath = imagePath;
//         this.texture = new Texture(Gdx.files.internal(imagePath));
//         this.level = level;
//         this.health = health;
//     }
//     public String getName() {
//         return name;
//     }

//     public Texture getTexture() {
//         return texture;
//     }

//     public int getLevel() {
//         return level;
//     }

//     public int getHealth() {
//         return health;
//     }

//     public void levelUp() {
//         this.level++;
//         this.health += 50;  // Example: increase power on level-up
//     }

//     // Dispose method to release texture when done
//     public void dispose() {
//         texture.dispose();
//     }
// }


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Pig extends Actor {
    private Texture texture;
    private String name;
    private int health;

    public Pig(String name, String texturePath, int health) {
        this.name = name;
        this.health = health;
        this.texture = new Texture(Gdx.files.internal(texturePath));
        setSize(texture.getWidth(), texture.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Draw the pig texture at the current position
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.remove();  // Remove the pig from the stage when health is zero
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // Additional logic, e.g., movement or animation can go here if needed
    }

    // @Override
    public void dispose() {
        texture.dispose();
    }
}
