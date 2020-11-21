package config;

import org.h2.server.web.WebServlet;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import servlets.Controleur;

import javax.servlet.*;
import java.util.EnumSet;

public class WebServletConfiguration implements WebApplicationInitializer{

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext webctx=new AnnotationConfigWebApplicationContext();
        webctx.register(ClientWebConfig.class);
        webctx.setServletContext(servletContext);

        ServletRegistration.Dynamic servlet=servletContext.addServlet("controleur",new Controleur());
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/");


/*        ServletRegistration.Dynamic servlet=servletContext.addServlet("dispatcher",new DispatcherServlet(webctx));
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/");
*/
        ContextLoaderListener contextLoaderListener = new ContextLoaderListener(webctx);
        servletContext.addListener(contextLoaderListener);

/*        FilterRegistration.Dynamic filter=servletContext.addFilter("emInViewFilter", new OpenEntityManagerInViewFilter());
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
*/
        ServletRegistration.Dynamic h2servlet = servletContext
                .addServlet("h2-console", new WebServlet());
        h2servlet.setLoadOnStartup(2);
        h2servlet.addMapping("/console/*");
    }
}
