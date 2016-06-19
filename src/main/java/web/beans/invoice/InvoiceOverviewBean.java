package web.beans.invoice;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import main.domain.Invoice;
import main.service.InvoiceService;
import web.beans.UserInfoBean;
import web.core.helpers.ContextHelper;
import web.core.helpers.FrontendHelper;
import web.core.helpers.PropertiesHelper;
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

    private Properties properties;

    public void init() {
        if (!ContextHelper.isAjaxRequest()) {
            properties = PropertiesHelper.getProperties(FacesContext.getCurrentInstance());

            try {
                invoiceService.retrieveInvoicesByUser(userInfoBean.getLoggedInUser());
            } catch (IOException ex) {
                Logger.getLogger(InvoiceOverviewBean.class.getName()).log(Level.SEVERE, null, ex);
                FrontendHelper.displayWarningSmallBox(properties.getProperty("COULD_NOT_RETRIEVE_INVOICES"));
            }
        }
    }

    /**
     * Save a invoice to the database and display a success message when
     * finished.
     */
    public void save() {
        try {
            invoiceService.update(invoice, userInfoBean.getLoggedInUser());
            invoiceService.retrieveInvoicesByUser(userInfoBean.getLoggedInUser());

            FrontendHelper.displaySuccessSmallBox(properties.getProperty("SAVED"));
        } catch (Exception ex) {
            Logger.getLogger(InvoiceOverviewBean.class.getName()).log(Level.SEVERE, null, ex);
            FrontendHelper.displayWarningSmallBox(properties.getProperty("SOMETHING_WENT_WRONG"));
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
