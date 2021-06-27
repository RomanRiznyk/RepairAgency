package com.repair.agency.model.utils;

import com.repair.agency.model.entity.Receipt;

import java.util.ArrayList;
import java.util.List;

public class ListSplitter {
    public List<Receipt> getListByPage(String pageNumber, int rowsOnPage, List<Receipt> receiptList) {
        List<Receipt> pageReceiptList = new ArrayList<>();

        if (pageNumber == null) {
            pageNumber = "1";
        }
        int page = Integer.parseInt(pageNumber);
        int min = (page - 1) * rowsOnPage;
        int max = min + rowsOnPage;
        int counter = 0;
        for (Receipt i : receiptList) {
            counter++;
            if (counter > max) {
                break;
            }
            if (counter > min) {
                pageReceiptList.add(i);
            }
        }
        return pageReceiptList;
    }
}
