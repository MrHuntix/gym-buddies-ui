package com.example.gym.buddies.utils;

import android.os.Build;

import com.example.gym.buddies.data.model.operation.Branch;
import com.example.gym.buddies.data.model.operation.Gym;
import com.example.gym.buddies.data.protos.GymProto;

import java.util.ArrayList;
import java.util.List;

public class MapperUtil {
    public static Gym getGymFromProto(GymProto.Gym gymProto) {
        Gym gym = new Gym();
        gym.setId(gymProto.getId());
        gym.setName(gymProto.getName());
        gym.setWebsite(gymProto.getWebsite());
        List<Branch> branches = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            gymProto.getBranchesList().forEach(branch -> {
                Branch b = new Branch();
                b.setId(branch.getId());
                b.setCity(branch.getCity());
                b.setContact(branch.getContact());
                b.setGymId(gymProto.getId());
                b.setLatitude(branch.getLatitude());
                b.setLongitude(branch.getLongitude());
                b.setLocality(branch.getLocality());
                branches.add(b);
            });
        } else {
            for(GymProto.Branch branch: gymProto.getBranchesList()) {
                Branch b = new Branch();
                b.setId(branch.getId());
                b.setCity(branch.getCity());
                b.setContact(branch.getContact());
                b.setGymId(gymProto.getId());
                b.setLatitude(branch.getLatitude());
                b.setLongitude(branch.getLongitude());
                b.setLocality(branch.getLocality());
                branches.add(b);
            }
        }
        gym.setBranches(branches);
        return gym;
    }
}
