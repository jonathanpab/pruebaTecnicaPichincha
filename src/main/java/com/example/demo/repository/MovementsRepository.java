package com.example.demo.repository;

import com.example.demo.entity.Movements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface MovementsRepository extends JpaRepository<Movements, Long> {
    @Query("FROM Movements movements WHERE movements.cuenta.client.person.nombre= :name " +
            "AND movements.fecha>:fecha_desde " +
            "AND movements.fecha<:fecha_hasta")
    List<Movements> findByUser(@Param("name") String name, @Param("fecha_desde") Date fecha_desde,
                               @Param("fecha_hasta") Date fecha_hasta);

    @Query("FROM Movements movements WHERE movements.cuenta.numero_cuenta= :numero_cuenta " +
            "AND movements.fecha>:fecha_desde " +
            "AND movements.fecha<:fecha_hasta")
    List<Movements> findByAccount(@Param("numero_cuenta") long numero_cuenta, @Param("fecha_desde") Date fecha_desde,
                                  @Param("fecha_hasta") Date fecha_hasta);
}
