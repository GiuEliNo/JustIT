package it.dosti.justit.bean.mapper;

import it.dosti.justit.bean.InvoiceBean;
import it.dosti.justit.model.Invoice;

public class InvoiceMapper {

    private InvoiceMapper() {
    }


    public static InvoiceBean toBean(Invoice invoice) {

        if (invoice == null) {
            return null;
        }

        InvoiceBean bean = new InvoiceBean();

        bean.setTotalCost(invoice.getTotalCost());
        bean.setPaid(invoice.isPaid());

        return bean;
    }
}