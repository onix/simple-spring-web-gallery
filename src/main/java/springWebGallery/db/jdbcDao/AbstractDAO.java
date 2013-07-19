package springWebGallery.db.jdbcDao;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class AbstractDAO {
    static final Logger logger = Logger.getLogger(AbstractDAO.class);

    void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {}
        }
    }

    void handleSqlException(Exception e) {
        logger.info("Failed to make connection!");
        throw new RuntimeException(e);
    }
}
