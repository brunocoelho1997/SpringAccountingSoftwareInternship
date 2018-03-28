package hello.Type;

import hello.Category;
import hello.Entity;
import hello.Genre;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@javax.persistence.Entity(name="type")
public class Type extends Entity {


    public static final int MAX_TYPENAME_LENGHT = 8;
    public static final int MAX__SUBTYPENAME_LENGHT = 8;

    /*
        a meu ver tbm tem de haver um genero aqui... para quando formos a adicionar uma despesa so aparecer os tipos de despesa... e o mesmo acontece para as receitas...

        atencao q tenho a enum genre so' na package Transaction
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genre genre;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;


    @NotNull
    @Size(min=1, max = MAX_TYPENAME_LENGHT)
    @Column(nullable = false, length = MAX_TYPENAME_LENGHT)
    private String typeName;

    @NotNull
    @Size(min=1, max = MAX__SUBTYPENAME_LENGHT)
    @Column(nullable = false, length = MAX__SUBTYPENAME_LENGHT)
    private String subtypeName;

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getSubtypeName() {
        return subtypeName;
    }

    public void setSubtypeName(String subtypeName) {
        this.subtypeName = subtypeName;
    }
}
