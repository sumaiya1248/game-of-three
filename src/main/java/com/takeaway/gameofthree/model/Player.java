package com.takeaway.gameofthree.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static java.util.Objects.hash;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer playedId;
	private String name ;

	@Override
	public int hashCode() {
		return hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		return Objects.equals(name, other.name);
	}

}
