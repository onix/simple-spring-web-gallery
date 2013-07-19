package springWebGallery.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthorizationController extends AbstractController {

    @RequestMapping(value = "unauthorized", method = RequestMethod.GET)
    public String redirectToAuthorizationPage() {
        logger.info("/unauthorized called");
        return "unauthorized";
    }

    @RequestMapping(value = "signout", method = RequestMethod.GET)
    public String logout(ModelMap model) {
        logger.info("Signed out");
        return "unauthorized";
    }

}
