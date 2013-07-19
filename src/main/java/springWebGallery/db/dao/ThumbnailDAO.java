package springWebGallery.db.dao;

import springWebGallery.models.Thumbnail;

import java.util.List;

public interface ThumbnailDAO {
    public List<Thumbnail> getThumbnailsByIds(List<Integer> listOfThumbnails);

    byte[] findByThumbnailId(int imageId);

    public void insert(Thumbnail thumbnail);

    public boolean deleteByThumbnailId(int thumbnailId);
}
