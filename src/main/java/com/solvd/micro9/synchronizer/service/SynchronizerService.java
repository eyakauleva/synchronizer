package com.solvd.micro9.synchronizer.service;

import com.solvd.micro9.synchronizer.domain.eventstore.Es;

public interface SynchronizerService {

    void sync(Es event);

}
