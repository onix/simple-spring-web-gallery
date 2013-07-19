package springWebGallery.db.jdbcDao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;
import springWebGallery.models.Exceptions.RoleDoesNotExistException;
import springWebGallery.models.User;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/configs/web-application-config.xml")
public class JdbcUserDAOTest {
    @Autowired
    protected WebApplicationContext wac;
    private final String login = "ownLogin";
    private final String password = "ownpass";
    private final String passwordHash = "f0304beda2a82363cb9634e5776f3adee258b8aab30940aaa2b44771b742bb37";
    private final String email = "hello@example.com";
    private final String name = "name";
    private final String surname = "Surname";
    private final String secondName = "Second Name";
    //private final String roleUser = "user";
    private final String roleAdmin = "admin";

    public void deleteTestUser() {
        JdbcUserDAO userDAO = (JdbcUserDAO) wac.getBean("userDAO");
        userDAO.deleteUserByLogin(login);
    }

    @Test
    public void testInsertNewUser() throws Exception {
        deleteTestUser();
        User user = new User(login, password, email);
        JdbcUserDAO userDAO = (JdbcUserDAO) wac.getBean("userDAO");

        int userIDBefore = userDAO.getLastUsedUserID();
        userDAO.insert(user);

        int userIDAfter = userDAO.getLastUsedUserID();
        Assert.assertTrue(userIDBefore + 1 == userIDAfter);

        User extractedUser = userDAO.findByUserLogin(login);
        Assert.assertTrue(extractedUser.getLogin().equals(login));
        Assert.assertTrue(extractedUser.getPasswordHash().equals(passwordHash));
        Assert.assertTrue(extractedUser.getEmail().equals(email));
    }

    @Test
    public void updateUserByLogin() throws Exception, RoleDoesNotExistException {
        // User inserted from testInsertNewUser() test above
        JdbcUserDAO userDAO = (JdbcUserDAO) wac.getBean("userDAO");
        User databaseUser = userDAO.findByUserLogin(login);
        databaseUser.setNameAndValidate(name);
        databaseUser.setSurnameAndValidate(surname);
        databaseUser.setSecondNameAndValidate(secondName);
        databaseUser.setRoleAndValidate(roleAdmin);

        userDAO.update(databaseUser);

        User extractedUser = userDAO.findByUserLogin(login);
        Assert.assertTrue(extractedUser.getLogin().equals(login));
        Assert.assertTrue(extractedUser.getPasswordHash().equals(passwordHash));
        Assert.assertTrue(extractedUser.getEmail().equals(email));
        Assert.assertTrue(extractedUser.getName().equals(name));
        Assert.assertTrue(extractedUser.getSurname().equals(surname));
        Assert.assertTrue(extractedUser.getSecondName().equals(secondName));
        Assert.assertTrue(extractedUser.getRole().toLowerCase().equals(roleAdmin));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void deleteUserByLogin() throws Exception {
        JdbcUserDAO userDAO = (JdbcUserDAO) wac.getBean("userDAO");

        Assert.assertTrue(userDAO.findByUserLogin(login) != null);
        deleteTestUser();
        userDAO.findByUserLogin(login);
    }
}
