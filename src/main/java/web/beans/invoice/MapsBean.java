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

import main.service.InvoiceService;
import web.beans.UserInfoBean;
import web.core.helpers.ContextHelper;

/**
 * @author maikel
 */
@Named
@ViewScoped
public class MapsBean implements Serializable {

    @Inject
    private InvoiceService invoiceService;
    @Inject
    private UserInfoBean userInfoBean;

    private Invoice invoice;
    private Long invoiceId;
    private List<Position> positions;

    public void init() {
        if (!ContextHelper.isAjaxRequest()) {
            try {
                invoice = invoiceService.retrieveInvoiceByUserAndId(userInfoBean.getLoggedInUser(), invoiceId);
                Long cartrackerId = Communicator.getCartrackerId(userInfoBean.getLoggedInUser().getId(), invoiceId);
                positions = new ArrayList<>(Communicator.getPositions(cartrackerId));
            } catch (IOException | JSONException ex) {
                Logger.getLogger(MapsBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String getPositionsAsJson() {
        return new Gson().toJson(positions);
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
