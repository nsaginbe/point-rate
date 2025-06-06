package org.nurgisa.pointrate.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rating")
@Getter
@Setter
@NoArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;

    // Owning side (owns the point_id foreign key)
    @OneToOne
    @JoinColumn(name = "point_id", referencedColumnName = "id", nullable = false)
    private Point point;

    // Non-owning side (Rating have RatingCount)
    @OneToOne(mappedBy = "rating", cascade = CascadeType.ALL)
    private RatingCount ratingCount;

    @Column(name = "education", nullable = false)
    private double education;

    @Column(name = "sports", nullable = false)
    private double sports;

    @Column(name = "entertainment", nullable = false)
    private double entertainment;

    @Column(name = "transport", nullable = false)
    private double transport;

    @Column(name = "ecology", nullable = false)
    private double ecology;

    @Column(name = "overall", nullable = false)
    private double overall;
}
