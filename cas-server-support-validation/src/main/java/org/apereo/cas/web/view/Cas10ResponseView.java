package org.apereo.cas.web.view;

import org.apereo.cas.services.web.view.AbstractCasView;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Custom View to Return the CAS 1.0 Protocol Response. Implemented as a view
 * class rather than a JSP (like CAS 2.0 spec) because of the requirement of the
 * line feeds to be "\n".
 *
 * @author Scott Battaglia
 * @since 3.0.0
 */
public class Cas10ResponseView extends AbstractCasView {

    @Override
    protected void renderMergedOutputModel(final Map model,
            final HttpServletRequest request, final HttpServletResponse response)
                    throws Exception {

        if (this.successResponse) {
            response.getWriter().print(
                    "yes\n" + getPrimaryAuthenticationFrom(model).getPrincipal().getId() + '\n');
        } else {
            response.getWriter().print("no\n\n");
        }
    }

    /**
     * The type Success.
     */
    @RefreshScope
    @Component("cas1ServiceSuccessView")
    public static class Success extends Cas10ResponseView {
        /**
         * Instantiates a new Success.
         */
        public Success() {
            super.setSuccessResponse(true);
        }
    }

    /**
     * The type Failure.
     */
    @RefreshScope
    @Component("cas1ServiceFailureView")
    public static class Failure extends Cas10ResponseView {
        /**
         * Instantiates a new Failure.
         */
        public Failure() {
            super.setSuccessResponse(false);
        }
    }

}
