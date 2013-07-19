package springWebGallery.controllers;

import imageUtil.ImageLoader;
import imageUtil.Picture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import springWebGallery.db.dao.PhotoDAO;
import springWebGallery.db.dao.ThumbnailDAO;
import springWebGallery.models.Photo;
import springWebGallery.models.Thumbnail;
import springWebGallery.uploader.FileUploadedJsonResponse;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

@Controller
@RequestMapping("photo")
public class PhotoController extends AbstractController {
    @Autowired
    PhotoDAO photoDao;

    @Autowired
    ThumbnailDAO thumbnailDao;

    public static final int THUMBNAIL_DIMENSION_PIXELS = 200;

    private byte[] createThumbnail(byte[] sourceImageBytes, int thumbDimension) throws InterruptedException, IOException {
        Picture img = ImageLoader.fromBytes(sourceImageBytes);
        Picture thumbnail = img.getResizedToSquare(thumbDimension, 0.0);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(thumbnail.getBufferedImage(), "jpg", output);

        return output.toByteArray();
    }

    private boolean fileContentTypeIsValid(String fileContentType) {
        return fileContentType.equals(MediaType.IMAGE_JPEG_VALUE) || fileContentType.equals(MediaType.IMAGE_PNG_VALUE);
    }

    @RequestMapping(value = "{imageId}", produces = {"image/jpeg", "image/jpg", "image/png"}, method = RequestMethod.GET)
    public
    @ResponseBody
    byte[] viewImage(@PathVariable int imageId) {
        return photoDao.findByPhotoId(imageId).getImageData();
    }

    @RequestMapping(value = "{imageId}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    void deleteImage(@PathVariable int imageId) {
        thumbnailDao.deleteByThumbnailId(imageId);
        photoDao.deleteByPhotoId(imageId);
    }

    @RequestMapping(value = "thumbnail/{imageId}", produces = {"image/jpeg", "image/jpg", "image/png"}, method = RequestMethod.GET)
    public
    @ResponseBody
    byte[] viewThumbnail(@PathVariable int imageId) {
        return thumbnailDao.findByThumbnailId(imageId);
    }

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public
    @ResponseBody
    FileUploadedJsonResponse upload(MultipartHttpServletRequest request, HttpServletResponse response) throws IOException, InterruptedException {
        FileUploadedJsonResponse files = null;
        int photoInsertedId = 0;

        Iterator<String> itr = request.getFileNames();
        MultipartFile file;

        while (itr.hasNext()) {
            file = request.getFile(itr.next());
            if (!file.isEmpty() && fileContentTypeIsValid(file.getContentType())) {
                Photo newPhoto = new Photo(getSignedIndUserId(), file.getBytes(), "");

                photoInsertedId = photoDao.insert(newPhoto);
                byte[] thumb = createThumbnail(newPhoto.getImageData(), THUMBNAIL_DIMENSION_PIXELS);
                Thumbnail thumbnail = new Thumbnail(photoInsertedId, thumb);
                thumbnailDao.insert(thumbnail);

                logger.info("Uploaded!");
            } else {
                logger.info("Wrong MIME type! Need image/jpeg or image/png, type found: " + file.getContentType());
            }

            String rootUrl = "http://" + request.getLocalName() + ":" + request.getServerPort();

            String url = rootUrl + "/photo/" + photoInsertedId;
            String thumbnail_url = rootUrl + "/photo/thumbnail/" + photoInsertedId;
            String name = file.getOriginalFilename();
            String type = file.getContentType();
            long size = file.getSize();
            String delete_url = rootUrl + "/photo/" + photoInsertedId;

            files = new FileUploadedJsonResponse(url, thumbnail_url, name, type, size, delete_url);
        }

        return files;
    }

    @RequestMapping(value = "thumbnailset/{amountOfThumbnails}/{page}", method = RequestMethod.GET)
    public
    @ResponseBody
    ArrayList<Thumbnail> getThumbnailsList(@PathVariable int amountOfThumbnails, @PathVariable int page) {
        ArrayList<Integer> listOfThumbs = (ArrayList<Integer>) photoDao.findPhotosByUserId(getSignedIndUserId(), amountOfThumbnails, page);
        logger.info("Extracted photos: " + listOfThumbs.toString());
        return (ArrayList<Thumbnail>) thumbnailDao.getThumbnailsByIds(listOfThumbs);
    }

}
