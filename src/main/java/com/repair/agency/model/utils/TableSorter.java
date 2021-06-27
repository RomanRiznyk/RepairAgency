package com.repair.agency.model.utils;

import com.repair.agency.model.entity.Receipt;

import java.util.List;

public class TableSorter {
    public void sort(String sortingType, List<Receipt> receiptList) {
        if (sortingType == null) {
            return;
        }
        switch (sortingType) {
            case "ByDate":
                receiptList.sort((o1, o2) -> o2.getCreateDate().compareTo(o1.getCreateDate()));
                break;
            case "ByStatus":
                receiptList.sort((o1, o2) -> o2.getStatus().compareTo(o1.getStatus()));
                break;
            case "ByPrice":
                receiptList.sort(((o1, o2) -> o2.getPrice().compareTo(o1.getPrice())));
                break;
            default:
                break;
        }
    }
}
