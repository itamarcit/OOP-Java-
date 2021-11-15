/**
 * Aggressive ship controlled by the computer. It sets it's course to the nearest ship to it, and fires at it once
 * it's close enough
 */
public class AggressiveShip extends SpaceShip {

    private static final double minFireAngle = 0.21d;

    /**
     * Redefines the ship's behavior to match the aggressive ship.
     * @param game The game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {
        SpaceShip closest = game.getClosestShipTo(this);
        double angle = this.getPhysics().angleTo(closest.getPhysics());
        int move_angle;
        if (angle < 0) {
            move_angle = -1;
        }
        else {
            move_angle = 1;
        }
        this.getPhysics().move(true, move_angle);
        if (Math.abs(angle) < minFireAngle) {
            fire(game);
        }
        if (currentEnergyLevel < maxEnergyLevel) {
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
