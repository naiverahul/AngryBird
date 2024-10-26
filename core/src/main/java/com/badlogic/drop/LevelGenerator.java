package com.badlogic.drop;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import Defense.DefenseStructure;
import Pigs.Pig;

import java.util.Random;

public class LevelGenerator {

    private Stage stage;
    private Random random;

    // Define constants for block and pig dimensions (you can tweak these)
    private final int BLOCK_SIZE = 60;

    // Screen bounds for object placement
    private final int MIN_X = 1100;   // Minimum X position
    private final int MAX_X = 1700;  // Maximum X position
    private final int MIN_Y = 150;    // Minimum Y position
    private final int MAX_Y = 400;   // Maximum Y position

    // Constructor
    public LevelGenerator(Stage stage) {
        this.stage = stage;
        this.random = new Random();
    }

    public void generateLevel() {
        // Clear the current level
        stage.clear();

        generateDefenseStructures(5); // Generate 5 defense structures
    }

    private void generateDefenseStructures(int structureCount) {
        for (int i = 0; i < structureCount; i++) {
            // Define the base X position for this structure
            int baseX = MIN_X + random.nextInt(MAX_X - MIN_X);
            
            // Define how many blocks high the structure will be
            int structureHeight = 3 + random.nextInt(3); // Towers of 3 to 5 blocks
    
            // Alternate types of blocks in the structure
            String[] blockTypes = {"wood", "stone", "glass"};
            
            for (int j = 0; j < structureHeight; j++) {
                // Set the Y position based on the height of the structure
                int blockHeight = BLOCK_SIZE;
                int blockY = MIN_Y + j * blockHeight;
    
                // Randomly select block type for the current level of the structure
                String type = blockTypes[j % blockTypes.length];  // Cycle through wood, stone, and glass
                String texturePath = getTextureForType(type);
                Vector2 position = new Vector2(baseX, blockY);
    
                // Create a block and set its size
                DefenseStructure structure = new DefenseStructure(type, texturePath, getRandomStrength(type), position);
                structure.setSize(BLOCK_SIZE, BLOCK_SIZE);  // Set a reasonable size for each block
                stage.addActor(structure);
            }
            
        
            int pigY = MIN_Y + structureHeight * BLOCK_SIZE;
            Pig pig = new Pig("Pig" + i, "pig.png", 100);
            pig.setPosition(baseX, pigY);
            pig.setSize(50, 50);  // Set a reasonable size for the pig
            stage.addActor(pig);
            
        }
    }
    
    


    private String getTextureForType(String type) {
        switch (type) {
            case "wood":
                // return "wood_block.png";  // Use appropriate texture paths
                return "wood_rectangle.png";  // Use appropriate texture paths
            case "stone":
                // return "stone_block.png";
                return "stone_circle.png";
            case "glass":
                return "glass_rectangle.png";
            default:
                return "wood_rectangle.png";  // Default to wood texture
        }
    }

    private int getRandomStrength(String type) {
        switch (type) {
            case "wood":
                return 100;
            case "stone":
                return 200;
            case "glass":
                return 50;
            default:
                return 100;  // Default strength for wood
        }
    }
}
