package springWebGallery.controllers;

import de.bripkens.gravatar.DefaultImage;
import de.bripkens.gravatar.Gravatar;
import de.bripkens.gravatar.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springWebGallery.db.dao.UserDAO;
import springWebGallery.models.User;
import springWebGallery.models.auxiliaryDatastructures.UserStats;

@Controller
@RequestMapping("profile")
public class ProfileController extends AbstractController {
    @Autowired
    UserDAO userDao;

    public void fillMapWithUserData(ModelMap model) {
        User profile = userDao.findByUserLogin(getSignedIndUserLogin());

        model.addAttribute("id", profile.getIdUser());
        model.addAttribute("login", profile.getLogin());
        model.addAttribute("email", profile.getEmail());
        model.addAttribute("name", profile.getName());
        model.addAttribute("surname", profile.getSurname());
        model.addAttribute("secondName", profile.getSecondName());
        model.addAttribute("role", profile.getRole().toLowerCase());

        String gravatarImageURL = new Gravatar()
                .setSize(420)
                .setHttps(true)
                .setRating(Rating.PARENTAL_GUIDANCE_SUGGESTED)
                .setStandardDefaultImage(DefaultImage.IDENTICON)
                .getUrl(profile.getEmail().toLowerCase());
        model.addAttribute("gravatarImageURL", gravatarImageURL);

        UserStats stats = userDao.statsByUserId(getSignedIndUserId());
        model.addAttribute("amountOfImages", stats.getAmountOfPhotos());
        model.addAttribute("amountOfPhotosets", stats.getAmountOfPhotosets());
    }

    @RequestMapping(method = RequestMethod.GET)
    public String viewProfile(ModelMap model) {
        fillMapWithUserData(model);
        return "profile-page";
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String editProfileGet(ModelMap model) {
        fillMapWithUserData(model);
        return "profile-edit";
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String editProfilePost(
            @RequestParam("inputLogin") String login,
            @RequestParam("inputEmail") String email,
            @RequestParam("inputName") String name,
            @RequestParam("inputSurname") String surname,
            @RequestParam("inputSecondName") String secondName) {
        User profile = userDao.findByUserLogin(getSignedIndUserLogin());
        profile.setLoginAndValidate(login);
        profile.setEmailAndValidate(email);

        if (!name.equals(""))
            profile.setNameAndValidate(name);
        else
            profile.setNameAndValidate(null);

        if (!surname.equals(""))
            profile.setSurnameAndValidate(surname);
        else
            profile.setSurnameAndValidate(null);

        if (!secondName.equals(""))
        {
            logger.info("isnt null");
            profile.setSecondNameAndValidate(secondName);
        }
        else  {
            logger.info("is null");
            profile.setSecondNameAndValidate(null); }

        logger.info(profile.getSecondName());

        userDao.update(profile);

        // Relogin, because login could been changed
        Authentication authentication = new UsernamePasswordAuthenticationToken(login, null,
                AuthorityUtils.createAuthorityList("ROLE_" + profile.getRole().toUpperCase()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "redirect:/profile";
    }

    @RequestMapping(value = "edit/change-password", method = RequestMethod.POST)
    public String editPasswordPost(
            @RequestParam("inputPassword") String password,
            @RequestParam("inputPasswordRepeat") String passwordRepeat) {
        if (!password.equals("") && password.equals(passwordRepeat)) {
            User profile = userDao.findByUserLogin(getSignedIndUserLogin());
            profile.setPasswordAndCheckIsHash(password);
            userDao.update(profile);
        }
        return "redirect:/profile";
    }

    /**
     * If user login is available or same as the login of authenticated user, return true.
     */
    @RequestMapping(value = "is-login-available", method = RequestMethod.GET)
    public @ResponseBody
    boolean isLoginAvailablePost(
            @RequestParam(value = "inputLogin", required = false) String inputLogin,
            @RequestParam(value = "loginRegistrationField", required = false) String loginRegistrationField
    ) {
        boolean result;
        if (loginRegistrationField != null) {
            // Called from registration form
            result = userDao.checkIsLoginAvailable(loginRegistrationField);
        } else {
            // Called from profile edit form
            result = inputLogin.equals(SecurityContextHolder.getContext().getAuthentication().getName()) ||
                    userDao.checkIsLoginAvailable(inputLogin);
        }
        return result;
    }
}
