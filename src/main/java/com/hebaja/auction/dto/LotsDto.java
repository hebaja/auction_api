package com.hebaja.auction.dto;

import java.util.ArrayList;
import java.util.List;

import com.hebaja.auction.model.Lot;

public class LotsDto {

    private List<Lot> lots = new ArrayList<>();

    public void addLot(Lot lot) {
        this.lots.add(lot);
    }

    public List<Lot> getLots() {
        return lots;
    }

    public void setLots(List<Lot> lots) {
        this.lots = lots;
    }
}
