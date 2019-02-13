package gitlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * zhiji class.
 * @author zhiji
 */
public class Zhiji {
    /**
     *
     * @param object
     * slsl.
     * @return
     * sytw.
     */
    public static byte[] serialize(Object object) {
        byte[] result;
        ByteArrayOutputStream s = new ByteArrayOutputStream();
        try {
            ObjectOutputStream out =
                    new ObjectOutputStream(s);
            out.writeObject(object);
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
     * file.
     * @return
     * return.
     */

    public static Object deserialize(File file) {
        Object stg;
        try {
            ObjectInputStream inp =
                    new ObjectInputStream(new FileInputStream(file));
            stg = (Object) inp.readObject();
            inp.close();
        } catch (IOException | ClassNotFoundException excp) {
            stg = null;
        }
        return stg;
    }
}
