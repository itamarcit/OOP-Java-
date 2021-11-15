/**
 * Basher ship controlled by the computer. It sets it's course to the nearest spaceship and attempts to bash at it with
 * shields on
 */
public class BasherShip extends SpaceShip {

    private static final double minDistanceShield = 0.19d;

    @Override
    public void doAction(SpaceWars game) {
        shields = false;
        SpaceShip closest = game.getClosestShipTo(this);
        double angle = this.getPhysics().angleTo(closest.getPhysics());
        if (angle < 0) {
            angle = -1;
        } else {
            angle = 1;
        }
        this.getPhysics().move(true, (int) angle);
        if(closest.getPhysics().distanceFrom(this.getPhysics()) <= minDistanceShield) {
            shieldOn();
        }
        if (currentEnergyLevel < maxEnergyLevel) {
            currentEnergyLevel++;
        }
    }
}
