package integrado.prog2.services;

import integrado.prog2.entities.Usuario;
import integrado.prog2.enums.Rol;
import integrado.prog2.exception.EntidadNoEncontradaException;
import integrado.prog2.exception.ReglaNegocioException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioService {
    private final List<Usuario> usuarios;
    private Long idCounter;

    public UsuarioService() {
        this.usuarios = new ArrayList<>();
        this.idCounter = 1L;
    }

    public Usuario crear(String nombre, String apellido, String mail, String celular, String contrasenia, Rol rol) throws ReglaNegocioException {
        if (mail == null || mail.trim().isEmpty()) {
            throw new ReglaNegocioException("El mail no puede estar vacio.");
        }
        
        for (Usuario u : usuarios) {
            if (u.getMail().equalsIgnoreCase(mail) && !u.isEliminado()) {
                throw new ReglaNegocioException("Ya existe un usuario con el mail: " + mail);
            }
        }

        Usuario nuevo = new Usuario(idCounter++, nombre, apellido, mail, celular, contrasenia, rol);
        usuarios.add(nuevo);
        return nuevo;
    }

    public List<Usuario> listar() {
        List<Usuario> activos = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (!u.isEliminado()) {
                activos.add(u);
            }
        }
        return activos;
    }

    public Usuario buscarPorId(Long id) throws EntidadNoEncontradaException {
        for (Usuario u : usuarios) {
            if (u.getId().equals(id) && !u.isEliminado()) {
                return u;
            }
        }
        throw new EntidadNoEncontradaException("No se encontro un usuario activo con el ID: " + id);
    }

    public void editar(Long id, String nombre, String apellido, String mail, String celular, String contrasenia, Rol rol) throws EntidadNoEncontradaException, ReglaNegocioException {
        Usuario u = buscarPorId(id);

        if (nombre != null && !nombre.trim().isEmpty()) u.setNombre(nombre);
        if (apellido != null && !apellido.trim().isEmpty()) u.setApellido(apellido);
        if (celular != null && !celular.trim().isEmpty()) u.setCelular(celular);
        if (contrasenia != null && !contrasenia.trim().isEmpty()) u.setContrasenia(contrasenia);
        if (rol != null) u.setRol(rol);

        if (mail != null && !mail.trim().isEmpty()) {
            if (!u.getMail().equalsIgnoreCase(mail)) {
                for (Usuario usr : usuarios) {
                    if (usr.getMail().equalsIgnoreCase(mail) && !usr.isEliminado()) {
                        throw new ReglaNegocioException("Ya existe otro usuario con el mail: " + mail);
                    }
                }
            }
            u.setMail(mail);
        }
    }

    public void eliminar(Long id) throws EntidadNoEncontradaException {
        Usuario u = buscarPorId(id);
        u.setEliminado(true);
    }
}