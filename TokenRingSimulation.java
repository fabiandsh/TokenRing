import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class TokenRingSimulation extends JFrame {
    private TokenRing tokenRing;
    private JButton startButton;
    private JButton stopButton;
    private JButton resetButton;
    private JTextField numHostsTextField;
    private JPanel tokenPanel;
    private JPanel statusPanel;
    private JPanel mainPanel; 
    private JTextArea statusTextArea;
    private Token token;
 
    
    public TokenRingSimulation() {
        super("Token Ring Simulation");
        
        startButton = new JButton("Iniciar");
        stopButton = new JButton("Detener");
        resetButton = new JButton("Reiniciar");
        numHostsTextField = new JTextField(5);
        tokenPanel = new JPanel();
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(new JLabel("Número de Hosts:"));
        buttonPanel.add(numHostsTextField);
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(resetButton);

        
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(tokenPanel, BorderLayout.WEST);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        getContentPane().add(mainPanel);
        
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        token = new Token();
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int numHosts = Integer.parseInt(numHostsTextField.getText());
                createTokenPanel(numHosts);
                
                tokenRing = new TokenRing(numHosts);
                tokenRing.setTokenListener(new TokenListener() {
                    public void tokenPassed(int hostId) {
                        updateTokenPanel(hostId);
                    }
                });
                
                tokenRing.start();
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                resetButton.setEnabled(true);
                numHostsTextField.setEnabled(false);
            }
        });
        
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tokenRing.stop();
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                resetButton.setEnabled(true);
                numHostsTextField.setEnabled(true);
            }
        });
        
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tokenRing.reset();
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                resetButton.setEnabled(false);
                numHostsTextField.setEnabled(true);
                
                clearTokenPanel();
            }
        });
        
        stopButton.setEnabled(false);
        resetButton.setEnabled(false);
    }
    
    private void createTokenPanel(int numHosts) {
        tokenPanel.removeAll();
        tokenPanel.setLayout(new BoxLayout(tokenPanel, BoxLayout.Y_AXIS));
        
        statusTextArea = new JTextArea(10, 20);
        statusTextArea.setEditable(false);
        JScrollPane statusScrollPane = new JScrollPane(statusTextArea);

        statusPanel = new JPanel();
        statusPanel.setLayout(new BorderLayout());
        statusPanel.add(statusScrollPane, BorderLayout.CENTER);
    
        mainPanel.add(statusPanel, BorderLayout.CENTER);

        for (int i = 0; i < numHosts; i++) {
            JLabel label = new JLabel("Host " + i);
            label.setForeground(Color.GRAY);
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            tokenPanel.add(label);
        }
        
        tokenPanel.revalidate();
        tokenPanel.repaint();
    }
    
    private void updateTokenPanel(int currentHost) {
        Component[] components = tokenPanel.getComponents();
        
        for (Component component : components) {
            JLabel label = (JLabel) component;
            label.setForeground(Color.GRAY);
        }
        
        JLabel currentLabel = (JLabel) components[currentHost];
        currentLabel.setForeground(Color.RED);

        statusTextArea.append("Pasó el token al Host " + currentHost + "\n");
        //aquí debe estar lo del token con lo del mensaje 
        statusTextArea.append("Token: "+ token.toString() +"\n");
    }
    
    private void clearTokenPanel() {
        tokenPanel.removeAll();
        tokenPanel.revalidate();
        tokenPanel.repaint();
        statusTextArea.setText("");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TokenRingSimulation();
        });
    }
}


