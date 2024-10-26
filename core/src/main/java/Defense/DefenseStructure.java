package Defense;

// import com.badlogic.gdx.graphics.Texture;
// import com.badlogic.gdx.math.Vector2;
// import com.badlogic.gdx.Gdx;

// public class DefenseStructure {
//     private String type;
//     private int strength;
//     private String imagePath;
//     private Texture texture;
//     private Vector2 position;

//     public DefenseStructure(String type, String imagePath, int strength, Vector2 position) {
//         this.type = type;
//         this.imagePath = imagePath;
//         this.texture = new Texture(Gdx.files.internal(imagePath));
//         this.strength = strength;
//         this.position = position;
//     }

//     public Texture getTexture() {
//         return texture;
//     }

//     public Vector2 getPosition() {
//         return position;
//     }

//     public int getStrength() {
//         return strength;
//     }

//     public void takeDamage(int damage) {
//         this.strength -= damage;
//         if (strength < 0) strength = 0;
//     }

//     public void dispose() {
//         texture.dispose();
//     }
// }


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class DefenseStructure extends Actor {
    private Texture texture;
    private String type;  // "wood", "stone", or "glass"
    private int strength; // Determines how much damage it can take

    public DefenseStructure(String type, String texturePath, int strength, Vector2 position) {
        this.type = type;
        this.strength = strength;
        this.texture = new Texture(Gdx.files.internal(texturePath));

        // Set the position and size based on the texture
        setPosition(position.x, position.y);
        setSize(texture.getWidth(), texture.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Draw the structure at its position
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    public void takeDamage(int damage) {
        this.strength -= damage;
        if (this.strength <= 0) {
            this.remove();  // Remove the structure from the stage if it’s destroyed
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // Additional behavior (e.g., falling when damaged) can be added here
    }

    // @Override
    public void dispose() {
        texture.dispose();
    }
}
