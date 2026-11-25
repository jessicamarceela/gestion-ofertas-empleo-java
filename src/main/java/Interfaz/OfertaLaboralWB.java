package Interfaz;

import dao.OfertaDAOImplObjectDB;
import modelo.OfertaLaboral;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class OfertaLaboralWB extends JFrame {

    private static final long serialVersionUID = 1L;
    // formulario
    private JPanel contentPane;
    private JPanel panel;
    private JLabel Título;
    private JTextField txtTitulo;
    private JLabel Empresa;
    private JTextField txtEmpresa;
    private JLabel Ubicacion;
    private JTextField txtUbicacion;
    private JLabel Salario;
    private JTextField txtSalario;
    private JLabel Vacantes;
    private JTextField txtVacantes;
    private JLabel Experiencia;
    private JComboBox<String> cmbExperiencia;
    private JLabel Contrato;
    private JLabel Fecha;
    private JTextField txtFecha;
    private JLabel ID;
    private JTextField txtId;
    // tabla y menu
    private JTable tabla;
    private TableRowSorter<DefaultTableModel> sorter;
    private DefaultTableModel modelo;
    private JPanel PanelBotones;
    private JButton btnNewButton;
    private JButton btnNewButton_1;
    private JButton btnNewButton_2;
    private JButton btnNewButton_3;
    private JMenuBar menuBar;
    private JMenu mnNewMenu;
    private JMenuItem mntmNewMenuItem;
    private JMenu mnNewMenu_1;
    private JMenuItem mntmNewMenuItem_1;
    private JPanel panelCentro;
    private JLabel lblTotal;
    private JPanel panelBuscador;
    private JLabel lblNewLabel_1;
    private JTextField txtBuscar;
    private JLabel lblNewLabel;
    private JComboBox<String> cmbFiltro;
    private JComboBox<String> cmbContrato;
    // variables
    //private OfertaDAO dao = new OfertaDAOImplObjectDB();
    private OfertaDAOImplObjectDB dao = new OfertaDAOImplObjectDB();

    private int filaHover = -1;

    private boolean salariosOcultos = false;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    OfertaLaboralWB frame = new OfertaLaboralWB();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public OfertaLaboralWB() {
        // CONFIGURACIÓN BÁSICA DEL FRAME
        setResizable(false);
        setBounds(new Rectangle(0, 0, 800, 600));
        setTitle("Gestión de Ofertas Laborales");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 600);
        setLocationRelativeTo(null);

        // BARRA DE MENÚ
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        Color grisIntegracion = new Color(240, 240, 240);
        menuBar.setBackground(grisIntegracion);
        menuBar.setOpaque(true);
        // Quitamos cualquier borde para que no haya líneas que marquen el corte
        menuBar.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // CREAR el menú
        mnNewMenu = new JMenu("Archivo");
        // AHORA sí puedes darle color
        mnNewMenu.setBackground(grisIntegracion);
        mnNewMenu.setOpaque(true);
        // Lo añado a la barra
        menuBar.add(mnNewMenu);

        mntmNewMenuItem = new JMenuItem("Salir");
        mntmNewMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        mnNewMenu.add(mntmNewMenuItem);

        mnNewMenu_1 = new JMenu("Ayuda");
        menuBar.add(mnNewMenu_1);

        mntmNewMenuItem_1 = new JMenuItem("Acerca de");
        mntmNewMenuItem_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Aplicación CRUD de ofertas laborales\r\n"
                                + "Desarrollado por Jessica\r\n" + "FP DAW - UT9 Interfaces", "Acerca de",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        mnNewMenu_1.add(mntmNewMenuItem_1);

        // DISEÑO DEL CONTENEDOR PRINCIPAL
        contentPane = new JPanel();
        contentPane.setBackground(Color.decode("#FDF3EE"));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        // PANEL CENTRAL: TABLA
        panelCentro = new JPanel();
        panelCentro.setBackground(Color.decode("#FDF3EE"));
        panelCentro.setLayout(new BorderLayout(0, 10));
        contentPane.add(panelCentro, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setForeground(new Color(255, 255, 255));
        scrollPane.setBackground(Color.decode("#FDF3EE"));
        scrollPane.getViewport().setBackground(Color.decode("#FDF3EE"));
        scrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder()); // Opcional: quita el borde del scroll

        tabla = new JTable();
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabla.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {

            @Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                                    boolean hasFocus, int row, int column) {

                java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                        column);

                if (isSelected) {
                    // Un salmón más oscuro y cálido para que se note qué fila hemos pinchado
                    c.setBackground(new Color(252, 208, 186));
                    c.setForeground(Color.BLACK);
                } else if (row == filaHover) {
                    // Un salmón casi blanco para el efecto cuando pasas el ratón
                    c.setBackground(new Color(255, 245, 240));
                    c.setBackground(new Color(255, 230, 215));
                    c.setForeground(Color.BLACK);
                } else {
                    // El color de fondo de mi app para unificar visualmente
                    c.setBackground(Color.decode("#FDF3EE"));
                    c.setForeground(Color.BLACK);
                }

                // Lógica de Ocultar Salarios (para que no se pierda al redibujar)
                if (column == 4 && salariosOcultos) {
                    setText("**** €");
                }

                // Quitar el borde de foco que a veces sale de color azul o puntos
                if (c instanceof javax.swing.JComponent) {
                    ((javax.swing.JComponent) c).setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));
                }

                return c;
            }
        });

        modelo = new DefaultTableModel(new Object[][]{},
                new String[]{"ID", "Puesto", "Empresa", "Ubicación", "Salario"});

        tabla.setModel(modelo);

        sorter = new TableRowSorter<>(modelo);
        tabla.setRowSorter(sorter);

        // Creamos la lista de ordenación inicial
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();

        // Ordenamos por la columna 1 (Puesto) de forma Ascendente
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));

        // Aplicamos al sorter
        sorter.setSortKeys(sortKeys);

        // cuando el ratón salga de la tabla, se limpie el efecto
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                filaHover = -1;
                tabla.repaint();
            }
        });

        tabla.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                // Obtenemos el índice de la fila bajo el ratón
                int fila = tabla.rowAtPoint(e.getPoint());

                // Si la fila ha cambiado, actualizamos y repintamos
                if (fila != filaHover) {
                    filaHover = fila;
                    tabla.repaint();
                }
            }
        });

        tabla.getSelectionModel().addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent e) {
                if (e.getValueIsAdjusting())
                    return;

                int filaVista = tabla.getSelectedRow();

                if (filaVista != -1) {
                    // 1. Convertimos el índice (por si hay filtros o reordenación)
                    int filaModelo = tabla.convertRowIndexToModel(filaVista);

                    // 2. Activamos botones
                    btnNewButton_1.setEnabled(true); // EDITAR
                    btnNewButton_2.setEnabled(true); // BORRAR

                    // 3. RELLENAR FORMULARIO DESDE EL MODELO (Sincronización Perfecta)
                    // En lugar de ir a la DAO, sacamos los datos que ya están en el JTable
                    txtId.setText(modelo.getValueAt(filaModelo, 0).toString());
                    txtTitulo.setText(modelo.getValueAt(filaModelo, 1).toString());
                    txtEmpresa.setText(modelo.getValueAt(filaModelo, 2).toString());
                    txtUbicacion.setText(modelo.getValueAt(filaModelo, 3).toString());
                    txtSalario.setText(modelo.getValueAt(filaModelo, 4).toString());

                    // Si quiero los combos, ahí sí podría usar la consulta por ID que tenía
                    // o mejor aún, haber guardado la lista completa en memoria.
                    int id = Integer.parseInt(modelo.getValueAt(filaModelo, 0).toString());
                    OfertaLaboral o = dao.consultarPorId(id);
                    if (o != null) {
                        txtVacantes.setText(String.valueOf(o.getVacantes()));
                        cmbExperiencia.setSelectedItem(o.getExperiencia());
                        cmbContrato.setSelectedItem(o.getTipoContrato());
                        txtFecha.setText(new java.text.SimpleDateFormat("dd-MM-yyyy").format(o.getFechaPublicacion()));
                    }
                }
            }
        });

        // ESTILO BONITO
        tabla.setRowHeight(25);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabla.getTableHeader().setBackground(new Color(70, 130, 180));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setOpaque(true);

        // Mantener el color, pero que no sea tan oscuro que tape la flecha
        tabla.getTableHeader().setBackground(new Color(70, 130, 180));
        tabla.getTableHeader().setForeground(Color.WHITE);

        tabla.setSelectionBackground(new Color(52, 152, 219));
        tabla.setSelectionForeground(Color.WHITE);

        scrollPane.setViewportView(tabla);
        panelCentro.add(scrollPane, BorderLayout.CENTER);

        // PANEL DERECHO: IMAGEN Y PRIVACIDAD
        JPanel panelImagen = new JPanel();
        panelImagen.setOpaque(false);
        panelImagen.setBackground(Color.decode("#FDF3EE"));
        panelImagen.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));

        ImageIcon icono = new ImageIcon(getClass().getResource("/img/humano.png"));
        Image img = icono.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        JLabel lblImagen = new JLabel(new ImageIcon(img));

        // El 140 es para que baje a la altura de la tabla.
        lblImagen.setBorder(new javax.swing.border.EmptyBorder(140, 10, 0, 10));
        lblImagen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblImagen.setToolTipText("Clic para ocultar/mostrar datos sensibles");

        // La etiqueta de texto de ayuda (Aquí es donde la profe verá la usabilidad)
        JLabel lblAyudaPrivacidad = new JLabel("Modo Privacidad");
        lblAyudaPrivacidad.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblAyudaPrivacidad.setForeground(Color.GRAY);
        lblAyudaPrivacidad.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblAyudaPrivacidad.setBorder(new javax.swing.border.EmptyBorder(5, 0, 0, 0));

        panelImagen.add(lblImagen);
        panelImagen.add(lblAyudaPrivacidad);

        panelCentro.add(panelImagen, BorderLayout.EAST);

        lblImagen.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                salariosOcultos = !salariosOcultos; // Cambiamos el estado

                int columnaSalario = 4;

                if (salariosOcultos) {
                    // Cambio visual en el texto de ayuda
                    lblAyudaPrivacidad.setText("PRIVACIDAD: ON");
                    lblAyudaPrivacidad.setForeground(new Color(200, 80, 80)); // Rojo suave para indicar que está activo
                    // Aplicar máscara
                    tabla.getColumnModel().getColumn(columnaSalario).setCellRenderer(new DefaultTableCellRenderer() {
                        @Override
                        public java.awt.Component getTableCellRendererComponent(JTable table, Object value,
                                                                                boolean isSelected, boolean hasFocus, int row, int column) {
                            // Mantener colores de selección y hover
                            Component c = super.getTableCellRendererComponent(table, "**** €", isSelected, hasFocus,
                                    row, column);
                            if (isSelected) {
                                c.setBackground(new Color(252, 208, 186));
                            } else if (row == filaHover) {
                                c.setBackground(new Color(255, 230, 215));
                            } else {
                                c.setBackground(Color.decode("#FDF3EE"));
                            }
                            return c;
                        }
                    });
                } else {
                    lblAyudaPrivacidad.setText("Modo Privacidad");
                    lblAyudaPrivacidad.setForeground(Color.GRAY);
                    // Quitar máscara (volver al formato normal)
                    tabla.getColumnModel().getColumn(columnaSalario).setCellRenderer(new DefaultTableCellRenderer());
                }

                tabla.repaint();
                tabla.getTableHeader().repaint();
            }
        });

        // PANEL SUPERIOR: BUSCADOR
        panelBuscador = new JPanel();
        panelBuscador.setBorder(new EmptyBorder(5, 0, 5, 10));
        panelBuscador.setBackground(Color.decode("#FDF3EE"));
        panelBuscador.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

        lblNewLabel = new JLabel("Buscar por:");
        panelBuscador.add(lblNewLabel);

        // 2. Creamos el ComboBox
        cmbFiltro = new JComboBox<>();
        // 3. EL LISTENER
        cmbFiltro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                filtrar(txtBuscar.getText());
            }
        });

        // 4. Configuración técnica (modelo y posición)
        cmbFiltro.setBounds(new Rectangle(0, 0, 120, 25));
        cmbFiltro.setModel(new DefaultComboBoxModel<>(new String[]{"Puesto", "Empresa", "Ubicación"}));
        panelBuscador.add(cmbFiltro);

        lblNewLabel_1 = new JLabel("Buscar:");
        panelBuscador.add(lblNewLabel_1);

        txtBuscar = new JTextField();
        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtrar(txtBuscar.getText());
            }
        });
        txtBuscar.setColumns(20);
        panelBuscador.add(txtBuscar);
        panelCentro.add(panelBuscador, BorderLayout.NORTH);

        // PANEL IZQUIERDO: FORMULARIO
        panel = new JPanel();
        Color salmonFondo = Color.decode("#FAE7DD"); // Tu color original
        // 1. IMPORTANTE: Hacemos el panel transparente para que no se vean las esquinas
        // rectas
        panel.setOpaque(false);
        // 2. Aplicamos el borde que RELLENA con el color salmón
        // El primer número (70) controla cuánto espacio hay arriba y abajo (para
        // centrar)
        panel.setBorder(javax.swing.BorderFactory.createCompoundBorder(new BordeRedondeado(salmonFondo, 30),
                new EmptyBorder(50, 25, 50, 25)));
        // 3. Lo añadimos al WEST (el BorderLayout se encarga del tamaño)
        contentPane.add(panel, BorderLayout.WEST);
        // 4. El layout de 10 filas que ya tenías
        panel.setLayout(new GridLayout(10, 2, 10, 10));

        Título = new JLabel("Puesto");
        Título.setFont(new Font("Segoe UI", Font.BOLD, 12));
        Título.setForeground(new Color(60, 60, 60));
        panel.add(Título);

        txtTitulo = new JTextField();
        txtTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtTitulo.setBounds(new Rectangle(0, 0, 150, 25));
        panel.add(txtTitulo);
        txtTitulo.setColumns(10);

        Empresa = new JLabel("Empresa");
        Empresa.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(Empresa);

        txtEmpresa = new JTextField();
        txtEmpresa.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtEmpresa.setBounds(new Rectangle(0, 0, 150, 25));
        panel.add(txtEmpresa);
        txtEmpresa.setColumns(10);

        Ubicacion = new JLabel("Ubicación");
        Ubicacion.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(Ubicacion);

        txtUbicacion = new JTextField();
        txtUbicacion.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtUbicacion.setBounds(new Rectangle(0, 0, 150, 25));
        panel.add(txtUbicacion);
        txtUbicacion.setColumns(10);

        Salario = new JLabel("Salario");
        Salario.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(Salario);

        txtSalario = new JTextField();
        txtSalario.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtSalario.setBounds(new Rectangle(0, 0, 150, 25));
        panel.add(txtSalario);

        txtSalario.setToolTipText("Introduce un número");

        txtSalario.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {

            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                validar();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                validar();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                validar();
            }

            private void validar() {

                if (txtSalario.getText().isEmpty()) {
                    txtSalario.setBackground(Color.WHITE);
                } else {
                    try {
                        Double.parseDouble(txtSalario.getText());
                        txtSalario.setBackground(Color.WHITE);
                    } catch (Exception ex) {
                        txtSalario.setBackground(new Color(255, 200, 200));
                    }
                }
            }
        });
        txtSalario.setColumns(10);

        Vacantes = new JLabel("Vacantes");
        Vacantes.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(Vacantes);

        txtVacantes = new JTextField();
        txtVacantes.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtVacantes.setBounds(new Rectangle(0, 0, 150, 25));
        panel.add(txtVacantes);
        txtVacantes.setColumns(10);
        txtVacantes.setToolTipText("Introduce un número entero");

        txtVacantes.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {

            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                validar();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                validar();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                validar();
            }

            private void validar() {

                if (txtVacantes.getText().isEmpty()) {
                    txtVacantes.setBackground(Color.WHITE);
                } else {
                    try {
                        Integer.parseInt(txtVacantes.getText());
                        txtVacantes.setBackground(Color.WHITE);
                    } catch (Exception ex) {
                        txtVacantes.setBackground(new Color(255, 200, 200));
                    }
                }
            }
        });

        Experiencia = new JLabel("Experiencia");
        Experiencia.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(Experiencia);

        cmbExperiencia = new JComboBox<>();

        cmbExperiencia.setModel(new DefaultComboBoxModel<>(new String[]{"Sin experiencia", "Menos de 6 meses",
                "6 meses - 1 año", "1 - 2 años", "Más de 2 años"}));

        cmbExperiencia.setToolTipText("Selecciona la experiencia requerida");

        panel.add(cmbExperiencia);

        Contrato = new JLabel("Contrato");
        Contrato.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(Contrato);

        cmbContrato = new JComboBox<>();

        cmbContrato.setModel(
                new DefaultComboBoxModel<>(new String[]{"Indefinido", "Temporal", "Prácticas", "Freelance"}));

        cmbContrato.setToolTipText("Selecciona tipo de contrato");

        panel.add(cmbContrato);

        Fecha = new JLabel("Fecha");
        Fecha.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(Fecha);

        txtFecha = new JTextField();
        txtFecha.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtFecha.setBounds(new Rectangle(0, 0, 150, 25));
        panel.add(txtFecha);
        txtFecha.setColumns(10);
        txtFecha.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {

            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                validar();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                validar();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                validar();
            }

            private void validar() {

                if (txtFecha.getText().isEmpty()) {
                    txtFecha.setBackground(Color.WHITE);
                } else if (txtFecha.getText().matches("\\d{2}-\\d{2}-\\d{4}")) {
                    txtFecha.setBackground(Color.WHITE);
                } else {
                    txtFecha.setBackground(new Color(255, 200, 200));
                }
            }
        });
        txtFecha.setToolTipText("Formato: dd-MM-yyyy (Ej: 07-04-2026)");

        ID = new JLabel("ID");
        ID.setFont(new Font("Segoe UI", Font.BOLD, 12));
        ID.setEnabled(false);
        panel.add(ID);

        txtId = new JTextField();
        txtId.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtId.setBounds(new Rectangle(0, 0, 150, 25));
        txtId.setEnabled(false);
        panel.add(txtId);
        txtId.setColumns(10);

        // PANEL INFERIOR: BOTONES
        lblTotal = new JLabel("Total ofertas: 0");
        PanelBotones = new JPanel();
        PanelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        PanelBotones.setBackground(Color.decode("#FDF3EE"));

        // CREACIÓN DE OBJETOS
        btnNewButton = new JButton("NUEVO");
        btnNewButton_1 = new JButton("EDITAR");
        btnNewButton_2 = new JButton("BORRAR");
        btnNewButton_3 = new JButton("LIMPIAR");
        // CONFIGURACIÓN DE ESTILO
        Color salmonBotones = Color.decode("#FFE0D0");
        Color salmonHover = Color.decode("#FCD0BA");

        // Creamos un array botones para aplicarles lo mismo a todos de golpe
        JButton[] misBotones = {btnNewButton, btnNewButton_1, btnNewButton_2, btnNewButton_3};

        for (JButton btn : misBotones) {
            btn.setBackground(salmonBotones);
            btn.setForeground(Color.BLACK); // Texto SIEMPRE negro cuando está activo
            btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
            btn.setFocusPainted(false);
            btn.setBorder(new EmptyBorder(10, 25, 10, 25));
            btn.setBorderPainted(false);

            // Aplicamos el hover corregido
            aplicarHover(btn, salmonBotones, salmonHover);
            // Los añadimos al panel
            PanelBotones.add(btn); // Los añadimos al panel

        }

        // 3. ASIGNACIÓN DE ACCIONES (Listeners)
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertar();
            }
        });

        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editar();
            }
        });

        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                borrar();
            }
        });

        btnNewButton_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiar();
            }
        });

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(Color.decode("#FDF3EE"));
        panelInferior.add(lblTotal, BorderLayout.NORTH);
        panelInferior.add(PanelBotones, BorderLayout.SOUTH);
        panelCentro.add(panelInferior, BorderLayout.SOUTH);

        // ESTADO INICIAL
        btnNewButton_1.setEnabled(false); // EDITAR
        btnNewButton_2.setEnabled(false);

        btnNewButton_2.setMargin(new Insets(0, 0, 0, 0));
        btnNewButton_2.setBounds(new Rectangle(0, 0, 140, 45));
        btnNewButton_2.setFocusPainted(false);
        btnNewButton_2.setBorder(new EmptyBorder(10, 25, 10, 25));
        btnNewButton_2.setBackground(salmonBotones);
        btnNewButton_2.setFont(new Font("Segoe UI", Font.BOLD, 13));
        PanelBotones.add(btnNewButton_2);

        aplicarHoverTabla();

        cargarTabla();
        limpiar();

    }

    // MÉTODOS DE LÓGICA CRUD

    private void cargarTabla() {

        modelo.setRowCount(0); // limpia tabla

        List<OfertaLaboral> lista = dao.listarOfertas();

        for (OfertaLaboral o : lista) {
            modelo.addRow(new Object[]{o.getId(), o.getTitulo(), o.getEmpresa(), o.getUbicacion(), o.getSalario()});
        }
        lblTotal.setText("Total ofertas: " + modelo.getRowCount());
    }

    private OfertaLaboral recogerDatos() {

        if (txtTitulo.getText().isEmpty() || txtEmpresa.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Campos obligatorios vacíos");
            return null;
        }

        if (!txtFecha.getText().matches("\\d{2}-\\d{2}-\\d{4}")) {
            JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto.\nUsa: dd-MM-yyyy\nEjemplo: 07-04-2026",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        try {
            return new OfertaLaboral(0, txtTitulo.getText(), txtEmpresa.getText(), txtUbicacion.getText(),
                    Double.parseDouble(txtSalario.getText()), Integer.parseInt(txtVacantes.getText()),
                    cmbExperiencia.getSelectedItem().toString(), cmbContrato.getSelectedItem().toString(),
                    new java.text.SimpleDateFormat("dd-MM-yyyy").parse(txtFecha.getText()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en los datos");
            return null;
        }
    }

    private void insertar() {

        OfertaLaboral o = recogerDatos();

        if (o != null && dao.insertar(o)) {
            JOptionPane.showMessageDialog(this, "Oferta guardada en ObjectDB correctamente");
            cargarTabla();
            limpiar();
        }
    }

    private void limpiar() {
        txtId.setText("");
        txtTitulo.setText("");
        txtEmpresa.setText("");
        txtUbicacion.setText("");
        txtSalario.setText("");
        txtVacantes.setText("");
        cmbExperiencia.setSelectedIndex(0);
        cmbContrato.setSelectedIndex(0);
        txtFecha.setText("");

        // RESET VISUAL
        txtSalario.setBackground(Color.WHITE);
        txtVacantes.setBackground(Color.WHITE);
        txtFecha.setBackground(Color.WHITE);

        tabla.clearSelection();

        // desactivar botones
        btnNewButton_1.setEnabled(false); // EDITAR
        btnNewButton_2.setEnabled(false); // BORRAR
    }

    private void editar() {

        int fila = tabla.convertRowIndexToModel(tabla.getSelectedRow());

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila");
            return;
        }

        try {

            OfertaLaboral o = new OfertaLaboral();

            o.setId(Integer.parseInt(txtId.getText()));
            o.setTitulo(txtTitulo.getText());
            o.setEmpresa(txtEmpresa.getText());
            o.setUbicacion(txtUbicacion.getText());
            o.setSalario(Double.parseDouble(txtSalario.getText()));
            o.setVacantes(Integer.parseInt(txtVacantes.getText()));
            o.setExperiencia(cmbExperiencia.getSelectedItem().toString());
            o.setTipoContrato(cmbContrato.getSelectedItem().toString());
            o.setFechaPublicacion(new java.text.SimpleDateFormat("dd-MM-yyyy").parse(txtFecha.getText()));

            if (dao.modificar(o)) {
                JOptionPane.showMessageDialog(this, "Datos guardados correctamente", "Información",
                        JOptionPane.INFORMATION_MESSAGE);
                cargarTabla();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Revisa los campos:\n- Salario debe ser número\n- Vacantes debe ser número", "Error de validación",
                    JOptionPane.ERROR_MESSAGE);
        }

        lblTotal.setText("Total ofertas: " + modelo.getRowCount());
    }

    private void borrar() {
        // 1. Obtener la fila seleccionada correctamente
        int fila = tabla.getSelectedRow();
        if (fila == -1) return;
        int id = (int) modelo.getValueAt(fila, 0); // Pillamos el ID de la tabla
        int confirmar = JOptionPane.showConfirmDialog(this, "¿Borrar de la base de datos de objetos?");

        if (confirmar == JOptionPane.YES_OPTION) {
            if (dao.eliminar(id)) {
                JOptionPane.showMessageDialog(this, "Oferta eliminada");
                cargarTabla();
                limpiar();
            }
        }
    }

    private void filtrar(String texto) {
        // Si el campo de búsqueda está vacío, eliminamos cualquier filtro activo
        if (texto.trim().isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            // Obtenemos qué campo ha seleccionado el usuario en el ComboBox
            // 0: Puesto, 1: Empresa, 2: Ubicación...
            int seleccion = cmbFiltro.getSelectedIndex();
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto, seleccion + 1));
        }

        // Actualizamos el contador de la interfaz para que muestre
        // el número de filas que han quedado visibles tras el filtro
        lblTotal.setText("Total ofertas: " + tabla.getRowCount());
    }

    private void aplicarHover(JButton boton, Color colorNormal, Color colorHover) {
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                // Solo cambia el fondo si el botón está habilitado
                if (boton.isEnabled()) {
                    boton.setBackground(colorHover);
                    boton.setForeground(Color.BLACK);
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                // Al salir, vuelve al color normal (el texto sigue siendo negro)
                boton.setBackground(colorNormal);
                boton.setForeground(Color.BLACK);
            }
        });
    }

    private void aplicarHoverTabla() {

        tabla.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {

            public void mouseMoved(java.awt.event.MouseEvent e) {

                filaHover = tabla.rowAtPoint(e.getPoint());
                tabla.repaint(); // redibuja la tabla
            }
        });

        tabla.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseExited(java.awt.event.MouseEvent e) {
                filaHover = -1;
                tabla.repaint();
            }
        });

        // Añadimos el listener para gestionar el cierre ordenado de la ventana
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                try {
                    // Obtenemos la factoría de la clase de conexión
                    jakarta.persistence.EntityManagerFactory emf = conexion.DBObjectDB.getEntityManagerFactory();

                    // Comprobamos si está abierta para cerrarla y liberar el archivo .odb
                    if (emf != null && emf.isOpen()) {
                        emf.close();
                        System.out.println("Base de datos ObjectDB cerrada correctamente.");
                    }
                } catch (Exception ex) {
                    // En caso de error, lo notificamos por consola
                    System.err.println("Error al cerrar la base de datos: " + ex.getMessage());
                }

                // Salida del programa fuera del bloque try-catch
                System.exit(0);
            }
        });

        cargarTabla();

    }

    class BordeRedondeado extends javax.swing.border.AbstractBorder {
        private static final long serialVersionUID = 1L;
        private Color colorFondo;
        private int radio;

        // ESTA ES LA PARTE CLAVE: El constructor.
        // Si no está exactamente así, Eclipse sacará el error de  foto.
        public BordeRedondeado(Color color, int radio) {
            this.colorFondo = color;
            this.radio = radio;
        }

        @Override
        public void paintBorder(java.awt.Component c, java.awt.Graphics g, int x, int y, int width, int height) {
            java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
            g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(colorFondo);
            // Usamos fill en lugar de draw para que no haya líneas grises
            g2.fillRoundRect(x, y, width - 1, height - 1, radio, radio);

            g2.dispose();
        }
    }
}
