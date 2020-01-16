package com.learnings.learningproject.services;

import com.learnings.learningproject.models.Cancellation;
import com.learnings.learningproject.models.Subscription;

import java.util.Date;
import java.util.List;

public interface ICancellationService {
    List<Cancellation> getAllCancellations();
    Cancellation getCancellation(long id);
    Cancellation createCancellation(Date cancelDate, long subId);
    Cancellation createCancellation(Date cancelDate, long subId, String reason);
    Subscription getCancellationSubscription(long cancelId);
}
