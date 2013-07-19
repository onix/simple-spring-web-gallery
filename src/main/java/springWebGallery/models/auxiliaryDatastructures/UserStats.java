package springWebGallery.models.auxiliaryDatastructures;

public class UserStats {
    int amountOfPhotos;
    int amountOfPhotosets;

    public UserStats(int amountOfPhotos, int amountOfPhotosets) {
        this.amountOfPhotos = amountOfPhotos;
        this.amountOfPhotosets = amountOfPhotosets;
    }

    public int getAmountOfPhotos() {
        return amountOfPhotos;
    }

    public void setAmountOfPhotos(int amountOfPhotos) {
        this.amountOfPhotos = amountOfPhotos;
    }

    public int getAmountOfPhotosets() {
        return amountOfPhotosets;
    }

    public void setAmountOfPhotosets(int amountOfPhotosets) {
        this.amountOfPhotosets = amountOfPhotosets;
    }
}
