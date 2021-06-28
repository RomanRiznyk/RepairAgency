package com.repair.agency.controller.utils;

import com.repair.agency.model.entity.Receipt;

import java.util.ArrayList;
import java.util.List;

public class PaginationUtil {

    public  static int countPages(int listSize, int rowsOnPage) {
        int pages = listSize / rowsOnPage;
        if (listSize % rowsOnPage != 0) {
            pages++;
        }
        return pages;
    }
    public static List<Receipt> getListByPage(String pageNumber, int rowsOnPage, List<Receipt> receiptList) {
        List<Receipt> pageReceiptList = new ArrayList<>();
        if (pageNumber == null) {
            pageNumber = "1";
        }
        int page = Integer.parseInt(pageNumber);
        int firstIndex = (page - 1) * rowsOnPage;
        int lastIndex = firstIndex + rowsOnPage;
        for (int i = firstIndex; i < receiptList.size() && i < lastIndex; i++) {
            pageReceiptList.add(receiptList.get(i));
        }
        return pageReceiptList;
    }
}
