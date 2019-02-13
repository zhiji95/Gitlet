package gitlet;

import ucb.junit.textui;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


/** The suite of all JUnit tests for the gitlet package.
 *  @author zhiji
 */
public class UnitTest {



    /** Run the JUnit tests in the loa package. Add xxxTest.class entries to
     *  the arguments of runClasses to run other JUnit tests. */
    public static void main(String[] ignored) {
        textui.runClasses(UnitTest.class);
    }

    String[] init = {"git", "init"};
    String[] add = {"git", "add", "file1"};
    String[] commit = {"git", "commit", "-a", "-m", "commit1"};
    Manager m = new Manager(init);

    @Test
    public void initTest() {
        String[] ini = {"git", "init"};
        Manager mm = new Manager(ini);

    }




    /** A dummy test to avoid complaint. */
    @Test
    public void addTest() {
        DateTimeFormatter f = DateTimeFormatter
                .ofPattern("EEE MMM dd HH:mm:ss YYYY Z");
        System.out.println(ZonedDateTime.now().format(f));
    }


    @Test
    public void commitTest() {
        Manager mm = new Manager(commit);
    }


    @Test
    public void placeholderTest() {
    }

}


