package com.learnings.learningproject.repositories.seeders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

public class MainSeeder {

    @Component
    @Profile("dev")
    public static class DevMainSeeder implements CommandLineRunner {

        private final ISeeder groupSeeder;
        private final ISeeder cancellationSeeder;
        private final ISeeder subscriptionSeeder;

        @Autowired
        public DevMainSeeder(GroupSeeder groupSeeder, CancellationSeeder cancellationSeeder, SubscriptionSeeder subscriptionSeeder) {
            this.groupSeeder = groupSeeder;
            this.cancellationSeeder = cancellationSeeder;
            this.subscriptionSeeder = subscriptionSeeder;
        }

        @Override
        public void run(String... args) throws Exception {
            groupSeeder.run();
            subscriptionSeeder.run();
            cancellationSeeder.run();
        }
    }
}
