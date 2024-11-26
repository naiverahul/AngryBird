// package Pigs;

// // import com.badlogic.gdx.Gdx;
// // import com.badlogic.gdx.graphics.Texture;

// // public class Pig {
// //     private String name;
// //     private int health;
// //     private String imagePath;
// //     private Texture pigtexture;
// //     private int level;

// //     public Pig(String name, String imagePath, int level, int health) {
// //         this.name = name;
// //         this.imagePath = imagePath;
// //         this.pigtexture = new Texture(Gdx.files.internal(imagePath));
// //         this.level = level;
// //         this.health = health;
// //     }
// //     public String getName() {
// //         return name;
// //     }

// //     public Texture getTexture() {
// //         return pigtexture;
// //     }

// //     public int getLevel() {
// //         return level;
// //     }

// //     public int getHealth() {
// //         return health;
// //     }

// //     public void levelUp() {
// //         this.level++;
// //         this.health += 50;  // Example: increase power on level-up
// //     }

// //     // Dispose method to release pigtexture when done
// //     public void dispose() {
// //         pigtexture.dispose();
// //     }
// // }


// import com.badlogic.gdx.Gdx;
// import com.badlogic.gdx.graphics.Texture;
// import com.badlogic.gdx.graphics.g2d.Batch;
// import com.badlogic.gdx.physics.box2d.Body;
// import com.badlogic.gdx.physics.box2d.World;
// import com.badlogic.gdx.scenes.scene2d.Actor;

// public class Pig extends Actor {
//     private Texture pigtexture;
//     private Body pigBody;
//     private int health;

//     // public Pig(String name, String texturePath, int health) {
//     public Pig(World world, String )
    
//         this.health = health;
//         this.pigtexture = new Texture(Gdx.files.internal(texturePath));
//         setSize(pigtexture.getWidth(), pigtexture.getHeight());
//     }

//     @Override
//     public void draw(Batch batch, float parentAlpha) {
//         // Draw the pig pigtexture at the current position
//         batch.draw(pigtexture, getX(), getY(), getWidth(), getHeight());
//     }

//     public void takeDamage(int damage) {
//         this.health -= damage;
//         if (this.health <= 0) {
//             this.remove();  // Remove the pig from the stage when health is zero
//         }
//     }

//     @Override
//     public void act(float delta) {
//         super.act(delta);
//         // // Update the actor's position to match the body's position
//         // // setPosition(pigBody.getPosition().x * 100f - pigTexture.getWidth() / 2f, pigBody.getPosition().y * 100f - pigTexture.getHeight() / 2f);

//         // setPosition(pigBody.getPosition().x * 100f - pigTexture.getWidth() / 2f, pigBody.getPosition().y * 100f - pigTexture.getHeight() / 2f);

//         setPosition(pigBody.getPosition().x * 100f - pigTexture.getWidth() / 2f, pigBody.getPosition().y * 100f - pigTexture.getHeight() / 2f);
//     }

//     // @Override
//     public void dispose() {
//         pigtexture.dispose();
//     }
// }
