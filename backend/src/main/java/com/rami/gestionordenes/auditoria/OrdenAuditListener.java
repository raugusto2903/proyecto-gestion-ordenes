package com.rami.gestionordenes.auditoria;

import com.rami.gestionordenes.models.Orden;
import com.rami.gestionordenes.repositories.OrdenAuditoriaRepository;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class OrdenAuditListener implements ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(OrdenAuditListener.class);
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }

    @PostPersist
    public void postPersist(Orden orden) {
        logger.info("🚀 Orden creada con ID: {}", orden.getId());
        registrarAuditoria(orden, "CREATE");
    }

    @PreUpdate
    public void preUpdate(Orden orden) {
        logger.info("✏️ Orden actualizada: ID={}, Estado={}", orden.getId(), orden.getEstado());
        registrarAuditoria(orden, "UPDATE");
    }

    @PreRemove
    public void preRemove(Orden orden) {
        logger.warn("🗑 Orden eliminada: ID={}", orden.getId());
        registrarAuditoria(orden, "DELETE");
    }

    private void registrarAuditoria(Orden orden, String accion) {
        // 🔥 Obtener el Repository de Spring
        OrdenAuditoriaRepository ordenAuditoriaRepository = applicationContext.getBean(OrdenAuditoriaRepository.class);

        if (ordenAuditoriaRepository == null) {
            logger.error("❌ ERROR: `ordenAuditoriaRepository` sigue siendo NULL");
            return;
        }

        OrdenAuditoria auditoria = new OrdenAuditoria();
        auditoria.setOrdenId(orden.getId());
        auditoria.setAccion(accion);
        auditoria.setTotal(orden.getTotal());
        auditoria.setEstado(orden.getEstado());
        auditoria.setFechaEvento(new Date());
        auditoria.setUsuario("system"); // Cambia esto para usar el usuario real

        ordenAuditoriaRepository.save(auditoria);
        logger.info("✅ Auditoría guardada en la BD para la orden ID={}", orden.getId());
    }
}
