/**
 * A special ship that regenerates energy once it hits another ship.
 * Its behavior is: It begins the game in a dormant stage, spinning around. Once a ship gets close enough to
 * it, it will start shooting at it and if a ship gets too close to it, it will activate its shields.
 * After a while, it will activate itself fully, repair itself and teleport away. It'll start hunting down ships close
 * to it and if it collides with another ship it will teleport away. If it runs out of energy (not enough energy to tp,
 * it'll go in a regeneration mode, running away from all ships to replenish its energy. If the ship's destroyed, it
 * will once again start in dormant stage.
 */
public class SpecialShip extends SpaceShip {
    /*
                                        The time until the ship will no longer be dormant.
                                         */
    private static final int dormantTimer = 500;
    private static final double minDistanceAccelerate = 0.3d;
    private static final double minTeleportDistance = 0.1d;
    private static final double minShootActive = 0.19d;
    private static final int minEnergy = 159;
    private static final double minShootAngle = 0.21d;
    private static final double minShootDormant = 0.35d;
    private static final double minShieldDormant = 0.12d;
    private static final int maxEnergyActive = 500;
    private static final int curEnergyActive = 400;

    /*
    Ship is dormant at the start of the game and after a while is no longer dormant (unless destroyed)
     */
    private boolean dormant = true;
    /*
    Ship will regenerate if it doesn't have enough energy to teleport.
     */
    private boolean regenerating = false;
    /*
    The ship's timer
     */
    private int timer = 0;

    /**
     * Redefines the ship's behavior to match the special ship.
     * @param game The game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {
        if(dormant) {
            dormantStage(game);
        }
        else {
            activeStage(game);
        }
    }

    /**
     * Redefines the reset method to match the special ship API.
     */
    @Override
    public void reset() {
        super.reset();
        dormant = true;
        timer = 0;
        regenerating = false;
    }

    /*
    Responsible for behavior once the ship is active
     */
    private void activeStage(SpaceWars game) {
        SpaceShip closest = game.getClosestShipTo(this);
        double angle = this.getPhysics().angleTo(closest.getPhysics());
        double distanceFromClosest = this.getPhysics().distanceFrom(closest.getPhysics());
        int move_angle;
        if (angle < 0) {
            move_angle = -1;
        }
        else {
            move_angle = 1;
        }
        move_angle = handleRegenAngle(move_angle);
        // accelerate only if distance is bigger than 0.3 to closest ship or regenerating
        this.getPhysics().move(!(distanceFromClosest < minDistanceAccelerate) || regenerating, move_angle);
        if(Math.abs(angle) < minShootActive && currentEnergyLevel >= minEnergy) {
            fire(game);
        }
        if(!regenerating && distanceFromClosest <= minTeleportDistance) {
            teleport();
        }
        if(regenerating) {
            if(currentEnergyLevel == maxEnergyLevel) {
                regenerating = currentEnergyLevel < minEnergy;
                timer = 0;
            }
        }
        else {
            regenerating = currentEnergyLevel < minEnergy;
        }
        roundCheckList();
    }
    /*
    Checks if the ship is regenerating and calculates the appropriate angle to go
     */
    private int handleRegenAngle(int angle) {
        if(regenerating) {
            switch(angle) {
                case 1:
                    angle = -1;
                    break;
                case -1:
                    angle = 1;
                    break;
            }
        }
        return angle;
    }
    /*
    Responsible for the behavior of the ship while dormant
     */
    private void dormantStage(SpaceWars game) {
        SpaceShip closest = game.getClosestShipTo(this);
        double distanceFromClosest = closest.getPhysics().distanceFrom(this.getPhysics());
        double angle = this.getPhysics().angleTo(closest.getPhysics());
        if (Math.abs(angle) < minShootAngle && distanceFromClosest < minShootDormant) {
            fire(game);
        }
        if(distanceFromClosest <= minShieldDormant) {
            shieldOn();
        }
        this.getPhysics().move(false, 1);
        roundCheckList();
        if(timer >= dormantTimer) {
            reset();
            dormant = false;
            maxEnergyLevel = maxEnergyActive;
            currentEnergyLevel = curEnergyActive;
        }
    }

    /*
     * Overrides the end of the round check list (adds the timer)
     */
    private void roundCheckList() {
        if(currentEnergyLevel < maxEnergyLevel) {
            currentEnergyLevel++;
        }
        if(hasFired) {
            roundCount++;
            if(roundCount >= fireCooldown) {
                roundCount = 0;
                hasFired = false;
            }
        }
        /*
        Check if the timer should be running
         */
        if(dormant) {
            timer++;
        }
    }
}
