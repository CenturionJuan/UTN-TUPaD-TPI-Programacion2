package integrado.prog2;

import integrado.prog2.entities.Categoria;
import integrado.prog2.entities.Pedido;
import integrado.prog2.entities.Producto;
import integrado.prog2.entities.Usuario;
import integrado.prog2.enums.Estado;
import integrado.prog2.enums.FormaPago;
import integrado.prog2.enums.Rol;
import integrado.prog2.exception.EntidadNoEncontradaException;
import integrado.prog2.exception.ReglaNegocioException;
import integrado.prog2.services.CategoriaService;
import integrado.prog2.services.PedidoService;
import integrado.prog2.services.ProductoService;
import integrado.prog2.services.UsuarioService;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final CategoriaService categoriaService = new CategoriaService();
    private static final ProductoService productoService = new ProductoService();
    private static final UsuarioService usuarioService = new UsuarioService();
    private static final PedidoService pedidoService = new PedidoService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\nSISTEMA DE PEDIDOS (FOOD STORE)");
            System.out.println("1. Categorias");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios");
            System.out.println("4. Pedidos");
            System.out.println("0. Salir");
            System.out.print("Seleccione: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1 -> menuCategorias();
                    case 2 -> menuProductos();
                    case 3 -> menuUsuarios();
                    case 4 -> menuPedidos();
                    case 0 -> System.out.println("Saliendo del sistema. Hasta luego!");
                    default -> System.out.println("[ERROR] Opcion invalida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Debe ingresar un numero valido.");
            }
        }
    }

    private static void menuCategorias() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\nMENU CATEGORIAS");
            System.out.println("1. Listar categorias");
            System.out.println("2. Crear categoria");
            System.out.println("3. Editar categoria");
            System.out.println("4. Eliminar categoria");
            System.out.println("0. Volver al menu principal");
            System.out.print("Seleccione: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> listarCategorias();
                    case 2 -> crearCategoria();
                    case 3 -> editarCategoria();
                    case 4 -> eliminarCategoria();
                    case 0 -> {
                    }
                    default -> System.out.println("[ERROR] Opcion invalida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Debe ingresar un numero valido.");
            }
        }
    }

    private static void listarCategorias() {
        System.out.println("\nListado de Categorias");
        List<Categoria> lista = categoriaService.listar();
        if (lista.isEmpty()) System.out.println("No hay categorias cargadas.");
        else for (Categoria c : lista) System.out.println(c.toString());
    }

    private static void crearCategoria() {
        System.out.println("\nCrear Categoria");
        System.out.print("Ingrese nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese descripcion: ");
        String descripcion = scanner.nextLine();
        try {
            Categoria nueva = categoriaService.crear(nombre, descripcion);
            System.out.println("[EXITO] Categoria creada con ID: " + nueva.getId());
        } catch (ReglaNegocioException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private static void editarCategoria() {
        System.out.println("\n-- Editar Categoria --");
        listarCategorias();
        System.out.print("Ingrese el ID a editar: ");
        try {
            Long id = Long.valueOf(scanner.nextLine());
            System.out.print("Nuevo nombre (vacio para mantener): ");
            String nombre = scanner.nextLine();
            System.out.print("Nueva descripcion (vacio para mantener): ");
            String descripcion = scanner.nextLine();
            categoriaService.editar(id, nombre, descripcion);
            System.out.println("[EXITO] Actualizada correctamente.");
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] ID invalido.");
        } catch (EntidadNoEncontradaException | ReglaNegocioException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private static void eliminarCategoria() {
        System.out.println("\nEliminar Categoria");
        listarCategorias();
        System.out.print("Ingrese el ID a eliminar: ");
        try {
            Long id = Long.valueOf(scanner.nextLine());
            System.out.print("Seguro? (S/N): ");
            if (scanner.nextLine().equalsIgnoreCase("S")) {
                categoriaService.eliminar(id);
                System.out.println("[EXITO] Eliminada logicamente.");
            }
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] ID invalido.");
        } catch (EntidadNoEncontradaException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private static void menuProductos() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\nMENU PRODUCTOS");
            System.out.println("1. Listar productos");
            System.out.println("2. Crear producto");
            System.out.println("3. Editar producto");
            System.out.println("4. Eliminar producto");
            System.out.println("0. Volver al menu principal");
            System.out.print("Seleccione: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> listarProductos();
                    case 2 -> crearProducto();
                    case 3 -> editarProducto();
                    case 4 -> eliminarProducto();
                    case 0 -> {
                    }
                    default -> System.out.println("[ERROR] Opcion invalida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Debe ingresar un numero valido.");
            }
        }
    }

    private static void listarProductos() {
        System.out.println("\nListado de Productos");
        List<Producto> lista = productoService.listar();
        if (lista.isEmpty()) System.out.println("No hay productos cargados.");
        else for (Producto p : lista) System.out.println(p.toString());
    }

    private static void crearProducto() {
        System.out.println("\nCrear Producto");
        listarCategorias();
        System.out.print("Ingrese ID de la categoria: ");
        try {
            Long idCat = Long.valueOf(scanner.nextLine());
            Categoria cat = categoriaService.buscarPorId(idCat);
            
            System.out.print("Nombre: "); String nombre = scanner.nextLine();
            System.out.print("Descripcion: "); String desc = scanner.nextLine();
            System.out.print("Precio: "); Double precio = Double.valueOf(scanner.nextLine());
            System.out.print("Stock: "); int stock = Integer.parseInt(scanner.nextLine());
            System.out.print("Imagen URL: "); String img = scanner.nextLine();
            System.out.print("Disponible? (true/false): "); Boolean disp = Boolean.valueOf(scanner.nextLine());

            Producto p = productoService.crear(nombre, precio, desc, stock, img, disp, cat);
            System.out.println("[EXITO] Producto creado con ID: " + p.getId());
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Dato numerico invalido.");
        } catch (EntidadNoEncontradaException | ReglaNegocioException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private static void editarProducto() {
        System.out.println("\nEditar Producto");
        listarProductos();
        System.out.print("ID del producto a editar: ");
        try {
            Long id = Long.valueOf(scanner.nextLine());
            System.out.print("Nuevo nombre (vacio para mantener): "); String nom = scanner.nextLine();
            System.out.print("Nuevo precio (vacio para mantener): "); String prec = scanner.nextLine();
            Double p = prec.isEmpty() ? null : Double.valueOf(prec);
            System.out.print("Nueva descripcion (vacio para mantener): "); String desc = scanner.nextLine();
            System.out.print("Nuevo stock (vacio para mantener): "); String stk = scanner.nextLine();
            Integer s = stk.isEmpty() ? null : Integer.valueOf(stk);
            System.out.print("Nueva imagen (vacio para mantener): "); String img = scanner.nextLine();
            System.out.print("Disponible? (true/false, vacio para mantener): "); String dispStr = scanner.nextLine();
            Boolean disp = dispStr.isEmpty() ? null : Boolean.valueOf(dispStr);
            System.out.print("Nuevo ID categoria (vacio para mantener): "); String catStr = scanner.nextLine();
            Categoria cat = catStr.isEmpty() ? null : categoriaService.buscarPorId(Long.valueOf(catStr));

            productoService.editar(id, nom, p, desc, s, img, disp, cat);
            System.out.println("[EXITO] Producto actualizado.");
        } catch (EntidadNoEncontradaException | ReglaNegocioException | NumberFormatException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private static void eliminarProducto() {
        System.out.println("\nEliminar Producto");
        listarProductos();
        System.out.print("ID a eliminar: ");
        try {
            Long id = Long.valueOf(scanner.nextLine());
            System.out.print("Seguro? (S/N): ");
            if (scanner.nextLine().equalsIgnoreCase("S")) {
                productoService.eliminar(id);
                System.out.println("[EXITO] Eliminado logicamente.");
            }
        } catch (EntidadNoEncontradaException | NumberFormatException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private static void menuUsuarios() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- MENU USUARIOS ---");
            System.out.println("1. Listar usuarios");
            System.out.println("2. Crear usuario");
            System.out.println("3. Editar usuario");
            System.out.println("4. Eliminar usuario");
            System.out.println("0. Volver al menu principal");
            System.out.print("Seleccione: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> listarUsuarios();
                    case 2 -> crearUsuario();
                    case 3 -> editarUsuario();
                    case 4 -> eliminarUsuario();
                    case 0 -> {
                    }
                    default -> System.out.println("[ERROR] Opcion invalida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Debe ingresar un numero valido.");
            }
        }
    }

    private static void listarUsuarios() {
        System.out.println("\nListado de Usuarios");
        List<Usuario> lista = usuarioService.listar();
        if (lista.isEmpty()) System.out.println("No hay usuarios cargados.");
        else for (Usuario u : lista) System.out.println(u.toString());
    }

    private static void crearUsuario() {
        System.out.println("\nCrear Usuario");
        System.out.print("Nombre: "); String nombre = scanner.nextLine();
        System.out.print("Apellido: "); String apellido = scanner.nextLine();
        System.out.print("Mail: "); String mail = scanner.nextLine();
        System.out.print("Celular: "); String celular = scanner.nextLine();
        System.out.print("Contrasenia: "); String pass = scanner.nextLine();
        
        System.out.println("Seleccione Rol: 1=ADMIN, 2=USUARIO");
        System.out.print("Rol: ");
        String rolInput = scanner.nextLine();
        Rol rol = rolInput.equals("1") ? Rol.ADMIN : Rol.USUARIO;

        try {
            Usuario u = usuarioService.crear(nombre, apellido, mail, celular, pass, rol);
            System.out.println("[EXITO] Usuario creado con ID: " + u.getId());
        } catch (ReglaNegocioException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private static void editarUsuario() {
        System.out.println("\nEditar Usuario");
        listarUsuarios();
        System.out.print("ID del usuario a editar: ");
        try {
            Long id = Long.valueOf(scanner.nextLine());
            System.out.print("Nuevo nombre (vacio para mantener): "); String nom = scanner.nextLine();
            System.out.print("Nuevo apellido (vacio para mantener): "); String ape = scanner.nextLine();
            System.out.print("Nuevo mail (vacio para mantener): "); String mail = scanner.nextLine();
            System.out.print("Nuevo celular (vacio para mantener): "); String cel = scanner.nextLine();
            System.out.print("Nueva contrasenia (vacio para mantener): "); String pass = scanner.nextLine();
            
            System.out.print("Nuevo Rol (1=ADMIN, 2=USUARIO, vacio para mantener): "); 
            String rolStr = scanner.nextLine();
            Rol rol = null;
            if (!rolStr.isEmpty()) {
                rol = rolStr.equals("1") ? Rol.ADMIN : Rol.USUARIO;
            }

            usuarioService.editar(id, nom, ape, mail, cel, pass, rol);
            System.out.println("[EXITO] Usuario actualizado.");
        } catch (EntidadNoEncontradaException | ReglaNegocioException | NumberFormatException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private static void eliminarUsuario() {
        System.out.println("\nEliminar Usuario");
        listarUsuarios();
        System.out.print("ID a eliminar: ");
        try {
            Long id = Long.valueOf(scanner.nextLine());
            System.out.print("Seguro? (S/N): ");
            if (scanner.nextLine().equalsIgnoreCase("S")) {
                usuarioService.eliminar(id);
                System.out.println("[EXITO] Eliminado logicamente.");
            }
        } catch (EntidadNoEncontradaException | NumberFormatException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private static void menuPedidos() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\nMENU PEDIDOS");
            System.out.println("1. Listar pedidos");
            System.out.println("2. Crear pedido");
            System.out.println("3. Actualizar estado y forma de pago");
            System.out.println("4. Eliminar pedido");
            System.out.println("0. Volver al menu principal");
            System.out.print("Seleccione: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> listarPedidos();
                    case 2 -> crearPedido();
                    case 3 -> actualizarPedido();
                    case 4 -> eliminarPedido();
                    case 0 -> {
                    }
                    default -> System.out.println("[ERROR] Opcion invalida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Debe ingresar un numero valido.");
            }
        }
    }

    private static void listarPedidos() {
        System.out.println("\nListado de Pedidos");
        List<Pedido> lista = pedidoService.listar();
        if (lista.isEmpty()) {
            System.out.println("No hay pedidos cargados.");
        } else {
            for (Pedido p : lista) {
                System.out.println(p.toString());
                System.out.println("   [Detalles del pedido]");
                for (var detalle : p.getDetalles()) {
                    System.out.println("   -> " + detalle.toString());
                }
            }
        }
    }

    private static void crearPedido() {
        System.out.println("\nCrear Pedido");
        listarUsuarios();
        System.out.print("Ingrese ID del usuario que realiza la compra: ");
        try {
            Long idUser = Long.valueOf(scanner.nextLine());
            Usuario u = usuarioService.buscarPorId(idUser);

            System.out.println("Seleccione Forma de Pago: 1=TARJETA, 2=TRANSFERENCIA, 3=EFECTIVO");
            System.out.print("Opcion: ");
            String pagoStr = scanner.nextLine();
            FormaPago formaPago = FormaPago.EFECTIVO;
            if (pagoStr.equals("1")) formaPago = FormaPago.TARJETA;
            else if (pagoStr.equals("2")) formaPago = FormaPago.TRANSFERENCIA;

            Pedido p = pedidoService.crear(u, formaPago);
            System.out.println("[INFO] Pedido iniciado. Vamos a agregar productos.");

            boolean agregando = true;
            while (agregando) {
                listarProductos();
                System.out.print("Ingrese ID del producto a comprar (0 para finalizar): ");
                Long idProd = Long.valueOf(scanner.nextLine());
                
                if (idProd == 0) {
                    if (p.getDetalles().isEmpty()) {
                        System.out.println("[INFO] El pedido no tiene productos. Cancelando creacion.");
                        pedidoService.cancelarCreacionPedido(p);
                    } else {
                        System.out.println("[EXITO] Pedido finalizado correctamente con ID: " + p.getId() + " | Total a pagar: $" + p.getTotal());
                    }
                    agregando = false;
                } else {
                    Producto prod = productoService.buscarPorId(idProd);
                    System.out.print("Ingrese la cantidad: ");
                    int cant = Integer.parseInt(scanner.nextLine());

                    pedidoService.agregarDetalle(p, prod, cant);
                    System.out.println("[EXITO] Producto agregado. Subtotal parcial del pedido: $" + p.getTotal());
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Formato invalido.");
        } catch (EntidadNoEncontradaException | ReglaNegocioException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private static void actualizarPedido() {
        System.out.println("\nActualizar Pedido");
        listarPedidos();
        System.out.print("Ingrese ID del pedido a actualizar: ");
        try {
            Long id = Long.valueOf(scanner.nextLine());
            
            System.out.println("Nuevo Estado: 1=PENDIENTE, 2=CONFIRMADO, 3=TERMINADO, 4=CANCELADO (vacio para mantener)");
            String estadoStr = scanner.nextLine();
            Estado estado = null;
            if (!estadoStr.isEmpty()) {
                switch(estadoStr) {
                    case "1" -> estado = Estado.PENDIENTE;
                    case "2" -> estado = Estado.CONFIRMADO;
                    case "3" -> estado = Estado.TERMINADO;
                    case "4" -> estado = Estado.CANCELADO;
                }
            }

            System.out.println("Nueva Forma de Pago: 1=TARJETA, 2=TRANSFERENCIA, 3=EFECTIVO (vacio para mantener)");
            String pagoStr = scanner.nextLine();
            FormaPago fp = null;
            if (!pagoStr.isEmpty()) {
                switch (pagoStr) {
                    case "1" -> fp = FormaPago.TARJETA;
                    case "2" -> fp = FormaPago.TRANSFERENCIA;
                    case "3" -> fp = FormaPago.EFECTIVO;
                    default -> {
                    }
                }
            }

            pedidoService.actualizarEstadoYFormaPago(id, estado, fp);
            System.out.println("[EXITO] Pedido actualizado.");
        } catch (EntidadNoEncontradaException | NumberFormatException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private static void eliminarPedido() {
        System.out.println("\nEliminar Pedido");
        listarPedidos();
        System.out.print("ID a eliminar: ");
        try {
            Long id = Long.valueOf(scanner.nextLine());
            System.out.print("Seguro? (S/N): ");
            if (scanner.nextLine().equalsIgnoreCase("S")) {
                pedidoService.eliminar(id);
                System.out.println("[EXITO] Pedido eliminado logicamente.");
            }
        } catch (EntidadNoEncontradaException | NumberFormatException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }
}