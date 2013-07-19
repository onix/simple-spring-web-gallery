package springWebGallery.db.jdbcDao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import springWebGallery.db.dao.UserDAO;
import springWebGallery.models.User;
import springWebGallery.models.auxiliaryDatastructures.UserStats;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUserDAO extends AbstractDAO implements UserDAO {

    private static String ID = "idUser";
    private static String LOGIN = "login";
    private static String PASSWORD_DB_FIELD = "password";
    private static String EMAIL = "email";
    private static String NAME = "name";
    private static String SURNAME = "surname";
    private static String SECOND_NAME = "secondName";
    private static String ROLE = "role";

    private static String INSERT_NEW_USER = "INSERT INTO webimagegallery.gallery_users (login, password, email, role)" +
            "VALUES (:login, :passwordHash, :email, :role)";
    private static String SELECT_USER_BY_LOGIN = "SELECT * FROM webimagegallery.gallery_users WHERE login = :login";
    private static String UPDATE_USER_BY_ID = "UPDATE webimagegallery.gallery_users SET " +
            "login = :login, " +
            "password = :passwordHash, " +
            "email = :email, " +
            "name = :name, " +
            "surname = :surname, " +
            "\"secondName\" = :secondName, " +
            "role = :role " +
            "WHERE \"idUser\" = :idUser;";
    private static String DELETE_USER_BY_LOGIN = "DELETE FROM webimagegallery.gallery_users WHERE login = :login";
    private static final String GET_LAST_USED_ID = "SELECT last_value FROM webimagegallery.\"gallery_users_idUser_seq\"";
    private static final String GET_AMOUNT_OF_PHOTOS = "SELECT count(*) AS photosAmount FROM webimagegallery.gallery_photos WHERE \"idUser\" = :idUser";
    private static final String GET_AMOUNT_OF_PHOTOSETS = "SELECT count(*) AS photosetsAmount FROM webimagegallery.gallery_photosets WHERE \"idOwner\" = :idUser";
    private static final String GET_AMOUNT_OF_USERS = "SELECT count(*) AS userAmount FROM webimagegallery.gallery_users WHERE login = :login";

    private Connection connection = null;

    private DriverManagerDataSource dataSource;

    public void setDataSource(DriverManagerDataSource dataSource) {
        this.dataSource = dataSource;
    }

    private MapSqlParameterSource fillMapSqlParametersSourceWithRequiredUserFields(MapSqlParameterSource parameterMap, User user) {
        parameterMap.addValue(LOGIN, user.getLogin());
        parameterMap.addValue("passwordHash", user.getPasswordHash());
        parameterMap.addValue(EMAIL, user.getEmail());
        parameterMap.addValue(ROLE, user.getRole().toLowerCase());
        return parameterMap;
    }

    private MapSqlParameterSource fillMapSqlParametersSourceWithNotRequiredUserFields(MapSqlParameterSource parameterMap, User user) {
        parameterMap.addValue(NAME, user.getName());
        parameterMap.addValue(SURNAME, user.getSurname());
        parameterMap.addValue(SECOND_NAME, user.getSecondName());
        return parameterMap;
    }

    @Override
    public User findByUserLogin(String userLogin) {
        if (userLogin != null) {
            connection = null;
            try {
                connection = dataSource.getConnection();
                NamedParameterJdbcTemplate namedSelect = new NamedParameterJdbcTemplate(this.dataSource);
                MapSqlParameterSource selectParameters = new MapSqlParameterSource(LOGIN, userLogin);

                User user = (User) namedSelect.queryForObject(SELECT_USER_BY_LOGIN, selectParameters,
                        new RowMapper() {
                            public Object mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                                return new User(resultSet.getInt(ID),
                                        resultSet.getString(LOGIN),
                                        resultSet.getString(PASSWORD_DB_FIELD),
                                        resultSet.getString(EMAIL),
                                        resultSet.getString(NAME),
                                        resultSet.getString(SURNAME),
                                        resultSet.getString(SECOND_NAME),
                                        resultSet.getString(ROLE));
                            }
                        }
                );
                //connection.close(); //TODO multiple upload 500
                logger.info("User with login " + user.getLogin() + " and ID " + user.getIdUser() + " got from DB.");
                return user;

            } catch (SQLException e) {
                handleSqlException(e);
            } finally {
                closeConnection(connection);
            }
        }
        return null;
    }

    @Override
    public UserStats statsByUserId(int userId) {
        if (userId > 0) {
            connection = null;
            try {
                connection = dataSource.getConnection();
                NamedParameterJdbcTemplate namedSelect = new NamedParameterJdbcTemplate(this.dataSource);
                MapSqlParameterSource selectParameters = new MapSqlParameterSource("idUser", userId);

                int amountOfPhotos = namedSelect.queryForObject(GET_AMOUNT_OF_PHOTOS, selectParameters, Integer.class);
                int amountOfPhotosets = namedSelect.queryForObject(GET_AMOUNT_OF_PHOTOSETS, selectParameters, Integer.class);

                UserStats stats = new UserStats(amountOfPhotos, amountOfPhotosets);

                logger.info("User with id " + userId + " have " + stats.getAmountOfPhotos() + " photos and " + stats.getAmountOfPhotosets() + " of photosets.");
                return stats;
            } catch (SQLException e) {
                handleSqlException(e);
            } finally {
                closeConnection(connection);
            }
        }
        return null;
    }

    @Override
    public boolean checkIsLoginAvailable(String userLogin) {
        if (userLogin != null) {
            connection = null;
            try {
                connection = dataSource.getConnection();
                NamedParameterJdbcTemplate namedSelect = new NamedParameterJdbcTemplate(this.dataSource);
                MapSqlParameterSource selectParameters = new MapSqlParameterSource(LOGIN, userLogin);
                int amountOfUsers = namedSelect.queryForObject(GET_AMOUNT_OF_USERS, selectParameters, Integer.class);
                return (amountOfUsers == 0);
            } catch (SQLException e) {
                handleSqlException(e);
            } finally {
                closeConnection(connection);
            }
        }
        return true;
    }

    @Override
    public void insert(User user) {
        if (user != null) {
            connection = null;
            try {
                connection = dataSource.getConnection();
                NamedParameterJdbcTemplate namedInsert = new NamedParameterJdbcTemplate(this.dataSource);
                MapSqlParameterSource insertParameters = new MapSqlParameterSource();

                fillMapSqlParametersSourceWithRequiredUserFields(insertParameters, user);

                namedInsert.update(INSERT_NEW_USER, insertParameters);
                logger.info("User " + user.getLogin() + " successfully added.");
                connection.close();

            } catch (SQLException e) {
                handleSqlException(e);
            } finally {
                closeConnection(connection);
            }
        }
    }

    @Override
    public void update(User user) {
        if (user != null) {
            connection = null;
            try {
                connection = dataSource.getConnection();
                NamedParameterJdbcTemplate namedUpdate = new NamedParameterJdbcTemplate(this.dataSource);
                MapSqlParameterSource updateParameters = new MapSqlParameterSource();

                fillMapSqlParametersSourceWithRequiredUserFields(updateParameters, user);
                fillMapSqlParametersSourceWithNotRequiredUserFields(updateParameters,user);
                updateParameters.addValue("idUser", user.getIdUser());

                namedUpdate.update(UPDATE_USER_BY_ID, updateParameters);
                logger.info("User " + user.getLogin() + " successfully updated.");
                connection.close();

            } catch (SQLException e) {
                handleSqlException(e);
            } finally {
                closeConnection(connection);
            }
        }
    }

    @Override
    public boolean deleteUserByLogin(String userLogin) {
        if (userLogin != null) {
            connection = null;
            try {
                connection = dataSource.getConnection();
                NamedParameterJdbcTemplate namedDelete = new NamedParameterJdbcTemplate(this.dataSource);

                int count = namedDelete.update(DELETE_USER_BY_LOGIN, new MapSqlParameterSource(LOGIN, userLogin));

                connection.close();
                logger.info("User with login " + userLogin + " deleted from DB.");
                return count == 1;

            } catch (SQLException e) {
                handleSqlException(e);
            } finally {
                closeConnection(connection);
            }
        }
        return false;
    }

    @Override
    public int getLastUsedUserID() {
        connection = null;
        try {
            connection = dataSource.getConnection();
            JdbcTemplate namedRequest = new JdbcTemplate(this.dataSource);

            int lastUserID = namedRequest.queryForObject(GET_LAST_USED_ID, Integer.class);

            connection.close();
            logger.info("Last UserID used is " + lastUserID);
            return lastUserID;
        } catch (SQLException e) {
            handleSqlException(e);
            return 0; //!
        } finally {
            closeConnection(connection);
        }
    }

}
