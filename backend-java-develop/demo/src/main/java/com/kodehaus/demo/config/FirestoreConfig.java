package com.kodehaus.demo.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirestoreConfig {

    @Value("${firestore.use-emulator:true}")
    private boolean useEmulator;

    @Value("${firestore.project-id}")
    private String projectId;

    @Value("${firestore.credentials.path}")
    private String credentialsPath;

    @Bean
    public Firestore firestore() throws IOException {
        if (useEmulator) {
            System.out.println("Conectando al Firestore EMULATOR en 127.0.0.1:8080");

            return com.google.cloud.firestore.FirestoreOptions.newBuilder()
                    .setProjectId(projectId)
                    .setHost("127.0.0.1:8080")
                    .setCredentials(com.google.cloud.NoCredentials.getInstance())
                    .build()
                    .getService();
        } else {
            System.out.println("Conectando al Firestore REAL con credenciales");

            // Lee el JSON desde resources
            InputStream serviceAccount = getClass()
                    .getClassLoader()
                    .getResourceAsStream(credentialsPath);

            if (serviceAccount == null) {
                throw new IOException("No se encontró el archivo de credenciales: " + credentialsPath);
            }

            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);

            FirebaseOptions options = FirebaseOptions.builder()
                    .setProjectId(projectId)
                    .setCredentials(credentials)
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }

            return FirestoreClient.getFirestore();
        }
    }
}
