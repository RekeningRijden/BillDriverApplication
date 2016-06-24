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
import web.core.pagination.Paginator;

/**
 * @author Sam
 */
@Named
@ViewScoped
public class InvoiceOverviewBean extends Paginator implements Serializable {

    @Inject
    private UserInfoBean userInfoBean;
    @Inject
    private InvoiceService invoiceService;

    private Invoice invoice;

    private transient List<Invoice> invoices;

    private Properties properties;

    public void init() {
        if (!ContextHelper.isAjaxRequest()) {
            setItemsPerPage(15);
            properties = PropertiesHelper.getProperties(FacesContext.getCurrentInstance());

            refreshList();
        }
    }

    /**
     * Save a invoice to the database and display a success message when
     * finished.
     */
    public void save() {
        try {
            invoiceService.update(invoice, userInfoBean.getLoggedInUser());
            refreshList();

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

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }
    //</editor-fold>

    private void refreshList() {
        try {
            invoices = invoiceService.retrieveInvoicesByUser(userInfoBean.getLoggedInUser(), getStartIndex(), getItemsPerPage());
        } catch (IOException ex) {
            Logger.getLogger(InvoiceOverviewBean.class.getName()).log(Level.SEVERE, null, ex);
            FrontendHelper.displayWarningSmallBox(properties.getProperty("COULD_NOT_RETRIEVE_INVOICES"));
        }
    }

    @Override
    public void beforePreviousPage() {
        super.beforePreviousPage();
        refreshList();
    }

    @Override
    public void previousPage() {
        super.previousPage();
        refreshList();
    }

    @Override
    public void currentPage() {
        super.currentPage();
        refreshList();
    }

    @Override
    public void nextPage() {
        super.nextPage();
        refreshList();
    }

    @Override
    public void afterNextPage() {
        super.afterNextPage();
        refreshList();
    }

    @Override
    public void firstPage() {
        super.firstPage();
        refreshList();
    }

    @Override
    public void lastPage() {
        super.lastPage();
        refreshList();
    }

    @Override
    public void setItemsPerPage(int itemsPerPage) {
        super.setItemsPerPage(itemsPerPage);
        refreshList();
    }
}
