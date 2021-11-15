import java.util.Random;

/**
 * Drunk ship, controlled by the computer. It's behaviour is unpredictable, it can fire and move unexpectedly.
 */
public class DrunkardShip extends SpaceShip {
    /* Constant Variables */
    private static final int fire = 0;
    private static final int direction = 1;
    private static final int constantFire = 5;
    private static final int fireProbability = 50;
    private static final int directionProbability = 2;

    @Override
    public void doAction(SpaceWars game) {
        int[] rand = new int[2];
        Random temp = new Random();
        rand[fire] = temp.nextInt(fireProbability); // get a number from 0 to 9
        rand[direction] = temp.nextInt(directionProbability); // get a number from 0 to 1
        if(rand[fire] == constantFire) {
            fire(game);
        }
        switch(rand[direction]) {
            case 0:
                this.getPhysics().move(false, 1);
                break;
            case 1:
                this.getPhysics().move(true, -1);
                break;
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
