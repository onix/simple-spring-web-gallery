package springWebGallery.db.jdbcDao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import springWebGallery.db.dao.PhotoDAO;
import springWebGallery.models.Photo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcPhotoDAO extends AbstractDAO implements PhotoDAO {

    private static String PHOTO_ID = "idPhoto";
    private static String USER_ID = "idUser";
    private static String PHOTO_DATA = "data";
    private static String PHOTO_DESCRIPTION = "description";

    private static String SELECT_PHOTOS_BY_USER_ID = "SELECT \"idPhoto\" FROM webimagegallery.gallery_photos " +
            "WHERE \"idUser\" = :idUser ORDER BY \"idPhoto\" DESC LIMIT :photosAmount OFFSET :page";

    private static String SELECT_PHOTO_BY_ID = "SELECT * FROM webimagegallery.gallery_photos WHERE \"idPhoto\" = :idPhoto";

    private static String INSERT_NEW_PHOTO = "INSERT INTO webimagegallery.gallery_photos (\"idUser\", data, description) " +
            "VALUES (:idUser, :data, :description)";

    private static String UPDATE_PHOTO_DESCRIPTION_BY_ID = "UPDATE webimagegallery.gallery_photos SET " +
            "description = :description " +
            "WHERE idPhoto = :idPhoto;";

    private static String DELETE_PHOTO_BY_ID = "DELETE FROM webimagegallery.gallery_photos WHERE \"idPhoto\" = :idPhoto";
    private static final String GET_LAST_USED_ID = "SELECT last_value FROM webimagegallery.\"gallery_photos_idPhoto_seq\"";

    private Connection connection = null;

    private DriverManagerDataSource dataSource;

    public void setDataSource(DriverManagerDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DriverManagerDataSource getDataSource() {
        return dataSource;
    }

    private MapSqlParameterSource fillMapSqlParametersSourceWithData(MapSqlParameterSource parameterMap, Photo photo) {
        parameterMap.addValue(USER_ID, photo.getIdUser());
        parameterMap.addValue(PHOTO_DATA, photo.getImageData());
        parameterMap.addValue(PHOTO_DESCRIPTION, photo.getImageDescription());
        return parameterMap;
    }

    @Override
    public ArrayList<Integer> findPhotosByUserId(int userId, int amountOfPhotos, int page) {
        if (userId != 0 && amountOfPhotos !=0) {
            connection = null;
            try {
                connection = dataSource.getConnection();
                NamedParameterJdbcTemplate namedSelect = new NamedParameterJdbcTemplate(this.dataSource);
                MapSqlParameterSource selectParameters = new MapSqlParameterSource();
                selectParameters.addValue("idUser", userId);
                selectParameters.addValue("photosAmount", amountOfPhotos);
                selectParameters.addValue("page", page);

                ArrayList<Integer> photos = (ArrayList<Integer>) namedSelect.queryForList(SELECT_PHOTOS_BY_USER_ID, selectParameters, Integer.class);

                connection.close();
                logger.info("Extracted photos for user with id " + userId + " , amount is " + amountOfPhotos + " , pagination " + page);
                return photos;

            } catch (SQLException e) {
                handleSqlException(e);
            } finally {
                closeConnection(connection);
            }
        }
        return null;
    }

    @Override
    public Photo findByPhotoId(int photoId) {
        if (photoId > 0) {
            connection = null;
            try {
                connection = dataSource.getConnection();
                NamedParameterJdbcTemplate namedSelect = new NamedParameterJdbcTemplate(this.dataSource);
                MapSqlParameterSource selectParameters = new MapSqlParameterSource();
                selectParameters.addValue("idPhoto", photoId);

                Photo photo = (Photo) namedSelect.queryForObject(SELECT_PHOTO_BY_ID, selectParameters,
                        new RowMapper() {
                            public Object mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                                return new Photo(
                                        resultSet.getInt(PHOTO_ID),
                                        resultSet.getInt(USER_ID),
                                        resultSet.getBytes(PHOTO_DATA),
                                        resultSet.getString(PHOTO_DESCRIPTION));
                            }
                        }
                );

                //connection.close(); //TODO multiple upload 500
                logger.info("Got photo with id " + photo.getIdPhoto());
                return photo;

            } catch (SQLException e) {
                handleSqlException(e);
            } finally {
                closeConnection(connection);
            }
        }
        return null;
    }

    @Override
    public int insert(Photo photo) {
        if (photo != null) {
            connection = null;
            try {
                connection = dataSource.getConnection();
                NamedParameterJdbcTemplate namedInsert = new NamedParameterJdbcTemplate(this.dataSource);
                MapSqlParameterSource insertParameters = new MapSqlParameterSource();

                fillMapSqlParametersSourceWithData(insertParameters, photo);

                KeyHolder lastIncrement = new GeneratedKeyHolder();
                namedInsert.update(INSERT_NEW_PHOTO, insertParameters, lastIncrement, new String[]{PHOTO_ID});
                logger.info("Photo with id " + lastIncrement.getKey().intValue() + " successfully added.");
                connection.close();
                return lastIncrement.getKey().intValue();
            } catch (SQLException e) {
                handleSqlException(e);
            } finally {
                closeConnection(connection);
            }
        }
        return 0;
    }

    @Override
    public void updateDesctiption(Photo photo) {
        if (photo != null) {
            connection = null;
            try {
                connection = dataSource.getConnection();
                NamedParameterJdbcTemplate namedUpdate = new NamedParameterJdbcTemplate(this.dataSource);
                MapSqlParameterSource updateParameters = new MapSqlParameterSource();
                updateParameters.addValue(PHOTO_ID, photo.getIdPhoto());
                updateParameters.addValue(PHOTO_DESCRIPTION, photo.getImageDescription());

                namedUpdate.update(UPDATE_PHOTO_DESCRIPTION_BY_ID, updateParameters);
                logger.info("Photo with id " + photo.getIdPhoto() + " description updated.");
                connection.close();

            } catch (SQLException e) {
                handleSqlException(e);
            } finally {
                closeConnection(connection);
            }
        }
    }

    @Override
    public boolean deleteByPhotoId(int photoId) {
        if (photoId > 0) {
            connection = null;
            try {
                connection = dataSource.getConnection();
                NamedParameterJdbcTemplate namedDelete = new NamedParameterJdbcTemplate(this.dataSource);

                int count = namedDelete.update(DELETE_PHOTO_BY_ID, new MapSqlParameterSource(PHOTO_ID, photoId));

                connection.close();
                logger.info("Photo with id " + photoId + " deleted from DB.");
                return count == 1;

            } catch (SQLException e) {
                handleSqlException(e);
            } finally {
                closeConnection(connection);
            }
        }
        return false;
    }

}
