package com.example.springai.model;

/**
 * Model class representing a prompt template
 */
public class Prompt {
    private String id;
    private String name;
    private String template;
    private String description;

    public Prompt() {
    }

    public Prompt(String id, String name, String template, String description) {
        this.id = id;
        this.name = name;
        this.template = template;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Prompt{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", template='" + template + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
