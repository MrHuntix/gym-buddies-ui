package com.example.gym.buddies.data.model;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Data;

@Data
public class Gyms implements ParentObject {
    private String id;
    private String name;
    private String startDay;
    private String endDay;
    private String startTime;
    private String endTime;
    private boolean isExpanded;
    private ArrayList<Branches> branches;

    public Gyms() {
        this.isExpanded = false;
    }

    @Override
    public List<Object> getChildObjectList() {
        return Collections.singletonList(branches);
    }

    @Override
    public void setChildObjectList(List<Object> list) {

    }
}
