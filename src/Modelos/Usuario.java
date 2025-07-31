package Modelos;

/**
 * Representa un usuario del sistema (administrador o cajero).
 */
public class Usuario {
    private String usuario;
    private String clave;
    private String rol;

    /**
     * Constructor de usuario.
     *
     * @param usuario Nombre de usuario.
     * @param clave Clave de acceso.
     * @param rol Rol del usuario ("administrador" o "cajero").
     */
    public Usuario(String usuario, String clave, String rol) {
        this.usuario = usuario;
        this.clave = clave;
        this.rol = rol;
    }

    public String getUsuario() { return usuario; }
    public String getClave() { return clave; }
    public String getRol() { return rol; }
}
