package views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import controller.HuespedDAO;
import controller.ReservaHuespedController;
import controller.ReservaDAO;
import factory.ConnectionFactory;
import model.Huesped;
import model.Reserva;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ImageIcon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	public static JTable tbHuespedes;
	private JTable tbReservas;
	private CustomTableModel modelo;
	private CustomTableModel modeloH;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;
	private ReservaHuespedController reservaHuespedController = new ReservaHuespedController();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
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
	public Busqueda() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);
		
		txtBuscar = new JTextField();
		txtBuscar.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		txtBuscar.setBounds(550, 127, 180, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		
		JLabel lblNewLabel_4 = new JLabel("Sistema de búsqueda");
		lblNewLabel_4.setForeground(new Color(12, 145, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblNewLabel_4.setBounds(331, 62, 280, 42);
		contentPane.add(lblNewLabel_4);
		
		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);

		tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), tbReservas, null);
		modelo = new CustomTableModel();
		tbReservas.setModel(modelo);
		modelo.addColumn("N° Reserva");
		modelo.addColumn("Check-in");
		modelo.addColumn("Check-out");
		modelo.addColumn("Valor");
		modelo.addColumn("Método Pago");
		
		modelo.addRow(new Object[]{
				"N° Reserva","Check-in", "Check-out",
				"Costo", "Método de Pago"
		});
		for (int i = 0; i < tbReservas.getColumnCount(); i++) {
		    tbReservas.getColumnModel().getColumn(i).setCellRenderer(new TableHeaderStyle());
		}
		tbReservas.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
		    @Override
		    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		        final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		        c.setBackground(row == 0 ? table.getTableHeader().getBackground() : (isSelected ? Color.LIGHT_GRAY : Color.WHITE));
		        return c;
		    }
		});
		
		reservaHuespedController.listarReservas(modelo);
		
		tbHuespedes = new JTable();
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")), tbHuespedes, null);
		modeloH = new CustomTableModel();
		tbHuespedes.setModel(modeloH);
		modeloH.addColumn("N° Huesped");
		modeloH.addColumn("Nombre");
		modeloH.addColumn("Apellido");
		modeloH.addColumn("Nacimiento");
		modeloH.addColumn("Nacionalidad");
		modeloH.addColumn("Teléfono");
		modeloH.addColumn("N° Reserva");
		
		modeloH.addRow(new Object[]{
				"N° Huesped", "Nombre", "Apellido", "Nacimiento",
				"Nacionalidad", "Teléfono", "N° Reserva"
		});
		for (int i = 0; i < tbHuespedes.getColumnCount(); i++) {
			tbHuespedes.getColumnModel().getColumn(i).setCellRenderer(new TableHeaderStyle());
		}
		reservaHuespedController.listarHuespedes(modeloH);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);
		
		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);
			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);
		
		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnAtras.setBackground(Color.white);
			     labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);
		
		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		
		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnexit.setBackground(Color.white);
			     labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);
		
		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);
		
		JPanel btnbuscar = new JPanel();
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable tablaActiva = (JTable) panel.getSelectedComponent();
				int indicePanelActivo = panel.getSelectedIndex();
				String nombrePanelActivo = panel.getTitleAt(indicePanelActivo);
				
				String valorABuscar = txtBuscar.getText();
				reservaHuespedController.buscar(modelo, modeloH, nombrePanelActivo, tablaActiva, valorABuscar);
			}
		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);
		
		JLabel lblBuscar = new JLabel("Buscar");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));
		
		JPanel btnEditar = new JPanel();
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);
		
		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// tablaActiva: Reservas / Huespedes
				JTable tablaActiva = (JTable) panel.getSelectedComponent();
				int filaSeleccionada = tablaActiva.getSelectedRow();
				
				if(filaSeleccionada > 0) { // -1 si no hay ningun seleccionado
					int editarAdvertencia = JOptionPane.showConfirmDialog(null, "<html>Está seguro/a de que desea guardar los cambios?<br>"
							+ "ATENCIÓN: Solo se guardarán los cambios de la fila seleccionada!</html>","Confirmación",0);
					if(editarAdvertencia == 0) { // 0: SI | 1: NO
						int indicePanelActivo = panel.getSelectedIndex();
						String nombrePanelActivo = panel.getTitleAt(indicePanelActivo);
						
						reservaHuespedController.editarRegistros(nombrePanelActivo, tablaActiva, filaSeleccionada);
						
						reservaHuespedController.limpiarLista(modelo, modeloH);
						reservaHuespedController.listarReservas(modelo);
						reservaHuespedController.listarHuespedes(modeloH);
					}
				}else {
					JOptionPane.showMessageDialog(null, "No hay ninguna fila seleccionada.");
				}
			}
		});
		
		JLabel lblEditar = new JLabel("Editar");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);
		
		JPanel btnEliminar = new JPanel();
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);
		
		btnEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// tablaActiva: Reservas / Huespedes
				JTable tablaActiva = (JTable) panel.getSelectedComponent();
				int filaSeleccionada = tablaActiva.getSelectedRow();
				
				if(filaSeleccionada > 0) { // -1 si no hay ningun seleccionado
					
					int eliminarAdvertencia = JOptionPane.showConfirmDialog(null, "Está seguro/a de que desea eliminar este registro?","Confirmación",0);
					
					if(eliminarAdvertencia == 0) { // 0: SI | 1: NO
						int indicePanelActivo = panel.getSelectedIndex();
						String nombrePanelActivo = panel.getTitleAt(indicePanelActivo);
						
						reservaHuespedController.eliminarSeleccionado(nombrePanelActivo, tablaActiva, filaSeleccionada);
						reservaHuespedController.limpiarLista(modelo, modeloH);
						
						reservaHuespedController.listarReservas(modelo);
						reservaHuespedController.listarHuespedes(modeloH);
					}
				}else {
					JOptionPane.showMessageDialog(null, "No hay ninguna fila seleccionada.");
				}
			}
		});
		
		JLabel lblEliminar = new JLabel("Eliminar");
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);
		
		JPanel btnReiniciarListas = new JPanel();
		btnReiniciarListas.setLayout(null);
		btnReiniciarListas.setBackground(new Color(12, 138, 199));
		btnReiniciarListas.setBounds(25, 508, 150, 35);
		btnReiniciarListas.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(btnReiniciarListas);
		
		btnReiniciarListas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				reservaHuespedController.limpiarLista(modelo, modeloH);
				reservaHuespedController.listarReservas(modelo);
				reservaHuespedController.listarHuespedes(modeloH);
			}
		});
		
		JLabel lblReiniciarListas = new JLabel("Reiniciar listas");
		lblReiniciarListas.setHorizontalAlignment(SwingConstants.CENTER);
		lblReiniciarListas.setForeground(Color.WHITE);
		lblReiniciarListas.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblReiniciarListas.setBounds(0, 0, 150, 35);
		btnReiniciarListas.add(lblReiniciarListas);
	}
	
	//Código que permite mover la ventana por la pantalla según la posición de "x" y "y"
	private void headerMousePressed(MouseEvent evt) {
		xMouse = evt.getX();
		yMouse = evt.getY();
	}

	private void headerMouseDragged(MouseEvent evt) {
		int x = evt.getXOnScreen();
		int y = evt.getYOnScreen();
		this.setLocation(x - xMouse, y - yMouse);
	}
}
