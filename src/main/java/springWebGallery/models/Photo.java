package springWebGallery.models;

public class Photo {
    private int idPhoto;
    private int idUser;
    private byte[] imageData;
    private String imageDescription;

    public Photo(int idUser, byte[] imageData, String imageDescription) {
        this.idUser = idUser;
        this.imageData = imageData;
        this.imageDescription = imageDescription;
    }

    public Photo(int idPhoto, int idUser, byte[] imageData, String imageDescription) {
        this.idPhoto = idPhoto;
        this.idUser = idUser;
        this.imageData = imageData;
        this.imageDescription = imageDescription;
    }

    public int getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(int idPhoto) {
        this.idPhoto = idPhoto;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getImageDescription() {
        return imageDescription;
    }

    public void setImageDescription(String imageDescription) {
        this.imageDescription = imageDescription;
    }

}
