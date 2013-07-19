$(document).ready(function () {
        $("#LoadingImage").show();
        $.getJSON('/photo/thumbnailset/30/0', function (data) {
            $("#LoadingImage").hide();
            if (data.length == 0) { //TODO handle 500 status
                $('#container').append('<h3>Empty photostream. Maybe, you\'ll try to <a href="image-uploader" title="Upload some images">upload</a> something?</h3>');
            } else {
                $.each(data, function (index, value) {
                    var link = "photo/" + value['idPhoto'];
                    var title = "title";
                    var dataGallery = "gallery";
                    var thumbnailData = value['imageData'];

                    $('#gallery').append('<a href="' + link + '" class="picture" title="' + title + '" data-gallery="' + dataGallery + '"><img src="data:image/jpg;base64,' + thumbnailData + '"/></a>');
                });
            }
        })
    }
);