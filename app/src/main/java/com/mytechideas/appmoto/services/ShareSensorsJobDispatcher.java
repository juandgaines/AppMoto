package com.mytechideas.appmoto.services;

import android.app.job.JobParameters;
import android.app.job.JobService;

public class ShareSensorsJobDispatcher extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
