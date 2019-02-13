package gitlet;
import java.io.Serializable;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * The commit class.
 * @author zhiji
 */
public class Commit extends Utils implements Map<String, Blob>, Serializable {

    /** the parent. */
    private Commit parent;
    /** wayayaya. */
    private Commit parent2;
    /** the message. */
    private String message;
    /** the date. */
    private ZonedDateTime date;
    /** The hash map of filename-sha1. */
    /** The hash map of blob. */
    private HashMap<String, Blob> blobs;

    /** The hash map of blob. */
    private boolean isMergeCommit;
    /**
     * Creates a commit with a set of blobs.
     * @param messages
     *            The commti message.
     * @param timeStamp
     *            The date time.
     * @param p1
     *            The parent commit.
     * @param p2
     *            Tha parent2.
     * @param blob
     *            The blobs involved in the commit.
     * @param ismergecommit
     * ssss
     */

    public Commit(String messages, ZonedDateTime timeStamp, Commit p1,
                  Commit p2, HashMap<String, Blob> blob,
                  Boolean ismergecommit) {
        if (messages == null || messages.isEmpty() || messages.equals("")) {
            System.out.println("Please enter a commit message.");
            System.exit(0);
        }
        this.isMergeCommit = ismergecommit;
        this.parent = p1;
        this.parent2 = p2;
        this.message = messages;
        this.date = timeStamp;
        this.blobs = blob;
    }



    /** wayayaya.
     * @return parent2
     * */
    public Commit getParent2() {
        return this.parent2;
    }
    /**
     * @return parent2
     * wayayaya. */
    public boolean isMergeCommit() {
        return this.isMergeCommit;
    }

    /**
     *sss.
     * @param cmt
     *   ssss.
     *
     * @return parent2
     * wayayaya.
     * */
    public static byte[] serialize(Commit cmt) {
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

    /**sss.
     * @param file
     * ssss.
     * @return parent2
     * wayayaya. */
    public static Commit deserialize(File file) {
        Commit cmt;
        try {
            ObjectInputStream inp =
                    new ObjectInputStream(new FileInputStream(file));
            cmt = (Commit) inp.readObject();
            inp.close();
        } catch (IOException | ClassNotFoundException excp) {
            cmt = null;
        }
        return cmt;
    }

    /**
     * @return parent2
     * wayayaya. */
    public String getID() {
        return Utils.sha1(serialize(this));
    }


    /**
     * Creates an initial commit.
     * @param message
     *            The initial message.
     * @param currentDate
     *            The date.
     */

    /**
     * @return the parent
     */
    public Commit getParent() {
        return this.parent;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * @return the date
     */
    public ZonedDateTime getDate() {
        return this.date;
    }

    /**
     * @return the blobs filename-sha1
     */
    public HashMap<String, Blob> getBlobs() {
        return this.blobs;
    }

    /**
     * Gets the toString of the commit.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String dateStr = this.date.format(formatter).replace('T', ' ');
        int nanoIndex = dateStr.indexOf('.');
        return "===\n" + "Commit " + this.sha1() + "\n"
                + dateStr.substring(0, nanoIndex) + "\n" + this.message + "\n";
    }

    /**
     * Determines the size of the blobs.
     */
    @Override
    public int size() {
        return this.blobs.size();
    }

    /**
     * Determines if a commit is empty.
     */
    @Override
    public boolean isEmpty() {
        return this.blobs.isEmpty();
    }

    /**
     * Determines if the commit references a file.
     */
    @Override
    public boolean containsKey(Object fileName) {
        return this.blobs.containsKey(fileName);
    }

    /**
     * Determines if the commti contains a blob hash.
     */
    @Override
    public boolean containsValue(Object hash) {
        return this.blobs.containsValue(hash);
    }

    /**
     * Gets a blob hash from a file name.
     */
    @Override
    public Blob get(Object fileName) {
        return this.blobs.get(fileName);
    }

    /**
     * Puts an element in the blob set.
     */
    @Override
    public Blob put(String fileName, Blob blob) {
        return this.blobs.put(fileName, blob);
    }

    /**
     * Removes an element from the blob set.
     */
    @Override
    public Blob remove(Object fileName) {
        return this.blobs.remove(fileName);
    }

    /**
     * Puts a bunch of blobs in the commit.
     */
    @Override
    public void putAll(Map<? extends String, ? extends Blob> m) {
        this.blobs.putAll(m);

    }

    /**
     * Clears the blobs.
     */
    @Override
    public void clear() {
        this.blobs.clear();
    }

    /**
     * Gets the blob keyset.
     */
    @Override
    public Set<String> keySet() {
        return this.blobs.keySet();
    }

    /**
     * Gets the blobs values.
     */
    @Override
    public Collection<Blob> values() {
        return this.blobs.values();
    }

    /**
     * Gets the blobs entry set.
     */
    @Override
    public Set<Map.Entry<String, Blob>> entrySet() {
        return this.blobs.entrySet();
    }

    /**
     * Iterates over the blobs.
     * @param action
     *            The action.
     */
    @Override
    public void forEach(BiConsumer<? super String, ? super Blob> action) {
        this.blobs.forEach(action);
    }
}
