package gitlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.FileInputStream;
import java.util.HashMap;

/**
 * kaudnksjd.
 * @author zhiji
 */
public class Stage extends Utils implements Serializable {

    /**
     *
     * @param add
     * ssssss.
     * @param remove
     * ssssss.
     */
    public Stage(HashMap<String, Blob> add, HashMap<String, Blob> remove) {
        this.added = add;
        this.removed = remove;
    }

    /**
     * @return
     * ssssss.
     */
    public HashMap<String, Blob> getAdded() {
        return this.added;
    }

    /**
     *
     * @return
     */
    public HashMap<String, Blob> getRemoved() {
        return this.removed;
    }

    /**
     *
     * @param stg
     * ssssss.
     * @return
     * ssssss.
     */
    public static byte[] serialize(Stage stg) {
        byte[] result;
        ByteArrayOutputStream s = new ByteArrayOutputStream();
        try {
            ObjectOutputStream out =
                    new ObjectOutputStream(s);
            out.writeObject(stg);
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
     * ssssss.
     * @return
     * ssssss.
     */
    public static Stage deserialize(File file) {
        Stage stg;
        try {
            ObjectInputStream inp =
                    new ObjectInputStream(new FileInputStream(file));
            stg = (Stage) inp.readObject();
            inp.close();
        } catch (IOException | ClassNotFoundException excp) {
            stg = null;
        }
        return stg;
    }

    /**
     * ssssss.
     */
    private HashMap<String, Blob> added = new HashMap<String, Blob>();
    /**
     * sssss.
     */
    private HashMap<String, Blob> removed = new HashMap<String, Blob>();
}
