package hello.Transaction;

import hello.Enums.Frequency;
import hello.Enums.Genre;
import hello.EntityPackage.Entity;
import hello.SubType.SubType;
import hello.Type.Type;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@MappedSuperclass
public class Transaction extends Entity {

    public static final int MAX_NAME_LENGHT = 30;
    public static final int MAX_DESCRIPTION_LENGHT = 30;

    @NotNull
    @Length(min = 1, max = MAX_NAME_LENGHT)
    @Column(nullable = false, length = MAX_NAME_LENGHT)
    private String name;

    @Length(max = MAX_DESCRIPTION_LENGHT)
    @Column(length = MAX_DESCRIPTION_LENGHT)
    private String description;

    /*
    TODO: se  n escrevermos nada a validacao da backend nao e' a melhor
     */
    @NotNull
    @Column(nullable = false)
    private float value;


    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Frequency frequency;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genre genre;

    @NotNull
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_Transaction_Type"), nullable = false)
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    private Type type;


    @JoinColumn(foreignKey = @ForeignKey(name = "FK_Transaction_SubType"))
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    private SubType subType;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate date;

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

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public SubType getSubType() {
        return subType;
    }

    public void setSubType(SubType subType) {
        this.subType = subType;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", value=" + value +
                ", frequency=" + frequency +
                ", genre=" + genre +
                ", type=" + type +
                ", subType=" + subType +
                ", date=" + date +
                '}';
    }
}
