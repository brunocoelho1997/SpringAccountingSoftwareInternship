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

    @Column
    @DateTimeFormat(pattern = "HH:mm")
    @NotNull
    private LocalTime duration;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }
}
