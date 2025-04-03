package org.nurgisa.pointrate.service;

import org.nurgisa.pointrate.client.GisApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreService {
    private final GisApiClient gisApiClient;

    @Autowired
    public ScoreService(GisApiClient gisApiClient) {
        this.gisApiClient = gisApiClient;
    }

}
