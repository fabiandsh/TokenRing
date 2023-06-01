import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class TokenRingSimulation extends JFrame {
    private TokenRing tokenRing;
    private JButton startButton;
    private JButton stopButton;
    private JButton resetButton;
    private JTextField numHostsTextField;
    private JTextField messageTextField;
    private JTextField senderTextField;
    private JTextField receiverTextField;
    private JPanel tokenPanel;
    private JPanel statusPanel;
    private JPanel mainPanel; 
    private JTextArea statusTextArea;
    private Token token;
    private Token messagetoken;
    private boolean thereIsMessage;
    private Color colorback =  new Color(50, 130, 184);
    
    public TokenRingSimulation() {
        super("Token Ring Simulation");
        
        startButton = new JButton("Iniciar");
        stopButton = new JButton("Detener");
        resetButton = new JButton("Reiniciar");
        numHostsTextField = new JTextField(5);
        messageTextField = new JTextField(10);
        senderTextField = new JTextField(5);
        receiverTextField = new JTextField(5);
        tokenPanel = new JPanel();
        
        JPanel buttonPanel = new JPanel();
        JLabel numHostLabel = new JLabel("Número de Hosts:");
        numHostLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        numHostLabel.setForeground(Color.WHITE);
        buttonPanel.add(numHostLabel);
        buttonPanel.add(numHostsTextField);
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(resetButton);

        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messagePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel titleMLable =new JLabel("Configuración Mensaje:");
        titleMLable.setFont(new Font("Arial", Font.BOLD, 16));
        messagePanel.add(titleMLable);
        messagePanel.add(new JLabel(" "));
        messagePanel.add(new JLabel(" "));
        messagePanel.add(new JLabel(" "));
        JLabel messaggeLabel =new JLabel("Mensaje a enviar:");
        messaggeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        messagePanel.add(messaggeLabel);
        messagePanel.add(new JLabel(" "));
        messagePanel.add(messageTextField);
        JLabel emiLabel =new JLabel("Emisor:");
        emiLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        messagePanel.add(new JLabel(" "));
        messagePanel.add(new JLabel(" "));
        messagePanel.add(emiLabel);
        messagePanel.add(new JLabel(" "));
        messagePanel.add(senderTextField);
        JLabel reLabel =new JLabel("Receptor:");
        reLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        messagePanel.add(new JLabel(" "));
        messagePanel.add(new JLabel(" "));
        messagePanel.add(reLabel);
        messagePanel.add(new JLabel(" "));
        messagePanel.add(receiverTextField);
        messagePanel.add(new JLabel(" "));
        messagePanel.add(new JLabel(" "));
        messagePanel.add(new JLabel(" "));
        messagePanel.add(new JLabel(" "));
        

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel labelTitle = new JLabel("SIMULADOR TOKEN-RING");
        labelTitle.setFont(new Font("Arial", Font.BOLD, 20));
        labelTitle.setForeground(Color.WHITE);
        titlePanel.add(labelTitle);
        
        
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BorderLayout());
        tokenPanel.setBackground(Color.WHITE);
        buttonPanel.setBackground(colorback);
        messagePanel.setBackground(Color.WHITE);
        titlePanel.setBackground(colorback);
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(tokenPanel, BorderLayout.WEST);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(messagePanel,  BorderLayout.EAST);
        
        getContentPane().add(mainPanel);
        
        setSize(800, 500);
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
                thereIsMessage = false;
                adminMessage();
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                resetButton.setEnabled(true);
                numHostsTextField.setEnabled(false);
                messageTextField.setEnabled(false);
                senderTextField.setEditable(false);
                receiverTextField.setEditable(false);
            }
        });
        
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tokenRing.stop();
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                resetButton.setEnabled(true);
                numHostsTextField.setEnabled(true);
                messageTextField.setEnabled(true);
                senderTextField.setEditable(true);
                receiverTextField.setEditable(true);
            }
        });
        
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tokenRing.reset();
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                resetButton.setEnabled(false);
                numHostsTextField.setEnabled(true);
                messageTextField.setEnabled(true);
                senderTextField.setEditable(true);
                receiverTextField.setEditable(true);
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
        statusScrollPane.setBackground(colorback);
        statusPanel.add(statusScrollPane, BorderLayout.CENTER);
    
        mainPanel.add(statusPanel, BorderLayout.CENTER);
        JLabel label = new JLabel(" ");
        tokenPanel.add(label);
        for (int i = 0; i < numHosts; i++) {
            label = new JLabel("   Host " + i +"   ");
            label.setFont(new Font("Arial", Font.BOLD, 18));
            label.setBackground(colorback);
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
        currentLabel.setForeground(colorback);
        tokenPanel.repaint();
        statusTextArea.append("Token: "+ token.toString() +"\n");
        statusTextArea.append("Pasando el token al Host " + currentHost + "\n"); 
        this.adminToken(currentHost);
    }

    private void adminMessage(){
        if(!messageTextField.getText().equals("")){
            messagetoken = new Token();
            messagetoken.addMessage(this.changeNumberToBinary(0,1), this.changeNumberToBinary(Integer.valueOf(this.senderTextField.getText()),8),
            this.changeNumberToBinary(Integer.valueOf(this.receiverTextField.getText()),8), 
            this.changeStringToBinary(this.messageTextField.getText()), this.changeNumberToBinary(0,1));
            thereIsMessage = true;
        }else{
            token.cleanToken();
        }
    }

    private void adminToken(int currentHost){
        if(thereIsMessage)
            if(messagetoken.getSourceAddres().equals(this.changeNumberToBinary(currentHost,8))){
                token =  ((Token) messagetoken.clone());
                messagetoken.cleanToken();
            }else if(token.getDestinatioAddress().equals(this.changeNumberToBinary(currentHost,8))){
                statusTextArea.append("El host " + currentHost + " copia el contenido del token para su procesamiento\n"); 
                token.setFrameStatus(this.changeNumberToBinary(1,1));
            }else if(token.getSourceAddres().equals(this.changeNumberToBinary(currentHost,8)) ){
                statusTextArea.append("El host " + currentHost + " verifica envio, limpia el token\n"); 
                token.cleanToken();
            }
    }

    private String changeNumberToBinary(int numero, int lenght){
        String binario = String.format("%"+lenght+"s", Integer.toBinaryString(numero)).replace(' ', '0');
        return binario;
    }

    private String changeStringToBinary(String cadena){
        StringBuilder binario = new StringBuilder();

        byte[] bytes = cadena.getBytes();
        for (byte b : bytes) {
            for (int i = 7; i >= 0; i--) {
                binario.append((b >> i) & 1);
            }
        }

        return binario.toString();
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


