package hello.SheetTransaction.Resources;

import hello.Project.Project;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalTime;

@Embeddable
public class HoursPerProject {

    @NotNull
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_HoursPerProject_Project"), nullable = false)
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = false)
    private Project project;

    @NotNull
    @Column(nullable = false)
    private float duration;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "HoursPerProject{" +
                "project=" + project +
                ", duration=" + duration +
                '}';
    }
}
