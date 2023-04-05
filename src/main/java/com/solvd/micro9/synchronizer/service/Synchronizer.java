package com.solvd.micro9.synchronizer.service;

import com.solvd.micro9.synchronizer.domain.eventstore.Es;

public interface Synchronizer {

    void sync(Es event);

}
