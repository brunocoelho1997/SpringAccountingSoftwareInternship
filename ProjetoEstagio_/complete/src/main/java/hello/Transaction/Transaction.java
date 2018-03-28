package hello.Transaction;

import hello.Entity;
import hello.Frequency;
import hello.Genre;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@MappedSuperclass
public class Transaction extends Entity {

    public static final int MAX_NAME_LENGHT = 30;
    public static final int MAX_DESCRIPTION_LENGHT = 30;

    @NotNull
    @Size(min=1, max = MAX_NAME_LENGHT)
    @Column(nullable = false, length = MAX_NAME_LENGHT)
    private String name;

    @Size(min=1, max = MAX_DESCRIPTION_LENGHT)
    @Column(length = MAX_DESCRIPTION_LENGHT)
    private String description;

    @NotNull
    @Column(nullable = false)
    private String value;


    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Frequency frequency;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genre genre;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}