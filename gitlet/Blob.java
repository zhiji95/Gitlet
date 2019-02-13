package gitlet;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

/**
 * contents.
 * @author zhiji
 */
public class Blob extends Utils implements Serializable {

    /**
     * contents.
     */
    private byte[] contents;
    /**
     * contents.
     */
    private String filename;

    /**
     * Generates a Blob.
     * @param content
     * sdua
     * @param fn
     * slskkdf.
     */
    public Blob(byte[] content, String fn) {
        this.contents = content;
        this.filename = fn;
    }

    /**
     *
     * @return
     * sss.
     */

    public String getID() {
        return Utils.sha1(serialize(this));
    }

    /**
     * @return the contents
     */
    public byte[] getContents() {
        return this.contents;
    }
    /**
     * @return the contents
     */

    public String getFilename() {
        return this.filename;
    }
    /**
     * @param b
     * scscs.
     * @return the contents
     */
    public static byte[] serialize(Blob b) {
        byte[] result;
        ByteArrayOutputStream s = new ByteArrayOutputStream();
        try {
            ObjectOutputStream out =
                    new ObjectOutputStream(s);
            out.writeObject(b);
            out.close();
            result = s.toByteArray();
            return result;
        } catch (IOException excp) {
            throw new Error(excp);
        }
    }
    /**
     * @param file
     * scscs.
     * @return the contents
     */
    public static Blob deserialize(File file) {
        Blob b;
        try {
            ObjectInputStream inp =
                    new ObjectInputStream(new FileInputStream(file));
            b = (Blob) inp.readObject();
            inp.close();
        } catch (IOException | ClassNotFoundException excp) {
            b = null;
        }
        return b;
    }
}
