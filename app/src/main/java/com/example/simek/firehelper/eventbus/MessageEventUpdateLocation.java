package com.example.simek.firehelper.eventbus;

import android.location.Location;

/**
 * Created by Simek on 23. 04. 2017.
 */

public class MessageEventUpdateLocation {
    Location m;

    public MessageEventUpdateLocation(Location m) {
        this.m = m;
    }

    public Location getM() {
        return m;
    }
}
