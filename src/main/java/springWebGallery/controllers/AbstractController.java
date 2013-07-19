package springWebGallery.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import springWebGallery.db.dao.UserDAO;
import springWebGallery.models.Exceptions.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AbstractController {
    static final Logger logger = Logger.getLogger(AbstractController.class);
    @Autowired
    UserDAO userDao;

    @ExceptionHandler(NullOrEmptyFieldException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "nullOrEmptyFieldException")
    public void handleExceptionNullOrEmptyFieldException(IllegalStateException ex, HttpServletResponse response) throws IOException {
    }

    @ExceptionHandler(PasswordHashingException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "passwordHashingException")
    public void handleExceptionPasswordHashingException(IllegalStateException ex, HttpServletResponse response) throws IOException {
    }

    @ExceptionHandler(RoleDoesNotExistException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "roleDoesNotExistException")
    public void handleExceptionRoleDoesNotExistException(IllegalStateException ex, HttpServletResponse response) throws IOException {
    }

    @ExceptionHandler(WrongFieldFormat.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "wrongFieldFormatException")
    public void handleExceptionWrongFieldFormatException(IllegalStateException ex, HttpServletResponse response) throws IOException {
    }

    @ExceptionHandler(WrongFieldSizeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "wrongFieldSizeException")
    public void handleExceptionWrongFieldSizeException(IllegalStateException ex, HttpServletResponse response) throws IOException {
    }

    int getSignedIndUserId() {
        return userDao.findByUserLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getIdUser();
    }

    String getSignedIndUserLogin() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
