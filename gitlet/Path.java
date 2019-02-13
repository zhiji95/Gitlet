package gitlet;

/**kjndsjnjdsnc.
 * oath class
 * @author zhiji
 */
public class Path {
    /**
     *
     * @return ss
     */
    public static String workingDir() {
        return workingDir;
    }
    /**
     *
     * @return ss
     */
    public static String gitletDir() {
        return gitletDir;
    }
    /**
     *
     * @return ss
     */
    public static String blobDir() {
        return blobDir;
    }
    /**
     *
     * @return ss
     */
    public static String branchDir() {
        return branchDir;
    }
    /**
     *
     * @return ss
     */
    public static String stageDir() {
        return stageDir;
    }
    /**
     *
     * @return ss
     */
    public static String repoDir() {
        return repoDir;
    }
    /**
     *
     * @return ss
     */
    public static String commitDir() {
        return commitDir;
    }

    /**oath class.*/
    private static String workingDir = System.getProperty("user.dir") + "/";
    /**oath class.*/
    private static String gitletDir = workingDir  + ".gitlet/";
    /**oath class.*/
    private static String blobDir = gitletDir  + "blobs/";
    /**oath class.*/
    private static String branchDir = gitletDir  + "branchDir/";
    /**oath class.*/
    private static String stageDir = gitletDir  + "stageDir/";
    /**oath class.*/
    private static String repoDir = gitletDir  + "repoDir/";
    /**oath class.*/
    private static String commitDir = gitletDir  + "commitDir/";
}
