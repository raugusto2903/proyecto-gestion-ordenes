package com.rami.gestionordenes.repositories;

import com.rami.gestionordenes.auditoria.OrdenAuditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenAuditoriaRepository extends JpaRepository<OrdenAuditoria, Long> {
}
