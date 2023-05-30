class TokenRing {
    private int currentHost;
    private TokenListener tokenListener;
    private boolean running;
    private int numHosts;
    private Token token;
    
    public TokenRing(int n) {
        running = false;
        numHosts = n;
        
        for (int i = 0; i < numHosts; i++) {
            final int hostId = i;
            new Thread(() -> {
                while (true) {
                    if (running && currentHost == hostId) {
                        if (tokenListener != null) {
                            tokenListener.tokenPassed(hostId);
                        }
                        currentHost = (currentHost + 1) % numHosts;
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
    
    public void start() {
        running = true;
    }
    
    public void stop() {
        running = false;
    }
    
    public void reset() {
        currentHost = 0;
        running = false;
    }
    
    public void setTokenListener(TokenListener listener) {
        this.tokenListener = listener;
    }

    public Token getToken(){
        return token;
    }
}