package org.nurgisa.pointrate.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rating_count")
@Getter
@Setter
@NoArgsConstructor
public class RatingCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Owning side (owns the rating_id foreign key)
    @OneToOne
    @JoinColumn(name = "rating_id", referencedColumnName = "id", nullable = false)
    private Rating rating;

    @Column(name = "education", nullable = false)
    private int education;

    @Column(name = "sports", nullable = false)
    private int sports;

    @Column(name = "entertainment", nullable = false)
    private int entertainment;

    @Column(name = "transport", nullable = false)
    private int transport;

    @Column(name = "ecology", nullable = false)
    private int ecology;
}
