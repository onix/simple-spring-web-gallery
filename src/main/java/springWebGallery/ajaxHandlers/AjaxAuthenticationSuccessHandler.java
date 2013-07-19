package springWebGallery.ajaxHandlers;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("ajaxAuthenticationSuccessHandler")
public class AjaxAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    public AjaxAuthenticationSuccessHandler() {
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if ("true".equals(request.getHeader("X-Ajax-call"))) {
            response.getWriter().print("authenticated");
            response.getWriter().flush();
            //setDefaultTargetUrl("/photostream");
            //super.onAuthenticationSuccess(request, response, authentication);
        } else {
            setAlwaysUseDefaultTargetUrl(false);
        }
    }
}
