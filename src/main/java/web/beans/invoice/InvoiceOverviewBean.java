package web.beans.invoice;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import main.domain.Invoice;
import main.service.InvoiceService;
import web.beans.UserInfoBean;
import web.core.helpers.ContextHelper;
import web.core.helpers.FrontendHelper;
import web.core.pagination.ListPaginator;

/**
 * @author Sam
 */
@Named
@ViewScoped
public class InvoiceOverviewBean implements Serializable {

    @Inject
    private UserInfoBean userInfoBean;
    @Inject
    private InvoiceService invoiceService;

    private Invoice invoice;

    private transient ListPaginator<Invoice> invoicePaginator;

    public void init() {
        if (!ContextHelper.isAjaxRequest()) {
            try {
                invoiceService.retrieveInvoicesByUser(userInfoBean.getLoggedInUser());
            } catch (IOException ex) {
                Logger.getLogger(InvoiceOverviewBean.class.getName()).log(Level.SEVERE, null, ex);
                FrontendHelper.displayWarningSmallBox("Kon facturen niet ophalen");
            }
        }
    }

    /**
     * Save a invoice to the database and display a success message when
     * finished.
     */
    public void save() {
        try {
            invoiceService.save(invoice, userInfoBean.getLoggedInUser());
            invoiceService.retrieveInvoicesByUser(userInfoBean.getLoggedInUser());

            FrontendHelper.displaySuccessSmallBox("Saved");
        } catch (Exception ex) {
            Logger.getLogger(InvoiceOverviewBean.class.getName()).log(Level.SEVERE, null, ex);
            FrontendHelper.displayWarningSmallBox("Something went wrong");
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public ListPaginator<Invoice> getInvoicePaginator() {
        return invoicePaginator;
    }

    public void setInvoicePaginator(ListPaginator<Invoice> invoicePaginator) {
        this.invoicePaginator = invoicePaginator;
    }

    public List<Invoice> getInvoices() {
        return invoicePaginator.toPaginatedList();
    }
    //</editor-fold>
}
