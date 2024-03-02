package com.cloudbees.model;

import jakarta.persistence.Id;

public record Fare(@Id Integer trainNo, String source, String dest, String type, Integer amount) {
}
