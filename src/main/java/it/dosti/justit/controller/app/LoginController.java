package it.dosti.justit.controller.app;

import it.dosti.justit.bean.LoginBean;
import it.dosti.justit.dao.*;
import it.dosti.justit.dao.clientuser.ClientUserDAO;
import it.dosti.justit.dao.tech.TechnicianDAO;
import it.dosti.justit.exceptions.LoginFromBackEndException;
import it.dosti.justit.model.Credentials;
import it.dosti.justit.model.user.TechnicianUser;
import it.dosti.justit.utils.Session;
import it.dosti.justit.utils.SessionManager;

public class LoginController {
    public String checkLogin(LoginBean loginBean) throws IllegalArgumentException, LoginFromBackEndException {
        String sessionId = SessionManager.getInstance().createSession();
        switch (loginBean.getRoleType()) {
            case "CLIENT" -> {
                ClientUserDAO dao = DaoFactory.getInstance().getClientUserDAO();

                if (dao.login(new Credentials(loginBean.getUsername(), loginBean.getPassword()))) {
                    SessionManager.getInstance().getActiveSession(sessionId).setLoggedUser(dao.findByUsername(loginBean.getUsername()));
                    return sessionId;
                }
                SessionManager.getInstance().logout(sessionId);
                throw new LoginFromBackEndException("Invalid username or password");
            }
            case "TECHNICIAN" -> {
                TechnicianDAO dao = DaoFactory.getInstance().getTechnicianDAO();
                if(dao.login(new Credentials(loginBean.getUsername(), loginBean.getPassword()))) {
                    Session session = SessionManager.getInstance().getActiveSession(sessionId);
                    session.setLoggedUser(dao.findByUsername(loginBean.getUsername()));
                    TechnicianUser technicianUser = (TechnicianUser) session.getLoggedUser();
                    session.setCurrentShop(technicianUser.getShop());
                    return sessionId;
                }
                SessionManager.getInstance().logout(sessionId);
                throw new LoginFromBackEndException("Invalid username or password");
            }

            default -> {
                SessionManager.getInstance().logout(sessionId);
                throw new LoginFromBackEndException("Invalid username or password");
            }
        }
    }
}
