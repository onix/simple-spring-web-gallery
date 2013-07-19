package springWebGallery.models;

public class Thumbnail {
    private int idPhoto;
    private byte[] imageData;

    public Thumbnail(int idPhoto, byte[] imageData) {
        this.idPhoto = idPhoto;
        this.imageData = imageData;
    }

    public int getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(int idPhoto) {
        this.idPhoto = idPhoto;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

}
