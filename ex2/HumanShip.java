import java.awt.*;
import oop.ex2.*;

/**
 * A spaceship controlled by the user.
 */
public class HumanShip extends SpaceShip{

    /**
     * Returns the current spaceship's image
     * @return The spaceship's image
     */
    @Override
    public Image getImage() {
        if(shields) {
            return GameGUI.SPACESHIP_IMAGE_SHIELD;
        }
        return GameGUI.SPACESHIP_IMAGE;
    }

    @Override
    public void doAction(SpaceWars game) {
        shields = false;
        if(game.getGUI().isTeleportPressed()) {
            teleport();
        }
        int angle = 0;
        if(game.getGUI().isRightPressed() || game.getGUI().isLeftPressed()) {
            if(game.getGUI().isLeftPressed()) {
                angle = 1;
            }
            if(game.getGUI().isRightPressed()) {
                angle = -1;
            }
            if(game.getGUI().isRightPressed() && game.getGUI().isLeftPressed()) {
                angle = 0;
            }
        }
        this.getPhysics().move(game.getGUI().isUpPressed(), angle);
        if(game.getGUI().isShieldsPressed()) {
            shieldOn();
        }
        if(game.getGUI().isShotPressed()) {
            fire(game);
        }
        if(currentEnergyLevel < maxEnergyLevel) {
            currentEnergyLevel++;
        }
        if(hasFired) {
            roundCount++;
            if(roundCount < fireCooldown) {
                roundCount = 0;
                hasFired = false;
            }
        }
    }
}
