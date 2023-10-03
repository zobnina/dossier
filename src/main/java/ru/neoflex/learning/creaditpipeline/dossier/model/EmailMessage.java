package ru.neoflex.learning.creaditpipeline.dossier.model;

import lombok.Data;

import java.util.UUID;

@Data
public class EmailMessage {

    private String address;

    private Theme theme;

    private UUID applicationId;
}
