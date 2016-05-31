/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.beans.invoice;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import main.core.communication.Communicator;
import main.domain.Invoice;
import main.domain.enums.PaymentStatus;
import web.beans.UserInfoBean;
import web.core.helpers.ContextHelper;
import web.core.helpers.RedirectHelper;

/**
 *
 * @author maikel
 */
@Named
@SessionScoped
public class SuccessBean implements Serializable {

    @Inject
    private UserInfoBean userInfoBean;
    private Long invoiceId;
    private Invoice invoice;
    private boolean canUpdateStatus = false;

    public void init() {
        if (!ContextHelper.isAjaxRequest()) {
            if (canUpdateStatus) {
                try {
                    invoice = Communicator.getInvoice(userInfoBean.getLoggedInUser().getId(), invoiceId);
                    invoice.setPaymentStatus(PaymentStatus.PAID);
                    Communicator.updateInvoice(userInfoBean.getLoggedInUser().getId(), invoice);
                    RedirectHelper.redirect("/pages/invoice/invoiceOverview.xhtml");
                } catch (Exception ex) {
                    Logger.getLogger(SuccessBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Getters and setters">
    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public boolean isCanUpdateStatus() {
        return canUpdateStatus;
    }

    public void setCanUpdateStatus(boolean canUpdateStatus) {
        this.canUpdateStatus = canUpdateStatus;
    }

//</editor-fold>
}
