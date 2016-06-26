package main.service;

import org.codehaus.jettison.json.JSONException;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;

import main.core.communication.Communicator;
import main.domain.Invoice;
import main.domain.User;
import main.domain.enums.PaymentStatus;
import main.pagination.InvoicePagination;
import web.core.pagination.ListPaginator;

/**
 * @author Sam
 */
@Stateless
public class InvoiceService implements Serializable {

    /**
     * Retrieves a list of Invoices belonging to a User. This list is
     * retrieved using a REST-call to the AdministrationSystem.
     * An listPaginator is created for the given invoices and is returned.
     *
     * @param user to get the Invoices from.
     * @return an listPaginator containing the Invoices.
     * @throws IOException when something goes wrong with the REST-call.
     */
    public InvoicePagination retrieveInvoicesByUser(User user, int pageIndex, int pageSize) throws IOException {
        return Communicator.getAllInvoices(user.getId(), pageIndex, pageSize);
    }

    /**
     * Retrieve an Invoice belonging to a User by it's id.
     * This invoice is retrieved using a REST-call to the AdministrationSystem.
     *
     * @param user      the invoice belongs to.
     * @param invoiceId to find the invoice with.
     * @return the retrieved invoice
     * @throws IOException when something went wrong with the REST-call.
     */
    public Invoice retrieveInvoiceByUserAndId(User user, Long invoiceId) throws IOException {
        return Communicator.getInvoice(user.getId(), invoiceId);
    }

    /**
     * Creates a REST-call that executes a HTTP-Put request to the AdministrationSystem.
     * This request updates the given Invoice to the database.
     *
     * @param invoice to update.
     * @param user    the invoice belongs to.
     * @throws IOException   when something went wrong with the REST-call.
     * @throws JSONException when something went wrong decrypting the REST-call JSON response.
     */
    public void update(Invoice invoice, User user) throws IOException, JSONException {
        Communicator.updateInvoice(user.getId(), invoice);
    }

    /**
     * Retrieves the Invoice belonging to the given User by the given Id.
     * Sets the Invoice status to PAID and updates the Invoice.
     *
     * @param user      the invoice belongs to.
     * @param invoiceId to get the Invoice by.
     * @throws IOException   when something went wrong with the REST-call.
     * @throws JSONException when something went wrong decrypting the REST-call JSON response.
     */
    public Invoice setInvoiceStatusPaid(User user, Long invoiceId) throws Exception {
        Invoice invoice = retrieveInvoiceByUserAndId(user, invoiceId);
        invoice.setPaymentStatus(PaymentStatus.PAID);
        update(invoice, user);

        return invoice;
    }
}
