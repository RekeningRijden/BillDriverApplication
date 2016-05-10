package web.beans.invoice;

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
import web.beans.UserInfoBean;
import web.core.helpers.ContextHelper;
import web.core.pagination.ListPaginator;

/**
 * @author Sam
 */
@Named
@ViewScoped
public class InvoiceOverviewBean implements Serializable {

    @Inject
    private UserInfoBean userInfoBean;
    private Invoice invoice;
    private ListPaginator<Invoice> invoicePaginator;

    public void init() {
        if (!ContextHelper.isAjaxRequest()) {
            try {
                invoicePaginator = new ListPaginator<>(Communicator.getAllInvoices(userInfoBean.getLoggedInUser().getId()));
            } catch (IOException ex) {
                Logger.getLogger(InvoiceOverviewBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            invoicePaginator.setItemsPerPage(15);
        }
    }

    /**
     * Save a invoice to the database and display a success message when
     * finished.
     */
    public void save() {
        //invoiceService.update(invoice);
        //FrontendHelper.displaySuccessSmallBox("Saved");
    }

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
    //</editor-fold>

    public ListPaginator<Invoice> getInvoicePaginator() {
        return invoicePaginator;
    }

    public void setInvoicePaginator(ListPaginator<Invoice> invoicePaginator) {
        this.invoicePaginator = invoicePaginator;
    }

    public List<Invoice> getInvoices() {
        return invoicePaginator.toPaginatedList();
    }
}
