package springWebGallery.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HomeAndNavigationController extends AbstractController {

    @RequestMapping(method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            logger.info("Authenticated user, redirecting to /photostream");
            return "redirect:/photostream";
        } else {
            logger.info("Unauthorized user, redirecting to /unauthorized");
            return "redirect:/unauthorized";
        }
    }

    @RequestMapping(value = "photostream", method = RequestMethod.GET)
    public String displayPhotostreamPage() {
        logger.info("/photostream called");
        return "photostream";
    }

    @RequestMapping(value = "image-uploader", method = RequestMethod.GET)
    public String displayUploaderPage() {
        logger.info("/uploader called");
        return "image-uploader";
    }

    @RequestMapping(value = "profile", method = RequestMethod.GET)
    public String displayProfilePage() {
        logger.info("/profile called");
        return "not-yet-implemented";
    }

    @RequestMapping(value = "photosets", method = RequestMethod.GET)
    public String displayPhotosetsPage() {
        logger.info("/photosets called");
        return "not-yet-implemented";
    }

    @RequestMapping(value = "admin", method = RequestMethod.GET)
    public String displayAdminPage() {
        logger.info("/admin called");
        return "not-yet-implemented";
    }
}