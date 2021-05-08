package view;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.DAO;

public class TelaContato extends JFrame {

	private JPanel contentPane;
	private JLabel lblStatus;
	private JTextField txtId;
	private JTextField txtNome;
	private JTextField txtFone;
	private JTextField txtEmail;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaContato frame = new TelaContato();
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
	public TelaContato() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				// ATivação do formulário (Formulário carregado)
				// Status da conexão
				status();
			}
		});
		setTitle("Agenda de Contatos");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaContato.class.getResource("/icones/favicon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon(TelaContato.class.getResource("/icones/dberror.png")));
		lblStatus.setBounds(388, 211, 32, 32);
		contentPane.add(lblStatus);

		JLabel lblId = new JLabel("ID:");
		lblId.setBounds(10, 14, 46, 14);
		contentPane.add(lblId);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(10, 54, 46, 14);
		contentPane.add(lblNome);

		JLabel lblFone = new JLabel("Fone:");
		lblFone.setBounds(10, 96, 46, 14);
		contentPane.add(lblFone);

		JLabel lblEmail = new JLabel("E-mail:");
		lblEmail.setBounds(10, 141, 46, 14);
		contentPane.add(lblEmail);

		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setBounds(52, 11, 127, 20);
		contentPane.add(txtId);
		txtId.setColumns(10);

		txtNome = new JTextField();
		txtNome.setBounds(52, 51, 222, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);

		txtFone = new JTextField();
		txtFone.setBounds(52, 93, 152, 20);
		contentPane.add(txtFone);
		txtFone.setColumns(10);

		txtEmail = new JTextField();
		txtEmail.setBounds(52, 138, 222, 20);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);

		btnRead = new JButton("");
		btnRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selecionarContato();
			}
		});
		btnRead.setEnabled(false);
		btnRead.setToolTipText("Pesquisar contato");
		btnRead.setIcon(new ImageIcon(TelaContato.class.getResource("/icones/read.png")));
		btnRead.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRead.setBorder(null);
		btnRead.setBackground(SystemColor.control);
		btnRead.setBounds(341, 45, 48, 48);
		contentPane.add(btnRead);

		btnCreate = new JButton("");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inserirContato();
			}
		});
		btnCreate.setEnabled(false);
		btnCreate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCreate.setBorder(null);
		btnCreate.setBackground(SystemColor.control);
		btnCreate.setToolTipText("Adicionar contato");
		btnCreate.setIcon(new ImageIcon(TelaContato.class.getResource("/icones/create.png")));
		btnCreate.setBounds(110, 180, 64, 64);
		contentPane.add(btnCreate);

		btnUpdate = new JButton("");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterarContato();
			}
		});
		btnUpdate.setEnabled(false);
		btnUpdate.setToolTipText("Editar contato");
		btnUpdate.setIcon(new ImageIcon(TelaContato.class.getResource("/icones/update.png")));
		btnUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUpdate.setBorder(null);
		btnUpdate.setBackground(SystemColor.control);
		btnUpdate.setBounds(175, 180, 64, 64);
		contentPane.add(btnUpdate);

		btnDelete = new JButton("");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deletarContato();
			}
		});
		btnDelete.setEnabled(false);
		btnDelete.setBorder(null);
		btnDelete.setToolTipText("Excluir contato");
		btnDelete.setIcon(new ImageIcon(TelaContato.class.getResource("/icones/delete.png")));
		btnDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDelete.setBackground(SystemColor.control);
		btnDelete.setBounds(241, 180, 64, 64);
		contentPane.add(btnDelete);
	} // fim do construtor

	// Criação de um objeto para acessar um método da classe DAO
	DAO dao = new DAO();
	private JButton btnRead;
	private JButton btnCreate;
	private JButton btnUpdate;
	private JButton btnDelete;

	/**
	 * Status da conexão
	 */
	private void status() {
		try {
			// estabelecer uma conexão
			Connection con = dao.Conectar();
			// status
			//System.out.println(con);
			// trocando o ícone do banco(status de conexão)
			if (con != null) {
				lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/dbok.png")));
				btnRead.setEnabled(true);
			} else {
				lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/dberror.png")));
			}
			// encerrar conexão
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Selecionar contato
	 */
	private void selecionarContato() {
		//instrução sql para pesquisar um contato pelo nome
		String read = "select * from contatos where nome = ?";
		try {
			// estabelecer uma conexão
			Connection con = dao.Conectar();
			// preparar a instrução sql
			PreparedStatement pst = con.prepareStatement(read);
			// substituir o parametro (?) pelo nome do contato
			pst.setString(1, txtNome.getText());
			// resultado (obter os dadosdo contato pesquisado)
			ResultSet rs = pst.executeQuery();
			// se existir um contato correspondente
			if (rs.next()) {
				txtId.setText(rs.getString(1)); // 1 - idcon
				txtFone.setText(rs.getString(3)); // 3 - Fone
				txtEmail.setText(rs.getString(4)); // 4 - Email
				btnUpdate.setEnabled(true);
				btnDelete.setEnabled(true);
				btnRead.setEnabled(false);
			} else {
				//JOptionPane.showMessageDialog(null, "Contato inexistente");
				btnCreate.setEnabled(true);
				btnRead.setEnabled(false);
			con.close();
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	 /**
	 * Ingerir um novo contato CRUD Read
	 */
	private void inserirContato() {
		// Validação dos campos
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do contato");
		} else if (txtFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o fone do contato");
		} else if (txtNome.getText().length() > 50) {
			JOptionPane.showMessageDialog(null, "O campo nao pode ter mais que 50 caracteres");
		} else if (txtFone.getText().length() > 15) {
			JOptionPane.showMessageDialog(null, "O campo nao pode ter mais que 15 caracteres");
		} else if (txtEmail.getText().length() > 50) {
			JOptionPane.showMessageDialog(null, "O campo nao pode ter mais que 50 caracteres");
		} else {

			{
				String create = "insert into contatos (nome,fone,email) values (?,?,?)";
				try {
					Connection con = dao.Conectar();
					// substituir os parametros(insert no banco de dados)
					PreparedStatement pst = con.prepareStatement(create);
					pst.setString(1, txtNome.getText());
					pst.setString(2, txtFone.getText());
					pst.setString(3, txtEmail.getText());
					// executar a quary
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Contato adicionado");
					con.close();

				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
	}
	
	/**
	 * Editar contato CRUD Update
	 */
	private void alterarContato() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do contato");
		} else if (txtFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o fone do contato");
		} else if (txtNome.getText().length() > 50) {
			JOptionPane.showMessageDialog(null, "O campo nao pode ter mais que 50 caracteres");
		} else if (txtFone.getText().length() > 15) {
			JOptionPane.showMessageDialog(null, "O campo nao pode ter mais que 15 caracteres");
		} else if (txtEmail.getText().length() > 50) {
			JOptionPane.showMessageDialog(null, "O campo nao pode ter mais que 50 caracteres");
		} else {

			{
				String update = "update contatos set nome=?, fone=?, email=? where idcon=?";
				try {
					Connection con = dao.Conectar();
					// substituir os parametros(insert no banco de dados)
					PreparedStatement pst = con.prepareStatement(update);
					pst.setString(1, txtNome.getText());
					pst.setString(2, txtFone.getText());
					pst.setString(3, txtEmail.getText());
					pst.setString(4,  txtId.getText());
					// executar a quary
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Contato editado com sucesso");
					con.close();

				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
	}
	/**
	 * Excluir contato CRUD Delete
	 */
	private void deletarContato() {
		String delete ="delete from contatos where  idcon=?";
		//Confirmação de exclusão do contato
		int confirma = JOptionPane.showConfirmDialog(null,"confirma a exclusão desse contato?","Atenção!",JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			try {
				Connection con = dao.Conectar();
				PreparedStatement pst = con.prepareStatement(delete);
				pst.setString(1, txtId.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Contato excluido");
				limpar();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			limpar();
		}
	}

	/**
	 * Limpar campos e configurar os botoes
	 */
	private void limpar() {
		txtId.setText(null);
		txtNome.setText(null);
		txtFone.setText(null);
		txtEmail.setText(null);
		btnCreate.setEnabled(false);
		btnUpdate.setEnabled(false);
		btnDelete.setEnabled(false);
		btnRead.setEnabled(true);

	}
}
