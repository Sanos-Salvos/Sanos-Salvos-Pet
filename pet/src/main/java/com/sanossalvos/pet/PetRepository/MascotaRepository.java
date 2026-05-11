package com.sanossalvos.pet.PetRepository;

import com.sanossalvos.pet.PetModel.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
}