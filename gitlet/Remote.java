package gitlet;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;

/**
 * @author zhiji
 */
public class Remote implements Serializable {
    /**
     *
     * @param re
     * erere.
     */
    public Remote(HashMap<String, String> re) {
        this.remotes = re;
    }

    /**
     *
     * @return
     * reutnr.
     */

    public HashMap<String, String> getRemotes() {
        return this.remotes;
    }

    /**
     *
     * @param name
     * sss.
     * @return
     * sss.
     */
    public Repo getRepo(String name) {
        String path = this.remotes.get(name);
        File repo = new File(path + "/repoDir/repo.txt");
        return (Repo) Zhiji.deserialize(repo);
    }

    /**
     *
     * @param name
     * sss.
     * @return
     * sss.
     */
    public Commit getHead(String name) {
        String path = this.remotes.get(name);
        File repo = new File(path + "/Head.txt");
        return (Commit) Zhiji.deserialize(repo);
    }

    /**
     *
     * @param name
     * name.
     * @param h
     * namw.
     * @param r
     * rrrr.
     */

    public void update(String name, Commit h, Repo r) {
        String path = this.remotes.get(name);
        File head = new File(path + "/Head.txt");
        Utils.writeContents(head, Zhiji.serialize(h));
        File repo = new File(path + "/repoDir/repo.txt");
        Utils.writeContents(repo, Zhiji.serialize(r));
    }




    /**
     *
     */

    private HashMap<String, String> remotes = new HashMap<>();
    /**
     *
     */
}
