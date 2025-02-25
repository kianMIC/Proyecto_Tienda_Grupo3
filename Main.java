import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Producto> productos = ProductoData.leerTodosProductos();
        List<Compra> compras = CompraData.leerTodasCompras();

        while (true) {
            System.out.println("Selecciona una opción:");
            System.out.println("1. Ver todo el contenido de compras.csv");
            System.out.println("2. Buscar por el nombre en compras.csv");
            System.out.println("3. Ver todo el contenido de productos.csv");
            System.out.println("4. Buscar por el nombre en productos.csv");
            System.out.println("5. Ganancias totales");
            System.out.println("6. Ganancias totales de un producto");
            System.out.println("7. Ganancias totales de cada producto");
            System.out.println("8. Agregar un producto");
            System.out.println("9. Agregar una compra");
            System.out.println("10. Eliminar un producto");
            System.out.println("11. Actualizar el precio de un producto");
            System.out.println("12. Cliente más activo por numero de compras");
            System.out.println("13. Cliente más activo por valor de compras");
            System.out.println("14. Salir");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.println("Contenido de compras" + "\n");
                    
                    

                    for (Producto producto : productos) {
                        int cantidad = 0;
                        for (Compra compra : compras) {
                            if (producto.getNombre().equals(compra.getNombreProducto())) {
                                cantidad += compra.getCantidad();
                            }

                        }

                        System.out.println(producto.getNombre() + " " + cantidad);
                    }
                    System.out.println("-----------------------------------------");
                    break;
                case 2:
                    System.out.print("Ingresa el nombre a buscar en compras.csv: ");
                    String nombreCl = scanner.nextLine();

                    List<Compra> compraPorNombre = new ArrayList<>();
                    for (Compra compra : compras) {
                        if (compra.getNombreClinete().equalsIgnoreCase(nombreCl)) {
                            compraPorNombre.add(compra);
                        }
                    }
                    if (!compraPorNombre.isEmpty()) {
                        System.out.println("Cliente " + nombreCl + " tiene estas compras:");
                        compraPorNombre.stream().forEach(compra -> System.out
                                .println(compra.getNombreProducto() + "   " + compra.getCantidad()));
                        System.out.println("-----------------------------------------");
                    } else {
                        System.out.println("No hay cliente con este nombre");
                    }
                    break;
                case 3:
                    productos = ProductoData.leerTodosProductos();
                    System.out.println("Lista de productos.csv:");
                    productos.stream().forEach(producto -> System.out.println(producto.toString()));
                    System.out.println("-----------------------------------------");
                    break;
                case 4:
                    System.out.print("Ingresa el nombre del producto a buscar: ");
                    String nombreProducto = scanner.nextLine();
                    boolean encontrado = false;
                    for (Producto producto : productos) {
                        if (producto.getNombre().equalsIgnoreCase(nombreProducto)) {
                            encontrado = true;
                            System.out.println(producto.toString());
                        }
                    }
                    if (!encontrado) {
                        System.out.println("No hay producto con este nombre");
                    }
                    System.out.println("-----------------------------------------");
                    break;
                case 5:
                    System.out.println("Ganancias totales: ");
                    double totalidad = 0.0;
                    for (Compra compra : compras) {
                        for (Producto producto : productos) {
                            if (producto.getNombre().equals(compra.getNombreProducto())) {
                                totalidad += compra.getCantidad() * producto.getPrecio();
                            }
                        }
                    }
                    System.out.println("Las ganancias totales son :" + totalidad);
                    System.out.println("-----------------------------------------");
                    break;
                case 6:
                    System.out.println("Introduce el nombre del producto:");
                    String nombrePr = scanner.nextLine();

                    double totalidadProducto = 0.0;
                    boolean productoEncontrado = false;
                    for (Producto producto : productos) {
                        if (producto.getNombre().equalsIgnoreCase(nombrePr)) {
                            productoEncontrado = true;
                            for (Compra compra : compras) {
                                if (compra.getNombreProducto().equalsIgnoreCase(nombrePr)) {
                                    totalidadProducto += producto.getPrecio() * compra.getCantidad();
                                }
                            }
                            System.out.println("Ganancias por producto " + nombrePr + " es " + totalidadProducto);
                            System.out.println("-----------------------------------------");
                            break;
                        }
                    }
                    if (!productoEncontrado) {
                        System.out.println("No hay producto con este nombre");
                    }
                    break;
                case 7:

                    break;

                case 8:
                System.out.println("Agregar un nuevo producto:");
                System.out.print("Nombre del producto: ");
                String nuevoNombre = scanner.nextLine();
                boolean existeProducto = productos.stream().anyMatch(p -> p.getNombre().equalsIgnoreCase(nuevoNombre));
                if (existeProducto) {
                    System.out.println("El producto ya existe. No se puede agregar duplicado.");
                    break;
                }
                double nuevoPrecio = 0;
                boolean precioValido = false;
                while (!precioValido) {
                    System.out.print("Precio del producto: ");
                    if (scanner.hasNextDouble()) {
                        nuevoPrecio = scanner.nextDouble();
                        scanner.nextLine();  // borrar fuffer
                        precioValido = true;
                    } else {
                        System.out.println("Entrada inválida. Por favor, ingresa un número válido para el precio.");
                        scanner.next();  // borrar input invalido
                    }
                }
                Producto nuevoProducto = new Producto(nuevoNombre, nuevoPrecio);
                productos.add(nuevoProducto);
                ProductoData.guardarProducto(nuevoProducto);
                System.out.println("Producto agregado exitosamente.");
                System.out.println("-----------------------------------------");
                break;
                case 9:
                    System.out.println("Nombre del cliente: ");
                    String nombreCliente = scanner.nextLine();
                    System.out.println("Nombre producto: ");
                    String nameProducto = scanner.nextLine();
                    boolean findProducto = false;

                    for (Producto producto : productos) {
                        if (nameProducto.equals(producto.getNombre())) {
                            findProducto = true;

                            System.out.println("Cantidad del producto: ");
                            int cantidadProducto = scanner.nextInt();

                            Compra nuevacompra = new Compra(nombreCliente, nameProducto, cantidadProducto);
                            compras.add(nuevacompra);
                            CompraData.guardarCompra(nuevacompra);

                            System.out.println("Se ha añadido la compra!");
                            System.out.println(
                                    nombreCliente + " | " + nameProducto + " | " + cantidadProducto + " | " + "\n");
                        }
                    }
                    if (!findProducto) {
                        System.out.println("\n" + "No existe este producto!" + "\n");
                    }

                    break;

                case 10:
                    System.out.println("Eliminar un producto:");
                    System.out.print("Nombre del producto a eliminar: ");
                    String nombreEliminar = scanner.nextLine();

                    Producto productoAEliminar = null;
                    for (Producto producto : productos) {
                        if (producto.getNombre().equalsIgnoreCase(nombreEliminar)) {
                            productoAEliminar = producto;
                            break;
                        }
                    }
                    if (productoAEliminar != null) {
                        productos.remove(productoAEliminar);
                        ProductoData.eliminarProducto(productoAEliminar);
                        System.out.println("Producto eliminado exitosamente.");
                    } else {
                        System.out.println("No hay producto con este nombre");
                    }
                    System.out.println("-----------------------------------------");
                    break;
                case 11:
                    System.out.println("Actualizar el precio de un producto:");
                    System.out.print("Nombre del producto a actualizar: ");
                    String nombreActualizar = scanner.nextLine();

                    Producto productoAActualizar = null;
                    for (Producto producto : productos) {
                        if (producto.getNombre().equalsIgnoreCase(nombreActualizar)) {
                            productoAActualizar = producto;
                            break;
                        }
                    }
                    if (productoAActualizar != null) {
                        double nuevoPrecioActualizar = 0;
                        boolean precioActualizarValido = false;

                        while (!precioActualizarValido) {
                            System.out.print("Nuevo precio del producto: ");
                            if (scanner.hasNextDouble()) {
                                nuevoPrecioActualizar = scanner.nextDouble();
                                scanner.nextLine();
                                precioActualizarValido = true;
                            } else {
                                System.out.println(
                                        "Entrada inválida. Por favor, ingresa un número válido para el precio.");
                                scanner.next(); 
                            }
                        }

                        productoAActualizar.setPrecio(nuevoPrecioActualizar);
                        ProductoData.actualizarProducto(productoAActualizar);
                        System.out.println("Producto actualizado exitosamente.");
                    } else {
                        System.out.println("No hay producto con este nombre");
                    }
                    System.out.println("-----------------------------------------");
                    break;

                case 12:
                    // Cliente más activo por numero de compras
                    boolean findCliente = false;
                    boolean findProduct = false;

                case 13:
                    System.out.println("cliente +  activo x valor de compra");

                    break;
                case 14:
                    System.out.println("Saliendo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}
