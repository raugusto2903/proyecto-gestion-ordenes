package com.rami.gestionordenes.services;

import com.rami.gestionordenes.models.Usuario;
import com.rami.gestionordenes.models.viewmodels.ClienteFrecuenteDTO;
import com.rami.gestionordenes.repositories.OrdenRepository;
import com.rami.gestionordenes.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private OrdenRepository ordenRepository;

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setUsername(usuarioActualizado.getUsername());
            usuario.setEmail(usuarioActualizado.getEmail());
            usuario.setEnabled(usuarioActualizado.getEnabled());
            usuario.setPassword(usuarioActualizado.getPassword()); // Sin encriptación
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public List<ClienteFrecuenteDTO> obtenerClientesFrecuentes() {
        List<Object[]> resultados = ordenRepository.findTopFrequentCustomers();

        return resultados.stream().map(obj -> new ClienteFrecuenteDTO(
                (Usuario) obj[0],  // Usuario
                ((Number) obj[1]).intValue()  // Cantidad de órdenes
        )).collect(Collectors.toList());
    }

}
