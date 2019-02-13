package gitlet;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * An object that reads and interprets a sequence of commands from an
 * input source.
 *
 * @author zhiji
 */

public class Manager {
    /**
     *
     * @param ip
     * input.
     */
    Manager(String[] ip) {
        this.input = ip;
    }
    /**
     * Parse and execute one statement from the token stream.
     *
     * @param cmt
     * ssss.
     */
    private void update(Commit cmt) {
        Repo r = getRepo();
        r.getCommits().put(cmt.getID(), cmt);
        r.getBranches().put(r.getcb(), cmt);
        File head = new File(Path.gitletDir() + "Head.txt");
        Utils.writeContents(head, Commit.serialize(cmt));
        saveRepo(r);
    }
    /**
     * Parse and execute one statement from the token stream.  Return true
     * iff the command is something other than quit or exit.
     */

    private Commit getHead() {
        File head = new File(Path.gitletDir() + "Head.txt");
        return (Commit) Zhiji.deserialize(head);
    }
    /**
     * Parse and execute one statement from the token stream.  Return true
     * iff the command is something other than quit or exit.
     * @return
     * sjkr.
     */

    private Stage getStage() {
        File stage = new File(Path.stageDir() + "stage.txt");
        return Stage.deserialize(stage);
    }
    /**
     *
     * Parse and execute one statement from the token stream.
     * Return true
     * @param stage
     * iff the command is something other than quit or exit.
     */

    private void saveStage(Stage stage) {
        File file = new File(Path.stageDir() + "stage.txt");
        Utils.writeContents(file, Stage.serialize(stage));
    }
    /**
     * Parse and execute one statement from the token stream.  Return true
     * iff the command is something other than quit or exit.
     * @return
     * sss.
     */

    private Repo getRepo() {
        return  (Repo) Zhiji.deserialize(new File(Path.repoDir() + "repo.txt"));
    }
    /**
     * Parse and execute one statement from the token stream.  Return tru
     *
     * @param rep0
     * sss.
     */

    private void saveRepo(Repo rep0) {
        File file = new File(Path.repoDir() + "repo.txt");
        Utils.writeContents(file, Zhiji.serialize(rep0));
    }
    /**
     * Parse and execute one statement from the token stream.  Return true
     * iff the command is something other than quit or exit.
     * @param sa
     * sss.
     */


    private String getMsg(String [] sa) {
        String result = "";
        for (String s : sa) {
            result.concat(s);
            result.concat(" ");
        }
        return result.substring(0, result.length() - 2);
    }

        /**
         * Parse and execute one statement from the token stream.  Return true
         * iff the command is something other than quit or exit.
         */
    void statement() {
        switch (input[0]) {
        case "init":
            initStatement();
            break;
        case "add":
            addStatement(input[1]);
            break;
        case "rm":
            rmStatement(input[1]);
            break;
        case "reset":
            resetStatement(input[1]);
            break;
        case "log":
            logStatement();
            break;
        case "global-log":
            globalLogStatement();
            break;
        case "commit":
            statement2(input);
            break;
        case "find":
            statement3(input);
            break;
        case "status":
            statusStatement();
            break;
        case "checkout":
            lengthCheck(input, 2, 3, 4);
            statement4(input);
            break;
        case "branch":
            branchStatement(input[1]);
            break;
        case "rm-branch":
            rmbranchStatement(input[1]);
            break;
        case "merge":
            lengthCheck(input, 2);
            mergeStatement(input[1]);
            break;
        default:
            extraCredit(input);
        }

    }

    /**
     *
     * @param inpu
     * inoput.
     */

    private void extraCredit(String[] inpu) {
        switch (inpu[0]) {
        case "add-remote":
            lengthCheck(inpu, 3);
            needRepoCheck();
            addremoteStatement(inpu[1], inpu[2]);
            break;
        case "rm-remote":
            lengthCheck(inpu, 2);
            needRepoCheck();
            rmremoteStatement(inpu[1]);
            break;
        case "push":
            lengthCheck(inpu, 3);
            needRepoCheck();
            pushStatement(inpu[1], inpu[2]);
            break;
        case "fetch":
            lengthCheck(inpu, 3);
            needRepoCheck();
            fetchStatement(inpu[1], inpu[2]);
            break;
        case "pull":
            lengthCheck(inpu, 3);
            needRepoCheck();
            pullStatement(inpu[1], inpu[2]);
            break;
        default:
            System.out.println("No command with that name exists.");
        }

    }

    /**
     * ssss.
     * @param inp
     * inps.
     */

    private void statement2(String[] inp) {
        if (inp.length == 1) {
            System.out.println("Incorrect operands.");
            System.exit(0);
        }
        if (inp.length == 2) {
            commitStatement(inp[1]);
        } else {
            String last = inp[inp.length -  1];
            if (inp[1].charAt(0) != '"'
                    || last.charAt(last.length()) != '"') {
                System.out.println("Incorrect operands.");
                System.exit(0);
            } else {
                String[] s = Arrays.copyOfRange(inp, 0, inp.length);
                commitStatement(getMsg(s));
            }
        }
    }

    /**
     * ssss.
     * @param inp
     * inps.
     */

    private void statement3(String[] inp) {
        if (inp.length == 2) {
            findStatement(inp[1]);
        } else {
            String last = inp[inp.length -  1];
            if (inp[1].charAt(0) != '"'
                    || last.charAt(last.length()) != '"') {
                System.out.println("Incorrect operands.");
                System.exit(0);
            } else {
                String[] s = Arrays.copyOfRange(inp, 0, inp.length);
                findStatement(getMsg(s));
            }
        }
    }

    /**
     * ssss.
     * @param inp
     * inps.
     */

    private void statement4(String[] inp) {
        if (inp.length == 2) {
            checkout3(inp[1]);
            System.exit(0);
        }
        if (inp.length == 3) {
            if (inp[1].equals("--")) {
                checkout1(inp[2]);
            } else {
                System.out.println("Incorrect operands.");
            }
        }
        if (inp.length == 4) {
            if (inp[2].equals("--")) {
                checkout2(inp[1], inp[3]);
            } else {
                System.out.println("Incorrect operands.");
            }
        }
    }

    /**
     * sssss.
     */
    private void needRepoCheck() {
        try {
            Utils.plainFilenamesIn(Path.gitletDir()).isEmpty();

        } catch (NullPointerException e) {
            System.out.println("Not in an initialized Gitlet directory.");
            System.exit(0);
        }
    }

    /**
     * ini.
     * @param ip
     * input.
     * @param length
     * length.
     */
    private void lengthCheck(String[] ip, int length) {
        if (ip.length != length) {
            System.out.println("Incorrect operands.");
            System.exit(0);
        }
    }

    /**
     * inpout.
     * @param ip
     * inpout.
     * @param length1
     * inpout.
     * @param length2
     * inpout.
     * @param length3
     * inpout.
     */

    private void lengthCheck(String[] ip, int length1,
                             int length2, int length3) {
        if (ip.length != length1 && ip.length != length2
                && ip.length != length3) {
            System.out.println("Incorrect operands.");
            System.exit(0);
        }
    }


    /**
     * Parse and execute a init statement from the token stream.
     */
    private void initStatement() {
        lengthCheck(input, 1);
        try {
            Utils.plainFilenamesIn(Path.gitletDir()).isEmpty();
            System.out.println("A Gitlet version-control "
                    + "system already exists in the current directory.");
            System.exit(0);

        } catch (NullPointerException e) {
            new File(Path.gitletDir()).mkdir();
            new File(Path.stageDir()).mkdir();
            new File(Path.blobDir()).mkdir();
            new File(Path.commitDir()).mkdir();
            new File(Path.branchDir()).mkdir();
            new File(Path.repoDir()).mkdir();

            File head = new File(Path.gitletDir() + "Head.txt");
            File master = new File(Path.branchDir() + "master");
            ZonedDateTime initTime =
                    ZonedDateTime.parse("1970-01-01T00:00:00Z");
            Commit initC = new Commit("initial commit", initTime
                    , null, null, null, false);
            File initCommit = new File(Path.commitDir() + initC.getID());
            File repo = new File(Path.repoDir() + "repo.txt");
            Repo rep = new Repo(initC);
            rep.getCommits().put(initC.getID(), initC);
            rep.getBranches().put("master", initC);
            Utils.writeContents(repo, Zhiji.serialize(rep));
            Utils.writeContents(head, Zhiji.serialize(initC));
            Utils.writeContents(master, Zhiji.serialize(initC));
            Utils.writeContents(initCommit, Zhiji.serialize(initC));
            update(initC);
            saveRepo(rep);
            HashMap<String, Blob> blobs = new HashMap<>();
            HashMap<String, Blob> rmblobs = new HashMap<>();
            Stage stage = new Stage(blobs, rmblobs);
            File fs = new File(Path.stageDir() + "stage.txt");
            Utils.writeContents(fs, Stage.serialize(stage));
            saveStage(stage);

        }
    }
    /**
     *
     * @param filename
     * filename.
     */

    private void addStatement(String filename) {
        needRepoCheck();
        List<String> files = Utils.plainFilenamesIn(Path.workingDir());
        Stage stage = getStage();
        if (files.contains(filename)) {
            File file = new File(Path.workingDir() + filename);
            Blob blob = new Blob(Utils.readContents(file), filename);
            String sha1 = blob.getID();
            if (stage.getRemoved().containsKey(sha1)) {
                stage.getRemoved().remove(sha1);
            }
            if (tracked(filename)) {
                Commit head = getHead();
                if (head.getBlobs().containsKey(sha1)) {
                    saveStage(stage);
                    return;
                } else {
                    stage.getAdded()
                            .remove(toID(stage.getAdded(), filename));
                }
            }
            stage.getAdded().put(sha1, blob);
            saveStage(stage);
        } else {
            System.out.println("File does not exist.");
        }
    }

    /**
     * ssss.
     * @param s
     * sss.
     * @param filename
     * filename.
     * @return
     * reutnr.
     */

    private boolean containFile(HashMap<String, Blob> s , String filename) {
        if (s == null) {
            return false;
        }
        for (Blob b : s.values()) {
            if (filename.equals(b.getFilename())) {
                return true;
            }
        }
        return false;
    }
    /**
     *
     * @param filename
     * files.
     * @return
     * filename.
     */

    private boolean tracked(String filename) {
        Commit head = getHead();
        if (head.getBlobs() == null) {
            return false;
        }
        for (Blob b : head.getBlobs().values()) {
            if (b.getFilename().equals(filename)) {
                return true;
            }
        }
        return false;
    }
    /**
     *
     * @param filename
     * filename.
     * @return
     * blloa.
     */

    private boolean staged(String filename) {
        Stage stg = getStage();
        for (Blob b : stg.getAdded().values()) {
            if (b.getFilename().equals(filename)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param blobs
     * bleobs.
     * @param filename
     * filnea.
     * @return
     * reunt.
     */
    private String toID(HashMap<String, Blob> blobs, String filename) {
        if (blobs == null) {
            return null;
        }
        for (String s : blobs.keySet()) {
            if (blobs.get(s).getFilename().equals(filename)) {
                return s;
            }
        }
        return null;
    }

    /**
     *
     * @param filename
     * filename.
     */

    private void rmStatement(String filename) {
        lengthCheck(input, 2);
        needRepoCheck();
        Commit head = getHead();
        Stage stage = getStage();
        if (tracked(filename)) {
            if (staged(filename)) {
                String sha1 = toID(stage.getAdded(), filename);
                stage.getAdded().remove(sha1);
                Blob b = stage.getAdded().get(sha1);
                stage.getRemoved().put(sha1, b);
            } else {
                String sha1 = toID(head.getBlobs(), filename);
                Blob b = head.getBlobs().get(sha1);
                stage.getRemoved().put(sha1, b);
            }
            List<String> files = Utils.plainFilenamesIn(Path.workingDir());
            if (files.contains(filename)) {
                Utils.restrictedDelete(filename);
            }
        } else {
            if (staged(filename)) {
                String sha1 = toID(stage.getAdded(), filename);
                stage.getAdded().remove(sha1);

            } else {
                System.out.println("No reason to remove the file");
                System.exit(0);
            }
        }

        saveStage(stage);


    }
    /**
     * @param message
     * Parse and execute a init statement from the token stream.
     */
    private void commitStatement(String message) {
        needRepoCheck();
        Stage stage = getStage();
        if (stage.getAdded().isEmpty() && stage.getRemoved().isEmpty()) {
            System.out.println(" No changes added to the commit.");
            System.exit(0);
        } else if (message.equals("\"\"")) {
            System.out.println("Please enter a commit message.");
            System.exit(0);
        } else {
            String msg = message.substring(0, message.length());
            ZonedDateTime timeStamp = ZonedDateTime.now();
            Commit parent = getHead();
            HashMap<String, Blob> newBlobs = copy(stage.getAdded());
            if (parent.getBlobs() != null) {

                for (String sha1 : parent.getBlobs().keySet()) {
                    String filename = parent.getBlobs()
                            .get(sha1).getFilename();
                    if (!stage.getRemoved().containsKey(sha1)
                            && !containFile(newBlobs, filename)) {
                        newBlobs.put(sha1, parent.getBlobs().get(sha1));
                    }

                }
            }

            Commit curCommit = new Commit(msg, timeStamp, parent,
                    null, newBlobs, false);
            File commitFile = new File(Path.commitDir()
                    + curCommit.getID());
            Utils.writeContents(commitFile, Commit.serialize(curCommit));
            stage.getRemoved().clear();
            stage.getAdded().clear();
            saveStage(stage);
            update(curCommit);
        }

    }

    /**
     *
     * @param s
     * sss.
     * @return
     * return.
     */

    private HashMap<String, Blob> copy(HashMap<String, Blob> s) {
        if (s == null) {
            return null;
        }
        HashMap<String, Blob> d  = new HashMap<>();
        for (String sha1 : s.keySet()) {
            d.put(sha1, s.get(sha1));
        }
        return d;
    }


    /**
     * Parse and execute a log statement from the token stream.
     */
    private void logStatement() {
        lengthCheck(input, 1);
        needRepoCheck();
        Commit cmt = getHead();
        while (cmt != null) {
            System.out.println("===");
            System.out.println("commit " + cmt.getID());
            if (cmt.isMergeCommit()) {
                String sha11 = cmt.getParent().getID().substring(0, 7);
                String sha12 = cmt.getParent2().getID().substring(0, 7);
                System.out.println("Merge: " + sha11 + " " + sha12);
            }
            DateTimeFormatter f = DateTimeFormatter
                    .ofPattern("EEE MMM dd HH:mm:ss YYYY Z");
            System.out.println("Date: " + cmt.getDate().format(f));
            System.out.println(cmt.getMessage());
            System.out.println();
            cmt = cmt.getParent();
        }
    }
    /**
     * Parse and execute a global-log statement from the token stream.
     */
    private void globalLogStatement() {
        lengthCheck(input, 1);
        needRepoCheck();
        Repo r = getRepo();
        HashMap<String, Commit> checked  = new HashMap<>();
        for (Commit cmt : r.getCommits().values()) {
            System.out.println("===");
            System.out.println("commit " + cmt.getID());
            if (cmt.isMergeCommit()) {
                String sha11 = cmt.getParent().getID().substring(0, 7);
                String sha12 = cmt.getParent2().getID().substring(0, 7);
                System.out.println("Merge: " + sha11 + " " + sha12);
            }
            DateTimeFormatter f = DateTimeFormatter
                    .ofPattern("EEE MMM dd HH:mm:ss YYYY Z");
            System.out.println("Date: " + cmt.getDate().format(f));
            System.out.println(cmt.getMessage());
            System.out.println();
        }
    }
    /**
     * Parse and execute a init statement from the token stream.
     * @param msg
     * sss.
     */
    private void findStatement(String msg) {
        lengthCheck(input, 2);
        needRepoCheck();
        Repo r = getRepo();
        boolean find = false;
        for (Commit cmt : r.getCommits().values()) {
            if (cmt.getMessage().equals(msg)) {
                System.out.println(cmt.getID());
                find = true;
            }
        }
        if (!find) {
            System.out.println("Found no commit with that message.");
        }
    }

    /**
     * sksks.
     */

    private  void statusStatement() {
        lengthCheck(input, 1);
        needRepoCheck();
        Commit head = getHead();
        Repo r = getRepo();
        Stack<String> helper = new Stack<>();
        System.out.println("=== Branches ===");
        for (String name : r.getBranches().keySet()) {
            helper.push(name);
        }
        while (!helper.empty()) {
            String name = helper.pop();
            if (name.equals(r.getcb())) {
                System.out.println("*" + name);
            } else {
                System.out.println(name);
            }
        }
        System.out.println();
        System.out.println("=== Staged Files ===");
        Stage stage = getStage();
        for (Blob b : stage.getAdded().values()) {
            System.out.println(b.getFilename());
        }
        System.out.println();
        System.out.println("=== Removed Files ===");
        for (Blob b :stage.getRemoved().values()) {
            System.out.println(b.getFilename());
        }
        System.out.println();
        System.out.println("=== Modifications Not Staged For Commit ===");
        annoyStatement();
        System.out.println("=== Untracked Files ===");
        List<String> files = Utils.plainFilenamesIn(Path.workingDir());
        for (String filename : files) {
            if (!staged(filename) && !tracked(filename)) {
                System.out.println(filename);
            }
        }
        System.out.println();
    }

    /**
     * sksks.
     */

    private void annoyStatement() {
        Commit head = getHead();
        Repo r = getRepo();
        Stage stage = getStage();
        List<String> files = Utils.plainFilenamesIn(Path.workingDir());
        for (String filename : files) {
            Blob blob = new Blob(Utils.readContents(new File(
                    Path.workingDir() + filename)), filename);
            if (tracked(filename) && !staged(filename)
                    && !head.getBlobs()
                    .containsKey(blob.getID())) {
                System.out.println(filename + " (modified)");
            }
        }
        for (Blob b : stage.getAdded().values()) {
            byte[] cont = Utils.readContents(
                    new File(Path.workingDir()
                            + b.getFilename()));
            if (!files.contains(b.getFilename())) {
                System.out.println(b.getFilename() + " (deleted)");
            } else if (!Arrays.equals(cont, b.getContents())) {
                System.out.println(b.getFilename() + " (modified)");
            }
        }
        if (head.getBlobs() != null) {
            for (Blob b : head.getBlobs().values()) {
                if (!files.contains(b.getFilename())
                        && !containFile(stage.getRemoved()
                        , b.getFilename())) {
                    System.out.println(b.getFilename() + " (deleted)");
                }
            }
        }
        System.out.println();
    }

    /**
     *
     * @param name
     * sss.
     */
    private void branchStatement(String name) {
        lengthCheck(input, 2);
        needRepoCheck();
        Repo r = getRepo();
        if (r.getBranches().keySet().contains(name)) {
            System.out.println(" A branch with that name already exists.");
        }
        File branch = new File(Path.branchDir() + name);
        Utils.writeContents(branch, Commit.serialize(getHead()));
        r.branch(name, getHead());
        saveRepo(r);
    }

    /**
     *
     * @param name
     * name.
     */

    private void rmbranchStatement(String name) {
        lengthCheck(input, 2);
        needRepoCheck();
        Repo r = getRepo();
        Commit head = getHead();
        Commit branch = r.getBranches().get(name);
        if (branch == null) {
            System.out.println("A branch with that name does not exist.");
            System.exit(0);
        }
        if (head.getID().equals(branch.getID())) {
            System.out.println("Cannot remove the current branch.");
            System.exit(0);
        } else {
            r.getBranches().remove(name);
            saveRepo(r);
        }

    }


    /**
     * java gitlet.Main checkout -- [file name].
     * @param filename
     * ssss.
     */

    private void checkout1(String filename) {
        Commit head = getHead();
        boolean find = false;
        File curFile = new File(Path.workingDir() + filename);
        for (Blob b : head.getBlobs().values()) {
            if (b.getFilename().equals(filename)) {
                find = true;
                Utils.writeContents(curFile, b.getContents());
            }
        }

        if (!find) {
            System.out.println("File does not exist in that commit.");
        }
    }

    /**
     *
     * @param id
     * sjks.
     * @param filename
     * yjyj.
     */
    private void checkout2(String id, String filename) {
        Repo r = getRepo();
        Commit head = getHead();
        Commit cmt = head;
        Commit target = null;
        boolean findname = false;
        while (cmt != null) {
            if (cmt.getID().substring(0, 7).equals(id.substring(0, 7))) {
                target = cmt;
                break;
            }
            cmt = cmt.getParent();
        }

        if (target == null) {
            System.out.println("No commit with that id exists.");
            System.exit(0);
        }



        File curFile = new File(Path.workingDir() + filename);
        for (Blob b : target.getBlobs().values()) {
            if (b.getFilename().equals(filename)) {
                findname = true;
                Utils.writeContents(curFile, b.getContents());
                break;
            }
        }


        if (!findname) {
            System.out.println("File does not exist in that commit.");
            System.exit(0);
        }
    }

    /**
     *java gitlet.Main checkout [branch name].
     * @param branchname
     * ssss.
     */
    private void checkout3(String branchname) {
        Repo r = getRepo();
        Stage stg = getStage();
        stg.getRemoved().clear();
        stg.getAdded().clear();
        saveStage(stg);
        Commit branch = r.getBranches().get(branchname);
        if (branch == null) {
            System.out.println("No such branch exists.");
            System.exit(0);
        }
        List<String> files = Utils.plainFilenamesIn(Path.workingDir());
        for (String filename : files) {
            if (!tracked(filename) && containFile(branch, filename)) {
                System.out.println("There is an untracked file in the way;"
                        + " delete it or add it first.");
                System.exit(0);
            }
        }
        Commit head = getHead();
        if (r.getcb().equals(branchname)) {
            System.out.println("No need to checkout the current branch.");
        } else {
            if (head.getBlobs() != null) {
                for (Blob b : head.getBlobs().values()) {
                    String filename = b.getFilename();
                    if (!containFile(branch.getBlobs(), filename)) {
                        File file = new File(Path.workingDir()
                                + filename);
                        Utils.restrictedDelete(file);
                    }
                }
            }
            if (branch.getBlobs() != null) {
                for (Blob b : branch.getBlobs().values()) {
                    File file = new File(Path.workingDir()
                            + b.getFilename());
                    Utils.writeContents(file, b.getContents());
                }
            }

            r.switchBranch(branchname);
            saveRepo(r);
            update(branch);
        }
    }
    /**
     *java gitlet.Main checkout [branch name].
     * @param c1
     * ksks.
     * @param c2
     * 'sjaysr.
     * @return sss.
     */


    private Commit findSplit(Commit c1, Commit c2) {

        while (c1 != null) {
            Commit cmt = c2;
            while (cmt != null) {
                if (c1.getID().equals(cmt.getID())) {
                    return c1;
                }
                cmt = cmt.getParent();
            }
            c1 = c1.getParent();
        }
        return c1;
    }
    /**
     *java gitlet.Main checkout [branch name].
     * @param cmt
     * scscs.
     * @param filename
     * uyor.
     * @return sss.
     */

    private boolean containFile(Commit cmt, String filename) {
        if (cmt.getBlobs() == null) {
            return false;
        }
        for (Blob b : cmt.getBlobs().values()) {
            if (b.getFilename().equals(filename)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param n
     * nme.
     */
    private void checkTracked(String n) {
        Commit head = getHead();
        Repo r = getRepo();
        Commit branch = r.getBranches().get(n);
        List<String> files = Utils.plainFilenamesIn(Path.workingDir());
        for (String filename : files) {
            if (!tracked(filename) && containFile(branch, filename)) {
                System.out.println(
                        "There is an untracked file in the way;"
                                + " delete it or add it first.");
                System.exit(0);
            }
        }
    }

    /**
     *
     * @param head
     *
     * jkasasdw.
     * @param branch
     * kjasnd.
     * @param split
     * akjsnd.
     * @param conflictBlobs
     * asljknda.
     * @param newBlobs
     * aksjnd.
     * @param conflict
     * asljknd.
     * @return
     * aksjd.
     */

    private boolean goThroughHead(Commit head, Commit branch
            , Commit split, HashMap<String, Blob> conflictBlobs
            , HashMap<String, Blob> newBlobs, boolean conflict) {
        HashMap<String, Blob> hb = head.getBlobs();
        HashMap<String, Blob> bb = branch.getBlobs();
        HashMap<String, Blob> sb = split.getBlobs();
        for (String sha1 : hb.keySet()) {
            Blob b = hb.get(sha1);
            String filename = b.getFilename();
            if (containFile(branch, filename)) {
                if (containFile(split, filename)) {
                    if (bb.containsKey(sha1)) {
                        newBlobs.put(sha1, b);
                    } else {
                        String id = toID(bb, filename);
                        if (sb != null && sb.containsKey(sha1)) {
                            newBlobs.put(id, bb.get(id));
                        } else {
                            if (sb != null && sb.containsKey(id)) {
                                newBlobs.put(sha1, b);
                            } else {
                                Blob conf =
                                        mergeConflict(b, bb.get(id), filename);
                                conflictBlobs.put(filename, conf);
                                conflict = true;
                            }
                        }
                    }
                } else {
                    if (bb.containsKey(sha1)) {
                        newBlobs.put(sha1, b);
                    } else {
                        Blob conf = mergeConflict(b, bb.get(toID(bb, filename)),
                                filename);
                        conflictBlobs.put(filename, conf);
                        conflict = true;
                    }
                }
            } else {
                if (containFile(split, filename)) {
                    if (sb == null || !sb.containsKey(sha1)) {
                        Blob conf = mergeConflict(b, null, filename);
                        conflictBlobs.put(filename, conf);
                        conflict = true;
                    }
                } else {
                    newBlobs.put(sha1, b);
                }
            }
        }
        return conflict;
    }
    /**
     *
     * @param head
     *
     * jkasasdw.
     * @param branch
     * kjasnd.
     * @param split
     * akjsnd.
     * @param conflictBlobs
     * asljknda.
     * @param newBlobs
     * aksjnd.
     * @param conflict
     * asljknd.
     * @return
     * aksjd.
     */
    private boolean goThroughBranch(Commit head, Commit branch
            , Commit split, HashMap<String, Blob> conflictBlobs
            , HashMap<String, Blob> newBlobs, boolean conflict) {
        HashMap<String, Blob> hb = head.getBlobs();
        HashMap<String, Blob> bb = branch.getBlobs();
        HashMap<String, Blob> sb = split.getBlobs();
        for (String sha1 : bb.keySet()) {
            Blob b = bb.get(sha1);
            String filename = b.getFilename();
            if (!containFile(head, filename)) {
                if (containFile(split, filename)) {
                    if (sb == null || !sb.keySet().contains(sha1)) {
                        Blob conf = mergeConflict(null, b, filename);
                        conflictBlobs.put(filename, conf);
                        conflict = true;
                    }
                } else {
                    newBlobs.put(sha1, b);
                }
            }
        }
        return conflict;
    }
    /**
     *
     * @param head
     *
     * jkasasdw.
     * @param branch
     * kjasnd.
     * @param split
     * akjsnd.
     * @param conflictBlobs
     * asljknda.
     * @param newBlobs
     * aksjnd.
     * @param name
     * lkamsd.
     * @param conflict
     * asljknd.
     */
    private void dealFile(Commit head, Commit branch
            , Commit split, HashMap<String, Blob> conflictBlobs
            , HashMap<String, Blob> newBlobs, String name, boolean conflict) {
        Repo r = getRepo();
        List<String> files = Utils.plainFilenamesIn(Path.workingDir());
        for (String filename : files) {
            if (!containFile(newBlobs, filename)
                    && !conflictBlobs.containsKey(filename)) {
                Utils.restrictedDelete(new File(Path.workingDir()
                        + filename));
            }
        }
        for (Blob b : newBlobs.values()) {
            Utils.writeContents(new File(Path.workingDir()
                    + b.getFilename()), b.getContents());
        }
        for (Blob b : conflictBlobs.values()) {
            newBlobs.put(b.getID(), b);
        }

        Commit cmt = new Commit("Merged " + name + " into "
                + r.getcb() + ".",
                ZonedDateTime.now(), head, branch, newBlobs, true);
        update(cmt);
        if (conflict) {
            System.out.println("Encountered a merge conflict.");
            System.exit(0);
        }
    }

    /**
     *java gitlet.Main checkout [branch name].
     * @param name
     * sss.
     * sss.
     *
     */

    private void mergeStatement(String name) {
        needRepoCheck();
        List<String> files = Utils.plainFilenamesIn(Path.workingDir());
        Stage stage = getStage();
        Repo r = getRepo();
        Commit head = getHead();
        Commit branch = r.getBranches().get(name);
        if (stage.getAdded().isEmpty() && stage.getRemoved().isEmpty()) {
            if (r.getBranches().keySet().contains(name)) {
                if (head.getID().equals(branch.getID())) {
                    System.out.println("Cannot merge a branch with itself.");
                    System.exit(0);
                }
                Commit split = findSplit(head, branch);
                if (split.getID().equals(branch.getID())) {
                    System.out.println("Given branch is an "
                            + "ancestor of the current branch.");
                    System.exit(0);
                } else if (split.getID().equals(head.getID())) {
                    System.out.println("Current branch fast-forwarded.");
                    System.exit(0);
                }
                checkTracked(name);
                HashMap<String, Blob> newBlobs = new HashMap<>();
                HashMap<String, Blob> conflictBlobs = new HashMap<>();
                for (String filename : files) {
                    if (!containFile(head, filename)
                            && !containFile(branch, filename)
                            && !containFile(split, filename)) {
                        Blob blob = new Blob(Utils.readContents(
                                new File(Path.workingDir() + filename)),
                                filename);
                        newBlobs.put(blob.getID(), blob);
                    }
                }
                if (branch.getBlobs() == null) {
                    System.exit(0);
                }
                if (head.getBlobs() == null) {
                    checkout3(name);
                    System.exit(0);
                }
                boolean conflict =
                        goThroughHead(head, branch, split, conflictBlobs
                                , newBlobs, false);
                conflict = goThroughBranch(head, branch, split, conflictBlobs
                        , newBlobs,  conflict);
                dealFile(head, branch, split, conflictBlobs,
                        newBlobs, name, conflict);
            } else {
                System.out.println("A branch with that name does not exist.");
            }
        } else {
            System.out.println("You have uncommitted changes.");
        }
    }
    /**
     * @param b1
     * yayaya.
     * @param b2
     * wawsa.
     * @param filename
     * filename.
     * @return
     * ianc.
     */
    private Blob mergeConflict(Blob b1, Blob b2, String filename) {
        String content1 = "";
        String content2 = "";
        if (b1 != null) {
            content1 = new String(b1.getContents());
        }
        if (b2 != null) {
            content2 = new String(b2.getContents());
        }
        String info = "<<<<<<< HEAD\n" + content1
                + "=======\n" + content2 + ">>>>>>>\n";
        File file = new File(Path.workingDir() + filename);
        Utils.writeContents(file, info.getBytes());
        return new Blob(info.getBytes(), filename);

    }

    /**
     *
     * @param id
     * id.
     */

    private void resetStatement(String id) {
        lengthCheck(input, 2);
        Stage stg = getStage();
        stg.getAdded().clear();
        stg.getRemoved().clear();
        saveStage(stg);
        Repo r = getRepo();
        Commit head = getHead();
        Commit cmt = r.getCommit(id);
        List<String> files = Utils.plainFilenamesIn(Path.workingDir());
        for (String filename : files) {
            if (!tracked(filename) && containFile(cmt, filename)) {
                System.out.println("There is an untracked file in the way;"
                        + " delete it or add it first.");
                System.exit(0);
            }
        }

        if (head.getBlobs() != null) {
            for (Blob b : head.getBlobs().values()) {
                String filename = b.getFilename();
                if (!containFile(cmt.getBlobs(), filename)) {
                    File file = new File(Path.workingDir()
                            + filename);
                    Utils.restrictedDelete(file);
                }
            }
        }
        if (cmt.getBlobs() != null) {
            for (Blob b : cmt.getBlobs().values()) {
                File file = new File(Path.workingDir()
                        + b.getFilename());
                Utils.writeContents(file, b.getContents());
            }
        }


        update(cmt);
    }

    /**
     *
     * @return
     * reutnr.
     */

    private Remote getRemote() {
        if (!Utils.plainFilenamesIn(Path.gitletDir()).contains("remote.txt")) {
            System.out.println("Remote directory not found.");
            System.exit(0);
            return null;
        } else {
            File remote = new File(Path.gitletDir() + "remote.txt");
            return (Remote) Zhiji.deserialize(remote);
        }
    }

    /**
     *
     * @param re
     * rere.
     */

    private void updateRemote(Remote re) {
        File remote = new File(Path.gitletDir() + "remote.txt");
        Utils.writeContents(remote, Zhiji.serialize(re));
    }


    /**
     *
     * @param remoteName
     * sss.
     * @param remotePath
     * sss.
     */
    private void addremoteStatement(String remoteName, String remotePath) {
        if (!Utils.plainFilenamesIn(Path.gitletDir()).contains("remote.txt")) {
            HashMap<String, String> hash1 = new HashMap<>();
            hash1.put(remoteName, remotePath);
            Remote re = new Remote(hash1);
            updateRemote(re);
        } else {
            Remote re = getRemote();
            if (re.getRemotes().containsKey(remoteName)) {
                System.out.println("A remote with that name already exists.");
            } else {
                re.getRemotes().put(remoteName, remotePath);
                updateRemote(re);
            }

        }
    }
    /**
     *
     * @param remoteName
     * sss.
     * @param branchName
     * sss.
     */
    private void pushStatement(String remoteName, String branchName) {


        Remote re = getRemote();
        if (!re.getRemotes().containsKey(remoteName)) {
            System.out.println("Remote directory not found.");
            System.exit(0);
        }
        String path = re.getRemotes().get(remoteName);
        List<String> files = Utils.plainFilenamesIn(path);
        if (files == null
                || !files.contains("Head.txt")) {
            System.out.println("Remote directory not found.");
            System.exit(0);
        }
        Repo r = getRepo();
        Commit head = getHead();
        if (!re.getRepo(remoteName).getBranches().containsKey(branchName)) {
            re.getRepo(remoteName).getBranches().put(branchName, head);
            updateRemote(re);
            System.exit(0);
        }
        Commit remoteBranch =
                re.getRepo(remoteName).getBranches().get(branchName);

        Commit cmt = head;
        boolean find = false;
        while (cmt != null) {
            if (cmt.getID().equals(remoteBranch.getID())) {
                find = true;
                break;
            }
            cmt = cmt.getParent();
        }
        if (!find) {
            System.out.println(
                    "Please pull down remote changes before pushing.");
        } else {
            Commit c = new Commit(head.getMessage(), head.getDate()
                    , remoteBranch, head.getParent2()
                    , head.getBlobs(), head.isMergeCommit());
            re.getRepo(remoteName).getBranches().put(branchName, c);
            re.getRepo(remoteName).getCommits().put(c.getID(), c);
            re.getRepo(remoteName).switchBranch(branchName);
            re.update(remoteName, c, re.getRepo(remoteName));
            updateRemote(re);
        }
    }
    /**
     *
     * @param remoteName
     * sss.
     */
    private void rmremoteStatement(String remoteName) {
        Remote re = getRemote();
        if (re.getRemotes() == null
                || !re.getRemotes().containsKey(remoteName)) {
            System.out.println("A remote with that name does not exist.");
        } else {
            re.getRemotes().remove(remoteName);
            updateRemote(re);
        }
    }
    /**
     *
     * @param remoteName
     * sss.
     * @param branchName
     * sss.
     */
    private void fetchStatement(String remoteName, String branchName) {
        Remote re = getRemote();
        String path = re.getRemotes().get(remoteName);
        List<String> files = Utils.plainFilenamesIn(path);
        if (files == null
                || !files.contains("Head.txt")) {
            System.out.println("Remote directory not found.");
            System.exit(0);
        }
        Repo rr = re.getRepo(remoteName);
        Repo r = getRepo();
        if (rr == null
                || !rr.getBranches().containsKey(branchName)) {
            System.out.println("That remote does not have that branch");
        } else {
            String name = remoteName + "/" + branchName;
            Commit remoteHead = rr.getBranches().get(branchName);
            r.getBranches().put(name, remoteHead);
            r.getCommits().put(remoteHead.getID(), remoteHead);
            File head = new File(Path.gitletDir() + "Head.txt");
            Utils.writeContents(head, Commit.serialize(remoteHead));
            r.switchBranch(branchName);
            saveRepo(r);
        }
    }
    /**
     *
     * @param remoteName
     * sss.
     * @param branchName
     * sss.
     */
    private void pullStatement(String remoteName, String branchName) {
        Remote re = getRemote();
        String path = re.getRemotes().get(remoteName);
        List<String> files = Utils.plainFilenamesIn(path);
        if (files == null
                || !files.contains("Head.txt")) {
            System.out.println("Remote directory not found.");
            System.exit(0);
        }
        Repo rr = re.getRepo(remoteName);
        Repo r = getRepo();
        if (rr == null
                || !rr.getBranches().containsKey(branchName)) {
            System.out.println("That remote does not have that branch");
        } else {
            String name = remoteName + "/" + branchName;
            Commit remoteHead = rr.getBranches().get(branchName);
            r.getBranches().put(name, remoteHead);
            r.getCommits().put(remoteHead.getID(), remoteHead);
            r.switchBranch(branchName);
            saveRepo(r);
            mergeStatement(name);
        }
    }



    /**
     * Database containing all tables.
     */
    private String[] input;
}
