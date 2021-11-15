import java.awt.Image;

import oop.ex2.*;

/**
 * The API spaceships need to implement for the SpaceWars game.
 * This is an abstract class that all types of ships inherit from.
 */
public abstract class SpaceShip {

    /* ATTRIBUTES */
    /* Constant Variables  */
    private static final int initialMaxHealth = 22;
    private static final int initialMaxEnergyLevel = 210;
    private static final int initialCurrentEnergyLevel = 190;
    /**
     * The defined fire cooldown for the ships (amount of rounds to wait between shots)
     */
    protected static final int fireCooldown = 7;

    /*
    Responsible for the spaceship's physics attributes (position, direction and velocity)
     */
    private SpaceShipPhysics physics = new SpaceShipPhysics();

    /**
    Ship's Maximal Energy Level
     */
    protected int maxEnergyLevel = initialMaxEnergyLevel;

    /**
    Current energy level (between 0 and max energy level)
     */
    protected int currentEnergyLevel = initialCurrentEnergyLevel;

    /**
    Spaceship's health
     */
    protected int health = initialMaxHealth;

    /**
    Spaceship's Shield
     */
    protected boolean shields = false;

    /**
    Round counter for each ship
     */
    protected int roundCount = 0;

    /**
    Has fired in the last 7 rounds
     */
    protected boolean hasFired = false;


    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     *
     * @param game The game object to which this ship belongs.
     */
    public abstract void doAction(SpaceWars game);

    /**
     * This method is called every time a collision with this ship occurs.
     */
    public void collidedWithAnotherShip() {
        if(shields) {
            maxEnergyLevel += 18;
            currentEnergyLevel += 18;
        }
        else {
            lowerEnergyLevel();
            health -= 1;
        }
    }

    /**
     * This method is called whenever a ship has died. It resets the ship's
     * attributes, and starts it at a new random position.
     */
    public void reset() {
        physics = new SpaceShipPhysics();
        health = initialMaxHealth;
        maxEnergyLevel = initialMaxEnergyLevel;
        currentEnergyLevel = initialCurrentEnergyLevel;
        shields = false;
        roundCount = 0;
        hasFired = false;
    }

    /**
     * Checks if this ship is dead.
     *
     * @return True if the ship is dead. false otherwise.
     */
    public boolean isDead() {
        return health <= 0;
    }

    /**
     * Gets the physics object that controls this ship.
     *
     * @return The physics object that controls the ship.
     */
    public SpaceShipPhysics getPhysics() {
        return physics;
    }

    /**
     * This method is called by the SpaceWars game object when ever this ship
     * gets hit by a shot.
     */
    public void gotHit() {
        if(!shields) {
            lowerEnergyLevel();
            health -= 1;
        }
    }

    /**
     * Lowers the maximum energy level of the spaceship, and checks if the current is bigger than the max (and updates)
     */
    private void lowerEnergyLevel() {
        if(maxEnergyLevel >= 10) {
            maxEnergyLevel -= 10;
        }
        else {
            maxEnergyLevel = 0;
        }
        if(currentEnergyLevel > maxEnergyLevel) {
            currentEnergyLevel = maxEnergyLevel;
        }
    }

    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     *
     * @return The image of this ship.
     */
    public Image getImage() {
        if(shields) {
            return GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD;
        }
        return GameGUI.ENEMY_SPACESHIP_IMAGE;
    }

    /**
     * Attempts to fire a shot.
     *
     * @param game The game object.
     */
    public void fire(SpaceWars game) {
        if(currentEnergyLevel >= 19 && !hasFired) {
            game.addShot(physics);
            currentEnergyLevel -= 19;
            hasFired = true;
        }
    }

    /**
     * Attempts to turn on the shield.
     */
    public void shieldOn() {
        if(currentEnergyLevel >= 3) {
            shields = true;
            currentEnergyLevel -= 3;
        }
    }

    /**
     * Attempts to teleport.
     */
    public void teleport() {
        if(currentEnergyLevel >= 140) {
            physics = new SpaceShipPhysics();
            currentEnergyLevel -= 140;
        }
    }
}
