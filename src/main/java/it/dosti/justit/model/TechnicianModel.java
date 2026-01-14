package it.dosti.justit.model;

import it.dosti.justit.DAO.TechnicianDAO;
import it.dosti.justit.DAO.TechnicianDAOJDBC;

public class TechnicianModel {
    private final TechnicianDAO technicianDAO;

    public TechnicianModel() {
        this.technicianDAO = new TechnicianDAOJDBC();
    }
}
