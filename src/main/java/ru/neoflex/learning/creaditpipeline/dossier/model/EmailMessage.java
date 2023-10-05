package ru.neoflex.learning.creaditpipeline.dossier.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailMessage {

    private String address;

    private Theme theme;

    private UUID applicationId;
}
