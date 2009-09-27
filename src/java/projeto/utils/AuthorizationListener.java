/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto.utils;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

public class AuthorizationListener implements PhaseListener {

    public void afterPhase(PhaseEvent event) {
        System.out.println("<-----------------VALIDANDO REQUISIÇÃO------------------>"); 
        FacesContext facesContext = event.getFacesContext();
        String currentPage = facesContext.getViewRoot().getViewId();
        System.out.println("<-----------------" + currentPage.toString() + "------------------>");
        
        boolean isRestrictPage = currentPage.startsWith("/main/");
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        Object currentUser = session.getAttribute("currentUser");

        if (isRestrictPage && currentUser == null) {
            System.out.println("<-----------------USUÁRIO NÃO LOGADO------------------>");  
            NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
            nh.handleNavigation(facesContext, null, "login");
        }
        System.out.println("END PHASE " + event.getPhaseId());
    }

    public void beforePhase(PhaseEvent event) {
        System.out.println("START PHASE " + event.getPhaseId());

    }

    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}
