package gitlet;


/** The main program for Qirkat.
 *  @author P. N. Hilfinger */
public class Main {


    /**
     * args.
     * @param args
     * args.
     */
    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Please enter a command.");
            System.exit(0);
        }

        Manager m = new Manager(args);
        m.statement();
    }

}
