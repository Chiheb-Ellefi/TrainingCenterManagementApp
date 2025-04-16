package com.example.CenterManagement.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "Training Center Management API"
                ,version = "1.0"
                ,description = "This API provides a comprehensive system for managing users, training sessions, trainers, and participants. It includes functionality for:\n" +
                "\n" +
                "    \uD83D\uDD10 User Authentication – Register, login, and manage authenticated sessions.\n" +
                "\n" +
                "    \uD83D\uDC65 User Management – Create, update, and delete user profiles.\n" +
                "\n" +
                "    \uD83C\uDFCB\uFE0F Training Management – Organize and control training sessions, budgets, and timelines.\n" +
                "\n" +
                "    \uD83E\uDDD1\u200D\uD83C\uDFEB Trainer Management – Add and manage internal or external trainers.\n" +
                "\n" +
                "    \uD83D\uDE4B Participant Management – Handle participant profiles and organizational classification.\n" +
                "\n" +
                "    \uD83D\uDCC5 Training Enrollment – Enroll or remove participants from training sessions.\n" +
                "\n" +
                "All endpoints follow RESTful conventions and require appropriate authentication where necessary."
                ,contact = @Contact(name = "Chiheb Ellefi", email = "chiheb.ellefi@etudiant-isi.utm.tn")
        ),
        servers = @Server(url = "http://localhost:5000",description = "Local Server")
)
@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfiguration {
}
