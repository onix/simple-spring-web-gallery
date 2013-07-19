package springWebGallery.uploader;

public class FileUploadedJsonResponse {
    public class FileMeta {
        private String url;
        private String thumbnail_url;
        private String name;
        private String type;
        private long size;
        private String delete_url;
        private final String delete_type = "DELETE";

        public FileMeta(String url, String thumbnail_url, String name, String type, long size, String delete_url) {
            this.url = url;
            this.thumbnail_url = thumbnail_url;
            this.name = name;
            this.type = type;
            this.size = size;
            this.delete_url = delete_url;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getThumbnail_url() {
            return thumbnail_url;
        }

        public void setThumbnail_url(String thumbnail_url) {
            this.thumbnail_url = thumbnail_url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }

        public String getDelete_url() {
            return delete_url;
        }

        public void setDelete_url(String delete_url) {
            this.delete_url = delete_url;
        }

        public String getDelete_type() {
            return delete_type;
        }
    }

    private FileMeta[] files;

    public FileUploadedJsonResponse(String url, String thumbnail_url, String name, String type, long size, String delete_url) {

        this.files = new FileMeta[]{new FileMeta(url, thumbnail_url, name, type, size, delete_url)};
    }

    public FileMeta[] getFiles() {
        return files;
    }

    public void setFiles(FileMeta[] files) {
        this.files = files;
    }
}
