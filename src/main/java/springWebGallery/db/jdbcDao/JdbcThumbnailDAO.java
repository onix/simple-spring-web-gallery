package springWebGallery.db.jdbcDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import springWebGallery.db.dao.ThumbnailDAO;
import springWebGallery.models.Thumbnail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JdbcThumbnailDAO extends AbstractDAO implements ThumbnailDAO {

    private static String PHOTO_ID = "idPhoto";
    private static String PHOTO_DATA = "data";

    private static String SELECT_THUMBNAIL_DATA_BY_ID = "SELECT * FROM webimagegallery.gallery_thumbnails WHERE \"idPhoto\" = :idPhoto";
    private static String INSERT_NEW_THUMBNAIL = "INSERT INTO webimagegallery.gallery_thumbnails (\"idPhoto\", data) VALUES (:idPhoto, :data)";
    private static String DELETE_THUMBNAIL_BY_ID = "DELETE FROM webimagegallery.gallery_thumbnails WHERE \"idPhoto\" = :idPhoto";

    private Connection connection = null;

    private DriverManagerDataSource dataSource;

    public void setDataSource(DriverManagerDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DriverManagerDataSource getDataSource() {
        return dataSource;
    }

    @Override
    public List<Thumbnail> getThumbnailsByIds(List<Integer> listOfThumbnails) {
        if (listOfThumbnails != null) {
            connection = null;
            try {
                connection = dataSource.getConnection();
                NamedParameterJdbcTemplate namedSelect = new NamedParameterJdbcTemplate(this.dataSource);
                MapSqlParameterSource selectParameters = new MapSqlParameterSource();

                ArrayList<Thumbnail> thumbnails = new ArrayList<Thumbnail>();

                for(Integer le: listOfThumbnails) {
                    selectParameters.addValue("idPhoto", le);
                    logger.info("current element: " + le + " , idphoto parameter value: " + selectParameters.getValue("idPhoto"));

                    Map<String,Object> results = namedSelect.queryForMap(SELECT_THUMBNAIL_DATA_BY_ID, selectParameters);

                    logger.info(results.toString());

                    int thumbId = Integer.parseInt(results.get(PHOTO_ID).toString());
                    byte[] thumbData = (byte[])results.get(PHOTO_DATA);
                    Thumbnail newThumb = new Thumbnail(thumbId, thumbData);

                    /*
                    Thumbnail newThumb = (Thumbnail) namedSelect.queryForObject(SELECT_THUMBNAIL_DATA_BY_ID, selectParameters,
                            new RowMapper() {
                                public Object mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                                    return new Thumbnail(resultSet.getInt(PHOTO_ID), resultSet.getBytes(PHOTO_DATA));
                                }
                            }
                    );
                    */
                    thumbnails.add(newThumb);
                }

                connection.close();
                logger.info("Extracted thumb List. ");
                return thumbnails;

            } catch (SQLException e) {
                handleSqlException(e);
            } finally {
                closeConnection(connection);
            }
        }
        return null;
    }

    @Override
    public byte[] findByThumbnailId(int imageId) {
        if (imageId > 0) {
            connection = null;
            try {
                connection = dataSource.getConnection();
                NamedParameterJdbcTemplate namedSelect = new NamedParameterJdbcTemplate(this.dataSource);
                MapSqlParameterSource selectParameters = new MapSqlParameterSource();
                selectParameters.addValue("idPhoto", imageId);
                Map<String,Object> results = namedSelect.queryForMap(SELECT_THUMBNAIL_DATA_BY_ID, selectParameters);
                return (byte[])results.get(PHOTO_DATA);
            } catch (SQLException e) {
                handleSqlException(e);
            } finally {
                closeConnection(connection);
            }
        }
        return null;
    }

    @Override
    public void insert(Thumbnail thumbnail) {
        if (thumbnail != null) {
            connection = null;
            try {
                connection = dataSource.getConnection();
                NamedParameterJdbcTemplate namedInsert = new NamedParameterJdbcTemplate(this.dataSource);
                MapSqlParameterSource insertParameters = new MapSqlParameterSource();

                insertParameters.addValue(PHOTO_ID, thumbnail.getIdPhoto());
                insertParameters.addValue(PHOTO_DATA, thumbnail.getImageData());

                namedInsert.update(INSERT_NEW_THUMBNAIL, insertParameters);
                logger.info("Thumbnail with id " + thumbnail.getIdPhoto() + " successfully added.");
                //connection.close(); //TODO multiple upload 500

            } catch (SQLException e) {
                handleSqlException(e);
            } finally {
                closeConnection(connection);
            }
        }
    }

    @Override
    public boolean deleteByThumbnailId(int thumbnailId) {
        if (thumbnailId > 0) {
            connection = null;
            try {
                connection = dataSource.getConnection();
                NamedParameterJdbcTemplate namedDelete = new NamedParameterJdbcTemplate(this.dataSource);

                int count = namedDelete.update(DELETE_THUMBNAIL_BY_ID, new MapSqlParameterSource(PHOTO_ID, thumbnailId));

                connection.close();
                logger.info("Thumbnail with id " + thumbnailId + " deleted from DB.");
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
