/**
 * Runner enemy ship. It will always try to get away from the closest ship to it. If the closest ship to the runner is
 * closer than 0.23 radians, it will attempt to teleport away.
 */
public class RunnerShip extends SpaceShip {

    private static final double minTeleportDistance = 0.25d;
    private static final double minTeleportAngle = 0.23d;

    /**
     * Redefines the ship's behavior to match the runner ship.
     * @param game The game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {
        SpaceShip closest = game.getClosestShipTo(this);
        if (closest.getPhysics().distanceFrom(this.getPhysics()) < minTeleportDistance
                && Math.abs(closest.getPhysics().angleTo(this.getPhysics())) < minTeleportAngle) {
            teleport();
        }
        double angle = this.getPhysics().angleTo(closest.getPhysics());
        if (angle < 0) {
            angle = 1;
        } else {
            angle = -1;
        }
        this.getPhysics().move(true, (int) angle);
        if (currentEnergyLevel < maxEnergyLevel) {
            currentEnergyLevel++;
        }
    }
}
