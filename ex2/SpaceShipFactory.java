import oop.ex2.*;

/**
 * This class has a single static method. It is used by the supplied driver to create all the spaceship
 * objects according to the command line arguments.
 */
public class SpaceShipFactory {

    private static final String runner = "r";
    private static final String human = "h";
    private static final String basher = "b";
    private static final String aggressive = "a";
    private static final String drunkard = "d";
    private static final String special = "s";

    /**
     * The function create spaceship objects according to the command line arguments.
     *
     * @param args Program arguments.
     * @return Array filled with spaceships.
     */
    public static SpaceShip[] createSpaceShips(String[] args) {
        SpaceShip[] ships = new SpaceShip[args.length];
        for(int i = 0; i < args.length; i++) {
            switch(args[i]) {
                case runner:
                    ships[i] = new RunnerShip();
                    break;
                case human:
                    ships[i] = new HumanShip();
                    break;
                case basher:
                    ships[i] = new BasherShip();
                    break;
                case aggressive:
                    ships[i] = new AggressiveShip();
                    break;
                case drunkard:
                    ships[i] = new DrunkardShip();
                    break;
                case special:
                    ships[i] = new SpecialShip();
                    break;
            }
        }
        return ships;
    }
}
