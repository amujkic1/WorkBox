package com.example.hr.service;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HibernateStatisticsService {

    private final SessionFactory sessionFactory;

    @Autowired
    public HibernateStatisticsService(EntityManagerFactory entityManagerFactory) {
        this.sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
    }

    public void logStatistics() {
        Statistics stats = sessionFactory.getStatistics();
        //stats.logSummary();
        //stats.get
    }
}
