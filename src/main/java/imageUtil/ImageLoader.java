package imageUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 *
 * @author James
 */
public class ImageLoader {

    private ImageLoader() {}
    
    /**
     * Load image from URL
     * @param url URL of image
     * @throws IOException If there is a problem loading the image from the URL
     */
    public static Picture fromUrl(URL url) throws IOException {
        return new Picture(url.openStream(), extensionToImageType(url.getPath()));
    }

    /**
     * Load image from URL string
     * @param url URL of image
     * @throws IOException If there is a problem loading the image from the URL
     */
    public static Picture fromUrl(String url) throws IOException {
        return fromUrl(new URL(url));
    }

    /**
     * Load image from file
     * @param file
     * @throws IOException If there is a problem loading the image from the file
     */
    public static Picture fromFile(File file) throws IOException {
        return new Picture(new FileInputStream(file), extensionToImageType(file.getPath()));
    }

    /**
     * Load image from file string
     * @param file
     * @throws IOException If there is a problem loading the image from the file
     */
    public static Picture fromFile(String file) throws IOException {
        return fromFile(new File(file));
    }

    /**
     * Load image from byte array
     * @param data image in the form of a byte array
     * @throws IOException
     */
    public static Picture fromBytes(byte[] data) throws IOException {
        return fromBytes(data, ImageType.UNKNOWN);
    }

    /**
     * Load image from byte array
     * @param data image in the form of a byte array
     * @param sourceType hint that may be used when you eventually write the image
     * @throws IOException
     */
    public static Picture fromBytes(byte[] data, ImageType sourceType) throws IOException {
        return new Picture(new ByteArrayInputStream(data), sourceType);
    }

    /**
     * Load image from an input stream
     * @param in image in the form of an input stream
     * @throws IOException
     */
    public static Picture fromStream(InputStream in) throws IOException {
        return fromStream(in, ImageType.UNKNOWN);
    }

    /**
     * Load image from an input stream
     * @param in image in the form of an input stream
     * @param sourceType hint that may be used when you eventually write the image
     * @throws IOException
     */
    public static Picture fromStream(InputStream in, ImageType sourceType) throws IOException {
        return new Picture(in, sourceType);
    }

    private static ImageType extensionToImageType(String path) {
        int idx = (path == null ? -1 : path.lastIndexOf("."));
        if (idx != -1)
            return ImageType.getType(path.substring(idx + 1));
        else
            return ImageType.UNKNOWN;

    }
}
