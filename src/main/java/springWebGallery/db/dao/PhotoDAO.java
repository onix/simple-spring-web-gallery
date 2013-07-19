package springWebGallery.db.dao;

import springWebGallery.models.Photo;

import java.util.List;

public interface PhotoDAO {
    public List<Integer> findPhotosByUserId(int userId, int amountOfPhotos, int page);

    public Photo findByPhotoId(int photoId);

    public int insert(Photo photo);

    public void updateDesctiption(Photo photo);

    public boolean deleteByPhotoId(int photoId);
}
