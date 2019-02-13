package gitlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;
/**
 * sdsd.
 * @author zhiji
 */
public class Repo extends Utils implements Serializable {

    /**ssss.
     *@param init
     * sss.
     */
    public Repo(Commit init) {
        this._cbName = "master";
    }

    /**
     *
     * @param id
     * isidisid.
     * @return result
     */

    public Commit getCommit(String id) {
        Commit result = _commits.get(id);
        if (result == null) {
            System.out.println("No commit with that id exists.");
            System.exit(0);
        }
        return result;
    }

    /**
     *
     * @param name
     * caca.
     * @param commit
     * nana.
     */


    public void branch(String name, Commit commit) {
        _branches.put(name, commit);
    }


    /**
     *
     * @return
     * sss.
     */



    public String getcb() {
        return _cbName;
    }

    /**
     *
     * @return
     * s...
     */
    public HashMap<String, Commit> getBranches() {
        return _branches;
    }

    /**
     *
     * @return
     * sss.
     */
    public HashMap<String, Commit> getCommits() {
        return _commits;
    }

    /**
     *
     * @param cmt
     * walwala.
     * @return
     * sss.
     */

    public static byte[] serialize(Repo cmt) {
        byte[] result;
        ByteArrayOutputStream s = new ByteArrayOutputStream();
        try {
            ObjectOutputStream out =
                    new ObjectOutputStream(s);
            out.writeObject(cmt);
            out.close();
            result = s.toByteArray();
            return result;
        } catch (IOException excp) {
            throw new Error(excp);
        }
    }

    /**
     *
     * @param file
     * eyayeya.
     * @return
     * sss.
     */
    public static Repo deserialize(File file) {
        Repo cmt;
        try {
            ObjectInputStream inp =
                    new ObjectInputStream(new FileInputStream(file));
            cmt = (Repo) inp.readObject();
            inp.close();
        } catch (IOException | ClassNotFoundException excp) {
            cmt = null;
        }
        return cmt;
    }

    /**
     *
     * @param name
     * skjsk.
     */
    public void switchBranch(String name) {
        this._cbName = name;
    }



    /**sss
    * .*/
    private String _cbName;
    /**sss.
    */
    private HashMap<String, Commit> _branches = new HashMap<String, Commit>();
    /**sss.*/
    private HashMap<String, Commit> _commits = new HashMap<>();




}
