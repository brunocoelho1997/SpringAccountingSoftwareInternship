package hello.SheetTransaction.Resources;

import hello.Project.Project;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Duration;

@Embeddable
public class HoursPerProject {

    @NotNull
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_HoursPerProject_Project"), nullable = false)
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = false)
    private Project project;

    @Column
    @NotNull
    private Duration duration;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
