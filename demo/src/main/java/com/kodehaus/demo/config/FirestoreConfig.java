package com.kodehaus.demo.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirestoreConfig {

  @Bean
  public Firestore firestore() throws IOException{
    GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
    FirebaseOptions options = FirebaseOptions.builder()
        .setCredentials(credentials)
        .setProjectId("kodehaus-sandbox")
        .build();
    FirebaseApp.initializeApp(options);

    Firestore db = FirestoreClient.getFirestore();
    return db;
  }
}