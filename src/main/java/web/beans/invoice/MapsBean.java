/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.beans.invoice;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import main.core.communication.Communicator;
import main.domain.Invoice;
import main.domain.Position;
import org.codehaus.jettison.json.JSONException;
import web.beans.UserInfoBean;
import web.core.helpers.ContextHelper;

/**
 *
 * @author maikel
 */
@Named
@ViewScoped
public class MapsBean implements Serializable {

    @Inject
    private UserInfoBean userInfoBean;
    private Invoice invoice;
    private Long invoiceId;
    private List<Position> positions;

    public void init() {
        if (!ContextHelper.isAjaxRequest()) {
            try {
                invoice = Communicator.getInvoice(userInfoBean.getLoggedInUser().getId(), invoiceId);
                positions = new ArrayList<>();
                Long cartrackerId = Communicator.getCartrackerId(userInfoBean.getLoggedInUser().getId(), invoiceId);
                System.out.println("cartrackerId: " + cartrackerId);
                positions.addAll(Communicator.getPositions(1L));
            } catch (IOException | JSONException ex) {
                Logger.getLogger(MapsBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public String getPositionsAsJson() {
        String g = new Gson().toJson(positions);
        return g; 
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }
}
