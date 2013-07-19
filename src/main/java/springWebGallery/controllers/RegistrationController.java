package springWebGallery.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import springWebGallery.db.dao.UserDAO;
import springWebGallery.models.User;

import java.security.NoSuchAlgorithmException;

@Controller
public class RegistrationController extends AbstractController {
    @Autowired
    UserDAO userDao;

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String register(
            @RequestParam("loginRegistrationField") String login,
            @RequestParam("passwordRegistrationField") String password,
            @RequestParam("emailRegistrationField") String email,
            @RequestParam("hiddenRegistrationField") String hidden) {
        if (hidden.equals("")) {
            try {
                User newUser = new User(login, password, email);
                userDao.insert(newUser);
            } catch (NoSuchAlgorithmException e) {
                logger.info("No Hashing algorithm found."); //TODO
            }
            Authentication auth = new UsernamePasswordAuthenticationToken(login, password);
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(auth);

            return "redirect:/photostream";
        } else {
            logger.info("Seems it's a spam.");
            return null;
        }
    }

}